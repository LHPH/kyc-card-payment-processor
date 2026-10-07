package com.kyc.card.payment.processor.validation;

import com.kyc.card.payment.processor.enums.PaymentOperationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankingCardValidationResult {

    private PaymentOperationStatusEnum status;
    private String error;
}
