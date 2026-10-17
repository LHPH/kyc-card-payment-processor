package com.kyc.card.payment.processor.config.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties("kyc-config.tcp")
public class ServerTcpConfigurationProperties {

    private int port;
    private int timeout;
    private int backlog;
    private IpFilterProperties ipFilter = new IpFilterProperties();
    private boolean clientAuth;


}
