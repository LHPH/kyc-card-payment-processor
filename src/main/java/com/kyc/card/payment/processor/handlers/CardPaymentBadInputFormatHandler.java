package com.kyc.card.payment.processor.handlers;

import com.kyc.core.model.MessageData;
import com.kyc.core.properties.KycMessages;
import com.kyc.core.util.IntegrationUtil;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.core.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import static com.kyc.card.payment.processor.constants.AppConstants.ERROR_MESSAGE_002;


@Slf4j
@Component
public class CardPaymentBadInputFormatHandler implements GenericHandler<String> {

    @Autowired
    private KycMessages kycMessages;

    @Override
    public @Nullable Object handle(String payload, MessageHeaders headers) {

        log.error("Bad input {}",payload);

        MessageData messageData= kycMessages.getMessage(ERROR_MESSAGE_002);
        return IntegrationUtil.getTcpResponseFromMessage(messageData);
    }
}
