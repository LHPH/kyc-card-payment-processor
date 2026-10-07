package com.kyc.card.payment.processor.handlers;

import com.kyc.card.payment.processor.entity.KycPaymentOperationData;
import com.kyc.card.payment.processor.model.PaymentOperationDTO;
import com.kyc.card.payment.processor.repository.PaymentOperationRepository;
import com.kyc.core.constants.IntegrationConstants;
import com.kyc.core.model.MessageData;
import com.kyc.core.properties.KycMessages;
import com.kyc.core.util.IntegrationUtil;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.core.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import static com.kyc.card.payment.processor.constants.AppConstants.ERROR_MESSAGE_003;
import static com.kyc.card.payment.processor.constants.AppConstants.ERROR_MESSAGE_005;

@Slf4j
@Component
public class CardPaymentMessageHandler implements GenericHandler<PaymentOperationDTO> {

    @Autowired
    private PaymentOperationRepository paymentOperationRepository;

    @Autowired
    private KycMessages kycMessages;

    @Override
    public @Nullable Object handle(PaymentOperationDTO payload, MessageHeaders headers) {

        log.info("{}",payload.getKycCardPaymentInputData());

        KycPaymentOperationData operationData = payload.getKycPaymentOperationData();
        paymentOperationRepository.save(operationData);

        return switch (operationData.getStatus()){
            case SUCCESS -> IntegrationConstants.ACK_RESPONSE;
            case FAILED -> {
                MessageData messageData= kycMessages.getMessage(ERROR_MESSAGE_005);
                yield IntegrationUtil.getTcpResponseFromMessage(messageData);
            }
            case ERROR,REGISTERED -> {
                MessageData messageData= kycMessages.getMessage(ERROR_MESSAGE_003);
                yield IntegrationUtil.getTcpResponseFromMessage(messageData);
            }
        };
    }
}
