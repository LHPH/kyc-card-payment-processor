package com.kyc.card.payment.processor.handlers;

import com.kyc.card.payment.processor.enums.CardTypeEnum;
import com.kyc.card.payment.processor.model.KycCardPaymentInputData;
import com.kyc.core.exception.KycException;
import com.kyc.core.properties.KycMessages;
import com.kyc.core.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.core.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.kyc.card.payment.processor.constants.KycChannelConstants.ERROR_MESSAGE_002;

@Component
public class ValidationDataMessageHandler implements GenericHandler<KycCardPaymentInputData> {

    @Autowired
    private KycMessages kycMessages;

    @Override
    public @Nullable Object handle(KycCardPaymentInputData payload, MessageHeaders headers) {

        String auth = payload.getAuthorization();
        String method = payload.getMethod();
        String card = payload.getAccount();
        String amount = payload.getAmount();
        String customer = payload.getCustomer();
        String date = payload.getDate();

        if(!checkMethod(method)){
            throw createException(payload,"Invalid method");
        }

        if(!checkAmount(amount)){
            throw createException(payload,"Invalid amount");
        }

        if(!checkCustomer(customer)){
            throw createException(payload,"Invalid customer number");
        }

        if(!checkFormatCard(card)){
            throw createException(payload,"Invalid card format");
        }

        if(!checkDate(date)){
            throw createException(payload,"Invalid date");
        }

        return payload;
    }

    private boolean checkMethod(String method) {

        CardTypeEnum type =  CardTypeEnum.getInstance(method);
        return !CardTypeEnum.UK.equals(type);
    }

    private boolean checkAmount(String amount) {

        try{
            BigDecimal bd = new BigDecimal(amount);
            return bd.compareTo(BigDecimal.ZERO) > 0;
        }
        catch(NumberFormatException ex){
            return false;
        }
    }

    private boolean checkCustomer(String customer) {
        return StringUtils.isNumeric(customer);
    }

    private boolean checkDate(String date){

        LocalDateTime localDateTime = DateUtil.stringToLocalDateTime(date,"yyyyMMddHHmmss");
        if(localDateTime!=null){

            LocalDateTime now = LocalDateTime.now();
            return localDateTime.isBefore(now) || localDateTime.isEqual(now);
        }
        return false;
    }

    private boolean checkFormatCard(String card){

        return StringUtils.isNumeric(card) && StringUtils.length(card) == 16;
    }

    private KycException createException(KycCardPaymentInputData payload, String errorMessage){

        throw KycException.builder()
                .inputData(payload)
                .errorData(kycMessages.getMessage(ERROR_MESSAGE_002))
                .outputData(errorMessage)
                .build();
    }
}
