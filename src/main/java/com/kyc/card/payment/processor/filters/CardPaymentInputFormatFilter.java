package com.kyc.card.payment.processor.filters;

import org.springframework.integration.core.GenericSelector;
import org.springframework.stereotype.Component;

import static com.kyc.card.payment.processor.constants.AppConstants.CARD_PAYMENT_LAYOUT_REGEX;
import static com.kyc.card.payment.processor.constants.AppConstants.CARD_PAYMENT_LAYOUT_SIZE;

@Component
public class CardPaymentInputFormatFilter implements GenericSelector<String> {

    @Override
    public boolean accept(String source) {

        String [] parts = source.split(CARD_PAYMENT_LAYOUT_REGEX);
        return parts.length == CARD_PAYMENT_LAYOUT_SIZE;
    }
}
