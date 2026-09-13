package com.kyc.card.payment.processor.config;

import com.kyc.card.payment.processor.filters.CardPaymentInputFormatFilter;
import com.kyc.card.payment.processor.handlers.CardPaymentBadInputFormatHandler;
import com.kyc.card.payment.processor.handlers.CardPaymentMessageHandler;
import com.kyc.card.payment.processor.transformers.CardPaymentInputTransformer;
import com.kyc.core.exception.handlers.KycMessagingExceptionHandler;
import com.kyc.core.properties.KycMessages;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.ip.tcp.inbound.TcpInboundGateway;

import java.nio.charset.StandardCharsets;

import static com.kyc.card.payment.processor.constants.KycChannelConstants.KYC_CARD_PAYMENT_ERROR_CHANNEL;

@Import({
        KycMessages.class
})
@Configuration
public class CardPaymentIntegrationFlowConfig {

    //001P|KYC|AUTH|CC|12345|123456789|2000.00|PAY SERVICE|1|1000|20260912
    @Bean
    public IntegrationFlow cardPaymentIntegrationFlow(
            TcpInboundGateway tcpInboundGateway,
            CardPaymentInputTransformer cardPaymentInputTransformer,
            CardPaymentMessageHandler cardPaymentMessageHandler,
            CardPaymentInputFormatFilter cardPaymentInputFormatFilter,
            CardPaymentBadInputFormatHandler cardPaymentBadInputFormatHandler
    ) {
        return IntegrationFlow.from(tcpInboundGateway)
                .transform(payload -> new String((byte[]) payload, StandardCharsets.UTF_8))
                .filter(cardPaymentInputFormatFilter, filterSpec -> filterSpec.discardFlow(
                        df -> df.handle(cardPaymentBadInputFormatHandler)
                ))
                .transform(cardPaymentInputTransformer)
                .handle(cardPaymentMessageHandler)
                .get();
    }

    @Bean
    public IntegrationFlow cardPaymentErrorIntegrationFlow(
            KycMessagingExceptionHandler kycMessagingExceptionHandler){

        return IntegrationFlow.from(KYC_CARD_PAYMENT_ERROR_CHANNEL)
                .handle(kycMessagingExceptionHandler)
                .get();
    }
}
