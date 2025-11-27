package com.trionesdev.payment.alipay.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "triones.payment.alipay")
@Data
public class AlipayPayProperties {
    private Boolean enabled;
    private String appId;
    private String privateKey;
    private String alipayPublicKey;
    private String appCertPath;
    private String alipayPublicCertPath;
    private String rootCertPath;
    private String appCertContent;
    private String alipayPublicCertContent;
    private String rootCertContent;

    private String notifyUrl;
    private Map<String, String> notifyUrls;
}
