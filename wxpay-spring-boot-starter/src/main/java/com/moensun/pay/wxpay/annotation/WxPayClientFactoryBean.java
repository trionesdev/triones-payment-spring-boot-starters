package com.moensun.pay.wxpay.annotation;

import com.moensun.pay.wxpay.v3.WxPay;
import com.moensun.pay.wxpay.v3.payment.WxPayConfig;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Base64;

@Setter
public class WxPayClientFactoryBean implements FactoryBean<Object>, InitializingBean,
        ApplicationContextAware, BeanFactoryAware {

    private String appId;
    private String mchId;
    private String apiV3Key;
    private String privateKey;
    private String privateCert;
    private String privateKeyBase64;
    private String privateCertBase64;
    private String privateKeyPath;
    private String privateCertPath;
    private String transactionNotifyUrl;
    private String refundNotifyUrl;

    private Class<?> type;
    private BeanFactory beanFactory;
    private ApplicationContext applicationContext;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object getObject() {
        return getTarget();
    }

    @Override
    public Class<?> getObjectType() {
        return this.type;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    protected <T> T getTarget() {

        if (StringUtils.isBlank(privateKey) && StringUtils.isNotBlank(privateKeyBase64)) {
            privateKey = new String(Base64.getDecoder().decode(privateKeyBase64));
        }
        if (StringUtils.isBlank(privateCert) && StringUtils.isNotBlank(privateCertBase64)) {
            privateCert = new String(Base64.getDecoder().decode(privateCertBase64));
        }
        WxPayConfig wxPayConfig = WxPayConfig.builder()
                .appId(appId)
                .mchId(mchId)
                .privateKey(privateKey)
                .privateCert(privateCert)
                .privateKeyPath(privateKeyPath)
                .privateCertPath(privateCertPath)
                .apiV3Key(apiV3Key)
                .transactionNotifyUrl(transactionNotifyUrl)
                .refundNotifyUrl(refundNotifyUrl)
                .build();
        WxPay wxPay = new WxPay(wxPayConfig);
        return (T) this.type.cast(Proxy.newProxyInstance(this.type.getClassLoader(), new Class[]{this.type}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return method.invoke(wxPay, args);
            }
        }));
    }
}
