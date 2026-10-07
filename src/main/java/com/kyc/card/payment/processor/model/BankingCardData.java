package com.kyc.card.payment.processor.model;

import com.kyc.card.payment.processor.enums.CardTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankingCardData {

    private Long card;
    private BigDecimal balance;
    private CardTypeEnum cardType;
    private boolean active;
    private boolean valid;
}
