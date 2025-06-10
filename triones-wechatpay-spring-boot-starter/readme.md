# WechatPay Spring Boot 自动装配
> 对 triones-payment-wechatpay 进行Spring Boot 自动装配

## 使用
### 添加maven依赖
```xml
<dependency>
    <groupId>com.trionesdev.payment</groupId>
    <artifactId>triones-wechatpay-spring-boot-starter</artifactId>
    <version>版本号</version>
</dependency>
```

## 配置项说明

| 属性                    | 说明          | 是否必须 | 默认值   |
|-----------------------|-------------|------|-------|
| enabled               | 是否企业微信支付    | 否    | false |
| appId                 | 微信AppId     |      |       |
| mchId                 | 商户ID        | 是    |       |
| apiV3Key              | V3版本接口key   | 是    |       |
| privateKey            | 私钥          |      |       |
| privateCert           | 证书          |      |       |
| privateKeyBase64      | base64格式的私钥 |      |       |
| privateCertBase64     | base64格式的证书 |      |       |
| privateKeyPath        | 私钥地址        |      |       |
| privateCertPath       | 证书地址        |      |       |
| transactionNotifyUrl  | 交易回调地址      |      |       |
| refundNotifyUrl       | 退款回调地址      |      |       |
| transferNotifyUrl     | 交易回调地址      |      |       |
| transactionNotifyUrls | 交易回调地址map   |      |       |
| refundNotifyUrls      | 交易回调地址map   |      |       |
| transferNotifyUrls    | 交易回调地址map   |      |       |

## 使用
获取 WechatPay Bean 进行操做 方法列表参考 [WechatPay](https://github.com/trionesdev/triones-payment/blob/develop/triones-payment-wechatpay/readme.md)
