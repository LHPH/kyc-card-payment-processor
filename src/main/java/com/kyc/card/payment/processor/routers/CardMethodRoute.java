package com.kyc.card.payment.processor.routers;

import com.kyc.card.payment.processor.enums.CardTypeEnum;
import com.kyc.card.payment.processor.model.PaymentOperationDTO;
import org.springframework.stereotype.Component;

@Component
public class CardMethodRoute {

    public CardTypeEnum resolve(PaymentOperationDTO data){

        return CardTypeEnum.getInstance(data.getKycCardPaymentInputData().getMethod());
    }
}
