package com.kyc.card.payment.processor.repository;

import com.kyc.card.payment.processor.entity.KycPaymentOperationData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentOperationRepository extends MongoRepository<KycPaymentOperationData, Integer> {
}
