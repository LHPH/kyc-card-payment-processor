package com.kyc.card.payment.processor.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PaymentOperationStatusEnum {

    REGISTERED,
    SUCCESS,
    ERROR,
    FAILED
}
