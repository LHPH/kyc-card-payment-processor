package com.kyc.card.payment.processor.transformers;

import com.kyc.card.payment.processor.model.KycCardPaymentInputData;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.stereotype.Component;


@Component
public class CardPaymentInputTransformer implements GenericTransformer<String,KycCardPaymentInputData> {

    @Override
    public KycCardPaymentInputData transform(String payload) {

        String [] parts = payload.split("\\|");

        return KycCardPaymentInputData.builder()
                .operation(parts[0])
                .source(parts[1])
                .authorization(parts[2])
                .method(parts[3])
                .folio(parts[4])
                .account(parts[5])
                .amount(parts[6])
                .motive(parts[7])
                .office(parts[8])
                .customer(parts[9])
                .date(parts[10])
                .build();
    }
}
