package com.kyc.card.payment.processor.handlers;

import com.kyc.card.payment.processor.model.KycCardPaymentInputData;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.integration.core.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CardPaymentMessageHandler implements GenericHandler<KycCardPaymentInputData> {

    @Override
    public @Nullable Object handle(KycCardPaymentInputData payload, MessageHeaders headers) {

        log.info("{}",payload);
        return "ACK";
    }
}
