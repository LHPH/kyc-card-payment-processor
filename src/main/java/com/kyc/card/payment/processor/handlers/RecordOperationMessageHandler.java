package com.kyc.card.payment.processor.handlers;

import com.kyc.card.payment.processor.entity.KycPaymentOperationData;

import static com.kyc.card.payment.processor.constants.KycChannelConstants.ERROR_MESSAGE_003;
import static com.kyc.card.payment.processor.constants.KycChannelConstants.ERROR_MESSAGE_004;
import static com.kyc.card.payment.processor.entity.KycPaymentOperationData.PaymentOperationSource;

import com.kyc.card.payment.processor.enums.PaymentOperationStatusEnum;
import com.kyc.card.payment.processor.model.KycCardPaymentInputData;
import com.kyc.card.payment.processor.model.PaymentOperationDTO;
import com.kyc.card.payment.processor.repository.PaymentOperationRepository;
import com.kyc.core.exception.KycException;
import com.kyc.core.properties.KycMessages;
import com.kyc.core.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.integration.core.GenericHandler;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class RecordOperationMessageHandler implements GenericHandler<KycCardPaymentInputData> {

    @Autowired
    private PaymentOperationRepository paymentOperationRepository;

    @Autowired
    private KycMessages kycMessages;

    @Override
    public @Nullable Object handle(KycCardPaymentInputData payload, MessageHeaders headers) {


        try{

           int id = payload.hashCode();
           log.info("{}",id);
           paymentOperationRepository.findById(id)
                   .ifPresent(operation -> {

                   throw KycException.builder()
                           .inputData(payload)
                           .errorData(kycMessages.getMessage(ERROR_MESSAGE_004))
                           .outputData(payload)
                           .build();
           });

            PaymentOperationSource source = PaymentOperationSource.builder()
                    .ipAddress(String.valueOf(headers.get(IpHeaders.IP_ADDRESS)))
                    .ipHostname(String.valueOf(headers.get(IpHeaders.HOSTNAME)))
                    .ipRemotePort(String.valueOf(headers.get(IpHeaders.REMOTE_PORT)))
                    .ipConnectionId(String.valueOf(headers.get(IpHeaders.CONNECTION_ID)))
                    .timestamp(headers.getTimestamp())
                    .build();

            KycPaymentOperationData kycPaymentOperationData = KycPaymentOperationData.builder()
                    .id(id)
                    .operation(payload.getOperation())
                    .source(source)
                    .authorization(payload.getAuthorization())
                    .method(payload.getMethod())
                    .folio(Long.parseLong(payload.getFolio()))
                    .account(payload.getAccount())
                    .amount(new BigDecimal(payload.getAmount()))
                    .motive(payload.getMotive())
                    .office(payload.getOffice())
                    .customer(Integer.parseInt(payload.getCustomer()))
                    .date(DateUtil.stringToLocalDateTime(payload.getDate(),"yyyyMMddHHmmss"))
                    .status(PaymentOperationStatusEnum.REGISTERED)
                    .build();

            KycPaymentOperationData result = paymentOperationRepository.save(kycPaymentOperationData);

            return new PaymentOperationDTO(payload,result);
        }
        catch(DataAccessException ex){
            throw KycException.builder()
                    .inputData(payload)
                    .exception(ex)
                    .errorData(kycMessages.getMessage(ERROR_MESSAGE_003))
                    .outputData(payload)
                    .build();
        }
    }
}
