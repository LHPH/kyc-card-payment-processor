package com.kyc.card.payment.processor.config;

import com.kyc.card.payment.processor.config.properties.ServerTcpConfigurationProperties;
import com.kyc.card.payment.processor.listeners.TcpIpFilterListener;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.DefaultTcpNetSocketFactorySupport;
import org.springframework.integration.ip.tcp.connection.TcpConnectionOpenEvent;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpSocketFactorySupport;
import org.springframework.integration.ip.tcp.serializer.ByteArrayCrLfSerializer;

@Configuration
public class TcpServerConnectionConfig {

    @Bean
    public ApplicationListener<TcpConnectionOpenEvent> tcpIpFilterListener(ServerTcpConfigurationProperties serverTcpConfigurationProperties) {

        return new TcpIpFilterListener(
                serverTcpConfigurationProperties.getIpFilter().isEnabled(),
                serverTcpConfigurationProperties.getIpFilter().getAllowedIp()
        );
    }

    @Bean
    public AbstractServerConnectionFactory abstractServerConnectionFactory(
            ServerTcpConfigurationProperties serverTcpConfigurationProperties,
            ObjectProvider<TcpSocketFactorySupport> objectProvider
    ){

        ByteArrayCrLfSerializer serializer = new ByteArrayCrLfSerializer();

        TcpNetServerConnectionFactory factory = new TcpNetServerConnectionFactory(serverTcpConfigurationProperties.getPort());
        factory.setSingleUse(false);
        factory.setSoTimeout(serverTcpConfigurationProperties.getTimeout());
        factory.setBacklog(serverTcpConfigurationProperties.getBacklog());

        factory.setSerializer(serializer);
        factory.setDeserializer(serializer);

        factory.setTcpSocketFactorySupport(objectProvider.getIfAvailable(DefaultTcpNetSocketFactorySupport::new));

        return factory;
    }
}
