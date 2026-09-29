package com.kyc.card.payment.processor.model;

import com.kyc.card.payment.processor.entity.KycPaymentOperationData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOperationDTO {

    private KycCardPaymentInputData kycCardPaymentInputData;
    private KycPaymentOperationData kycPaymentOperationData;
}
