package com.kyc.card.payment.processor.entity;

import com.kyc.card.payment.processor.enums.CardTypeEnum;
import com.kyc.card.payment.processor.enums.PaymentOperationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@Document(collection = "card_payments_records")
public class KycPaymentOperationData {

    @Id
    private Long id;
    private PaymentOperationSource source;
    private CardTypeEnum method;
    private Long folio;
    private String account;
    private BigDecimal amount;
    private String motive;
    private String office;
    private Integer customer;
    private LocalDateTime date;
    private PaymentOperationStatusEnum status;
    private PaymentOperationError error;

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

    @Getter
    @Builder
    @AllArgsConstructor
    public static class PaymentOperationError{

        private String message;
    }
}
