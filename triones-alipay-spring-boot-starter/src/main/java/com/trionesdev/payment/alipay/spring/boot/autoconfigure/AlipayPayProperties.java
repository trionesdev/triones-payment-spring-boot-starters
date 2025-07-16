package com.trionesdev.payment.alipay.spring.boot.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "triones.payment.alipay")
@Data
public class AlipayPayProperties {
    private Boolean enabled;
}
