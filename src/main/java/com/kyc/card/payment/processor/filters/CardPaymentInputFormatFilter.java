package com.kyc.card.payment.processor.filters;

import org.springframework.integration.core.GenericSelector;
import org.springframework.stereotype.Component;

@Component
public class CardPaymentInputFormatFilter implements GenericSelector<String> {

    @Override
    public boolean accept(String source) {

        String [] parts = source.split("\\|");
        if (parts.length == 11) {

            String operation = parts[0];
            String sourceApp = parts[1];
            String authorization=parts[2];
            String method = parts[3];
            String folio = parts[4];
            String account = parts[5];
            String amount = parts[6];
            String motive = parts[7];
            String office = parts[8];
            String customer = parts[9];
            String date = parts[10];

            return true;
        }
        return false;
    }
}
