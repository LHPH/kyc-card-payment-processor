package com.kyc.card.payment.processor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class KycCardPaymentInputData {

    private String operation;
    private String source;
    private String authorization;
    private String method;
    private String folio;
    private String account;
    private String amount;
    private String motive;
    private String office;
    private String customer;
    private String date;
}
