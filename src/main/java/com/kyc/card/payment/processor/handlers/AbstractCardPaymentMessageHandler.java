package com.kyc.card.payment.processor.handlers;

import com.kyc.card.payment.processor.entity.KycPaymentOperationData;
import com.kyc.card.payment.processor.enums.CardTypeEnum;
import com.kyc.card.payment.processor.enums.PaymentOperationStatusEnum;
import com.kyc.card.payment.processor.model.BankingCardData;
import com.kyc.card.payment.processor.model.BankingPaymentResult;
import com.kyc.card.payment.processor.model.PaymentOperationDTO;
import com.kyc.card.payment.processor.services.BankingCardPaymentService;
import com.kyc.card.payment.processor.validation.BankingCardValidationEngine;
import com.kyc.card.payment.processor.validation.BankingCardValidationParam;
import com.kyc.card.payment.processor.validation.BankingCardValidationResult;
import com.kyc.core.util.GeneralUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.integration.core.GenericHandler;
import org.springframework.messaging.MessageHeaders;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.kyc.card.payment.processor.entity.KycPaymentOperationData.PaymentOperationError;

@Slf4j
@Getter
@RequiredArgsConstructor
public class AbstractCardPaymentMessageHandler implements GenericHandler<PaymentOperationDTO> {

    private final BankingCardPaymentService bankingCardPaymentService;
    private final BankingCardValidationEngine bankingCardValidationEngine;

    @Override
    public @Nullable Object handle(PaymentOperationDTO payload, MessageHeaders headers) {

        KycPaymentOperationData operationData = payload.getKycPaymentOperationData();

        KycPaymentOperationData refreshOperationData = processCardPayment(operationData);

        payload.setKycPaymentOperationData(refreshOperationData);
        return payload;
    }

    protected Optional<BankingCardValidationResult> checkIfInvalidCard(String account, BigDecimal amount, CardTypeEnum cardTypeEnum) {

        String maskedAccount = GeneralUtil.maskValue(account,"*",4);
        log.info("Getting info of card {}", maskedAccount);
        BankingCardData bankingCardData = bankingCardPaymentService.getCardInfo(account, cardTypeEnum);

        Function<BankingCardData,Boolean> enoughBalance
                = (data) -> data.getBalance().compareTo(amount) >= 0;

        List<BankingCardValidationParam> validations = List.of(
                new BankingCardValidationParam(BankingCardData::isValid,"valid card"),
                new BankingCardValidationParam(BankingCardData::isActive,"active card"),
                new BankingCardValidationParam(enoughBalance,"enough balance")
        );

        log.info("Validating card {}", maskedAccount);
        List<BankingCardValidationResult> results = validations.stream()
                .map(validation -> bankingCardValidationEngine.validate(bankingCardData,validation))
                .toList();

        return results.stream()
                .filter(result -> !PaymentOperationStatusEnum.SUCCESS.equals(result.getStatus()))
                .findFirst();
    }

    protected KycPaymentOperationData processCardPayment(KycPaymentOperationData operationData) {

        String account = operationData.getAccount();
        BigDecimal amount = operationData.getAmount();
        Integer customer = operationData.getCustomer();
        CardTypeEnum cardTypeEnum = operationData.getMethod();

        Optional<BankingCardValidationResult> opValidationResult = checkIfInvalidCard(account,amount,cardTypeEnum);

        if(opValidationResult.isPresent()){

            BankingCardValidationResult validationResult = opValidationResult.get();
            return operationData.toBuilder()
                    .status(PaymentOperationStatusEnum.FAILED)
                    .error(PaymentOperationError.builder()
                            .message(validationResult.getError())
                            .build())
                    .build();
        }
        else{
            return applyCardPayment(operationData);
        }
    }

    protected KycPaymentOperationData applyCardPayment(KycPaymentOperationData paymentData){

        String account = paymentData.getAccount();
        String maskedAccount = GeneralUtil.maskValue(account,"*",4);
        BigDecimal amount = paymentData.getAmount();
        CardTypeEnum cardTypeEnum = paymentData.getMethod();
        Integer customer = paymentData.getCustomer();

        log.info("Applying payment {} of card {} - {} from  {}",amount,maskedAccount,cardTypeEnum,customer);
        BankingPaymentResult bankingPaymentResult = bankingCardPaymentService.applyCardPayment(account,cardTypeEnum,amount);

        KycPaymentOperationData.PaymentOperationError paymentError = null;
        if(bankingPaymentResult.getError() != null) {
            paymentError = PaymentOperationError.builder()
                    .message(bankingPaymentResult.getError())
                    .build();
        }
        log.info("The payment {} of card {} is {}",amount,maskedAccount,bankingPaymentResult.getStatus());
        return paymentData.toBuilder()
                .status(bankingPaymentResult.getStatus())
                .error(paymentError)
                .build();
    }

}
