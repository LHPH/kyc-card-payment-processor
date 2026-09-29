package com.kyc.card.payment.processor.enums;

import com.kyc.card.payment.processor.model.PaymentOperationDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CardTypeEnum {

    UK("Unknown"),
    CC("CreditCard"),
    DC("DebitCard");

    private final String desc;

    public static CardTypeEnum getInstance(String value){

        return Arrays.stream(CardTypeEnum.values())
                .filter(element -> element.name().equals(value))
                .findFirst()
                .orElse(UK);
    }
}
