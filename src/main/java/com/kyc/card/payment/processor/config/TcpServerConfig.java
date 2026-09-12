package com.kyc.card.payment.processor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;
import org.springframework.integration.ip.tcp.inbound.TcpInboundGateway;
import org.springframework.integration.ip.tcp.serializer.ByteArrayCrLfSerializer;

@Configuration
public class TcpServerConfig {

    @Bean
    public AbstractServerConnectionFactory abstractServerConnectionFactory(){

        TcpNetServerConnectionFactory factory = new TcpNetServerConnectionFactory(9990);
        factory.setSingleUse(false);
        factory.setSerializer(new ByteArrayCrLfSerializer());
        factory.setDeserializer(new ByteArrayCrLfSerializer());
        return factory;
    }

    @Bean
    public TcpInboundGateway tcpInboundGateway(AbstractServerConnectionFactory abstractServerConnectionFactory){

        TcpInboundGateway gateway = new TcpInboundGateway();
        gateway.setConnectionFactory(abstractServerConnectionFactory);
        gateway.setRequestChannelName("kycCardPaymentRequestChannel");
        gateway.setLoggingEnabled(true);
        return gateway;
    }

    @Bean(name = "kycCardPaymentRequestChannel")
    public DirectChannel directChannel(){
        return new DirectChannel();
    }
}
