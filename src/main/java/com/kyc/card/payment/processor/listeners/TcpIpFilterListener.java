package com.kyc.card.payment.processor.listeners;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.integration.ip.tcp.connection.TcpConnection;
import org.springframework.integration.ip.tcp.connection.TcpConnectionOpenEvent;

import java.util.Objects;
import java.util.Set;

@Slf4j
@AllArgsConstructor
public class TcpIpFilterListener implements ApplicationListener<TcpConnectionOpenEvent> {

    private boolean enabled;
    private Set<String> allowedIp;

    @Override
    public void onApplicationEvent(TcpConnectionOpenEvent event) {

        if (enabled) {

            TcpConnection connection = (TcpConnection) event.getSource();
            //Use with -Djava.net.preferIPv4Stack=true
            String remoteIp = Objects.requireNonNull(connection.getSocketInfo()).getInetAddress().getHostAddress();
            if (!allowedIp.contains(remoteIp)) {

                log.error("IP address {} is not allowed", remoteIp);
                connection.close();
            }
            else{
                log.info("IP address {} connecting", remoteIp);
            }
        }
    }
}
