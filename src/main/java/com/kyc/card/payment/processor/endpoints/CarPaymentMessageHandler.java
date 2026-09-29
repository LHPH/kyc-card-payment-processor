package com.kyc.card.payment.processor.endpoints;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

@Slf4j
//@MessageEndpoint
public class CarPaymentMessageHandler {

    //@ServiceActivator(inputChannel = "requestChannel",requiresReply = "true")
    public byte[] handleMessage(Message<byte[]> message) {


        log.info("Received request {}",message.getHeaders());

        log.info("Received payload {}",new String(message.getPayload()));

        return "ACK".getBytes();
    }
}
