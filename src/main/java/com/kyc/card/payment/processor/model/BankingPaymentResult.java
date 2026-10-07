package com.kyc.card.payment.processor.model;

import com.kyc.card.payment.processor.enums.PaymentOperationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankingPaymentResult {

    private PaymentOperationStatusEnum status;
    private String error;
}
