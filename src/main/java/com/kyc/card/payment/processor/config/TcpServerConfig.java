package com.kyc.card.payment.processor.config;

import com.kyc.core.exception.handlers.KycMessagingExceptionHandler;
import com.kyc.core.properties.KycMessages;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;
import org.springframework.integration.ip.tcp.inbound.TcpInboundGateway;
import org.springframework.integration.ip.tcp.serializer.ByteArrayCrLfSerializer;

import static com.kyc.card.payment.processor.constants.KycChannelConstants.KYC_CARD_PAYMENT_ERROR_CHANNEL;
import static com.kyc.card.payment.processor.constants.KycChannelConstants.KYC_CARD_PAYMENT_REQUEST_CHANNEL;

@Configuration
public class TcpServerConfig {

    @Bean
    public AbstractServerConnectionFactory abstractServerConnectionFactory(
            @Value("${kyc-config.tcp.port}") Integer port
    ){

        TcpNetServerConnectionFactory factory = new TcpNetServerConnectionFactory(port);
        factory.setSingleUse(false);
        factory.setSerializer(new ByteArrayCrLfSerializer());
        factory.setDeserializer(new ByteArrayCrLfSerializer());
        return factory;
    }

    @Bean
    public TcpInboundGateway tcpInboundGateway(AbstractServerConnectionFactory abstractServerConnectionFactory){

        TcpInboundGateway gateway = new TcpInboundGateway();
        gateway.setConnectionFactory(abstractServerConnectionFactory);
        gateway.setRequestChannelName(KYC_CARD_PAYMENT_REQUEST_CHANNEL);
        gateway.setLoggingEnabled(true);
        gateway.setErrorChannelName(KYC_CARD_PAYMENT_ERROR_CHANNEL);
        return gateway;
    }

    @Bean(name = KYC_CARD_PAYMENT_REQUEST_CHANNEL)
    public DirectChannel directChannel(){
        return new DirectChannel();
    }

    @Bean(name = KYC_CARD_PAYMENT_ERROR_CHANNEL)
    public DirectChannel errorChannel(){
        return new DirectChannel();
    }

    @Bean
    public KycMessagingExceptionHandler kycMessagingExceptionHandler(KycMessages kycMessages){
        return new KycMessagingExceptionHandler(kycMessages);
    }
}
