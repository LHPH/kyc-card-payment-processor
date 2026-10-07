package com.kyc.card.payment.processor.handlers;


import com.kyc.card.payment.processor.model.PaymentOperationDTO;
import com.kyc.card.payment.processor.services.BankingCardPaymentService;
import com.kyc.card.payment.processor.validation.BankingCardValidationEngine;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.core.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreditCardPaymentMessageHandler extends AbstractCardPaymentMessageHandler implements GenericHandler<PaymentOperationDTO> {

    @Autowired
    public CreditCardPaymentMessageHandler(BankingCardPaymentService bankingCardPaymentService, BankingCardValidationEngine bankingCardValidationEngine) {
        super(bankingCardPaymentService, bankingCardValidationEngine);
    }

    @Override
    public @Nullable Object handle(PaymentOperationDTO payload, MessageHeaders headers) {

        log.info("Processing credit card payment message");
        return super.handle(payload, headers);
    }
}
