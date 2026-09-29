package com.kyc.card.payment.processor.handlers;

import com.kyc.card.payment.processor.model.PaymentOperationDTO;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.integration.core.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DebitCardPaymentMessageHandler implements GenericHandler<PaymentOperationDTO> {

    @Override
    public @Nullable Object handle(PaymentOperationDTO payload, MessageHeaders headers) {

        log.info("Processing DebitCardMessageHandler...");
        return payload;
    }
}
