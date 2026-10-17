package com.kyc.card.payment.processor.config;

import com.kyc.card.payment.processor.config.properties.ServerTcpConfigurationProperties;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.ip.tcp.connection.DefaultTcpNetSSLSocketFactorySupport;
import org.springframework.integration.ip.tcp.connection.DefaultTcpSocketSupport;
import org.springframework.integration.ip.tcp.connection.TcpSSLContextSupport;
import org.springframework.integration.ip.tcp.connection.TcpSocketFactorySupport;
import org.springframework.integration.ip.tcp.connection.TcpSocketSupport;

import javax.net.ssl.SSLServerSocket;
import java.net.ServerSocket;

@Configuration
@Profile("prod")
public class SSLConfig {

    @Bean
    public TcpSocketSupport tcpSocketSupport(ServerTcpConfigurationProperties serverTcpConfigurationProperties){

        return new DefaultTcpSocketSupport(false){
            @Override
            public void postProcessServerSocket(ServerSocket serverSocket) {
                if (serverSocket instanceof SSLServerSocket sslServerSocket) {
                    sslServerSocket.setNeedClientAuth(serverTcpConfigurationProperties.isClientAuth());
                }
            }
        };
    }

    @Bean
    public TcpSSLContextSupport  tcpSSLContextSupport(SslBundles sslBundles){
        return ()-> sslBundles.getBundle("kyc-bundle").createSslContext();
    }

    @Bean
    @Primary
    public TcpSocketFactorySupport tcpSocketFactorySupport(TcpSSLContextSupport tcpSSLContextSupport){
        return new DefaultTcpNetSSLSocketFactorySupport(tcpSSLContextSupport);
    }

}
