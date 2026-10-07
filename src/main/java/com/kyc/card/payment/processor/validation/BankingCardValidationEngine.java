package com.kyc.card.payment.processor.validation;

import com.kyc.card.payment.processor.enums.PaymentOperationStatusEnum;
import com.kyc.card.payment.processor.model.BankingCardData;
import org.springframework.stereotype.Component;

@Component
public class BankingCardValidationEngine {

    public BankingCardValidationResult validate(BankingCardData bankingCardData, BankingCardValidationParam param) {

        Boolean result = param.getValidationFunction().apply(bankingCardData);
        if(result){
            return new BankingCardValidationResult(PaymentOperationStatusEnum.SUCCESS,null);
        }
        return new BankingCardValidationResult(PaymentOperationStatusEnum.FAILED,param.getErrorType());
    }
}
