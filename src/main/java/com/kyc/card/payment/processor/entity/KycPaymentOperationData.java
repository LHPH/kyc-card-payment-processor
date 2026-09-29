package com.kyc.card.payment.processor.entity;

import com.kyc.card.payment.processor.enums.PaymentOperationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@Document(collection = "card_payments_records")
public class KycPaymentOperationData {

    @Id
    private Integer id;
    private String operation;
    private PaymentOperationSource source;
    private String authorization;
    private String method;
    private Long folio;
    private String account;
    private BigDecimal amount;
    private String motive;
    private String office;
    private Integer customer;
    private LocalDateTime date;
    private PaymentOperationStatusEnum status;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class PaymentOperationSource{

        private String ipAddress;
        private String ipHostname;
        private String ipRemotePort;
        private String ipConnectionId;
        private Long timestamp;
    }
}
