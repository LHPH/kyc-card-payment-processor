package com.kyc.card.payment.processor.repository;

import com.kyc.card.payment.processor.entity.KycPaymentOperationData;
import com.kyc.card.payment.processor.enums.PaymentOperationStatusEnum;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PaymentOperationRepository extends MongoRepository<KycPaymentOperationData, Long> {

    Optional<KycPaymentOperationData> findByFolioAndStatus(Long folio, PaymentOperationStatusEnum status);
}
