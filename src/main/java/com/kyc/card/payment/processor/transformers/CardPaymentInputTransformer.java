package com.kyc.card.payment.processor.transformers;

import com.kyc.card.payment.processor.model.KycCardPaymentInputData;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.stereotype.Component;

import static com.kyc.card.payment.processor.constants.AppConstants.CARD_PAYMENT_LAYOUT_ACCOUNT;
import static com.kyc.card.payment.processor.constants.AppConstants.CARD_PAYMENT_LAYOUT_AMOUNT;
import static com.kyc.card.payment.processor.constants.AppConstants.CARD_PAYMENT_LAYOUT_CUSTOMER;
import static com.kyc.card.payment.processor.constants.AppConstants.CARD_PAYMENT_LAYOUT_DATE;
import static com.kyc.card.payment.processor.constants.AppConstants.CARD_PAYMENT_LAYOUT_FOLIO;
import static com.kyc.card.payment.processor.constants.AppConstants.CARD_PAYMENT_LAYOUT_METHOD;
import static com.kyc.card.payment.processor.constants.AppConstants.CARD_PAYMENT_LAYOUT_MOTIVE;
import static com.kyc.card.payment.processor.constants.AppConstants.CARD_PAYMENT_LAYOUT_OFFICE;
import static com.kyc.card.payment.processor.constants.AppConstants.CARD_PAYMENT_LAYOUT_REGEX;


@Component
public class CardPaymentInputTransformer implements GenericTransformer<String,KycCardPaymentInputData> {

    @Override
    public KycCardPaymentInputData transform(String payload) {

        String [] parts = payload.split(CARD_PAYMENT_LAYOUT_REGEX);

        return KycCardPaymentInputData.builder()
                .folio(parts[CARD_PAYMENT_LAYOUT_FOLIO])
                .account(parts[CARD_PAYMENT_LAYOUT_ACCOUNT])
                .method(parts[CARD_PAYMENT_LAYOUT_METHOD])
                .amount(parts[CARD_PAYMENT_LAYOUT_AMOUNT])
                .motive(parts[CARD_PAYMENT_LAYOUT_MOTIVE])
                .office(parts[CARD_PAYMENT_LAYOUT_OFFICE])
                .customer(parts[CARD_PAYMENT_LAYOUT_CUSTOMER])
                .date(parts[CARD_PAYMENT_LAYOUT_DATE])
                .build();
    }
}
