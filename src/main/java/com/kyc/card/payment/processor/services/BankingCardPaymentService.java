package com.kyc.card.payment.processor.services;

import com.kyc.card.payment.processor.enums.CardTypeEnum;
import com.kyc.card.payment.processor.enums.PaymentOperationStatusEnum;
import com.kyc.card.payment.processor.model.BankingCardData;
import com.kyc.card.payment.processor.model.BankingPaymentResult;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BankingCardPaymentService {

    //TODO
    public BankingCardData getCardInfo(String cardNumber, CardTypeEnum cardType) {


        BankingCardData bankingCardData = new BankingCardData();

        bankingCardData.setActive(true);
        bankingCardData.setValid(true);
        bankingCardData.setBalance(BigDecimal.valueOf(1000));
        bankingCardData.setCardType(cardType);
        bankingCardData.setCard(Long.parseLong(cardNumber));

        return bankingCardData;
    }

    public BankingPaymentResult applyCardPayment(String cardNumber, CardTypeEnum cardType, BigDecimal amount) {
        return new BankingPaymentResult(PaymentOperationStatusEnum.SUCCESS,null);
    }
}
