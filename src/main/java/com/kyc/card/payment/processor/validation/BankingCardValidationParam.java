package com.kyc.card.payment.processor.validation;

import com.kyc.card.payment.processor.model.BankingCardData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.function.Function;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankingCardValidationParam {

    private Function<BankingCardData,Boolean> validationFunction;
    private String errorType;
}
