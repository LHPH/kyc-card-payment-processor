package com.kyc.card.payment.processor.config.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IpFilterProperties {

    private boolean enabled;
    private Set<String> allowedIp = Set.of();
}
