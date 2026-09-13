package com.kyc.card.payment.processor.handlers;

import com.kyc.card.payment.processor.model.KycCardPaymentInputData;
import com.kyc.core.exception.KycException;
import com.kyc.core.properties.KycMessages;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.core.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CardPaymentMessageHandler implements GenericHandler<KycCardPaymentInputData> {

    @Autowired
    private KycMessages kycMessages;

    @Override
    public @Nullable Object handle(KycCardPaymentInputData payload, MessageHeaders headers) {

        log.info("{}",payload);

        if(!payload.getSource().contains("KYC")){

            throw KycException.builder()
                    .inputData(payload)
                    .errorData(kycMessages.getMessage("003"))
                    .exception(new IllegalArgumentException("Error in database"))
                    .build();
        }

        return "ACK";
    }
}
