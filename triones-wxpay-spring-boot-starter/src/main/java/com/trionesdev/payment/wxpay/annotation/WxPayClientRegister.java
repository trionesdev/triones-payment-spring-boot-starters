package com.trionesdev.payment.wxpay.annotation;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class WxPayClientRegister implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {
    private ResourceLoader resourceLoader;

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        registerWxPayClients(importingClassMetadata, registry);
    }

    private void registerWxPayClients(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        LinkedHashSet<BeanDefinition> candidateComponents = new LinkedHashSet<>();
        Map<String, Object> attrs = metadata.getAnnotationAttributes(EnableWxPayClients.class.getName());
        final Class<?>[] channels = attrs == null ? null : (Class<?>[]) attrs.get("clients");
        if (channels == null || channels.length == 0) {
            ClassPathScanningCandidateComponentProvider scanner = getScanner();
            scanner.setResourceLoader(this.resourceLoader);
            scanner.addIncludeFilter(new AnnotationTypeFilter(WxPayClient.class));
            Set<String> basePackages = getBasePackages(metadata);
            for (String basePackage : basePackages) {
                candidateComponents.addAll(scanner.findCandidateComponents(basePackage));
            }
        } else {
            for (Class<?> clazz : channels) {
                candidateComponents.add(new AnnotatedGenericBeanDefinition(clazz));
            }
        }

        for (BeanDefinition candidateComponent : candidateComponents) {
            if (candidateComponent instanceof AnnotatedBeanDefinition) {
                AnnotatedBeanDefinition beanDefinition = (AnnotatedBeanDefinition) candidateComponent;
                AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();
                Map<String, Object> attributes = annotationMetadata
                        .getAnnotationAttributes(WxPayClient.class.getCanonicalName());
                registerWxPayClient(registry, annotationMetadata, attributes);
            }
        }
    }

    private void registerWxPayClient(BeanDefinitionRegistry registry, AnnotationMetadata annotationMetadata,
                                     Map<String, Object> attributes) {
        String className = annotationMetadata.getClassName();
        ConfigurableBeanFactory beanFactory = registry instanceof ConfigurableBeanFactory
                ? (ConfigurableBeanFactory) registry : null;
        Class clazz = ClassUtils.resolveClassName(className, null);
        WxPayClientFactoryBean factoryBean = new WxPayClientFactoryBean();
        factoryBean.setBeanFactory(beanFactory);
        factoryBean.setType(clazz);
        BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(clazz, () -> {
            factoryBean.setAppId(getAppId(beanFactory, attributes));
            factoryBean.setMchId(getMchId(beanFactory, attributes));
            factoryBean.setApiV3Key(getApiV3Key(beanFactory, attributes));
            factoryBean.setPrivateKey(getPrivateKey(beanFactory, attributes));
            factoryBean.setPrivateCert(getPrivateCert(beanFactory, attributes));
            factoryBean.setPrivateKeyBase64(getPrivateKeyBase64(beanFactory, attributes));
            factoryBean.setPrivateCertBase64(getPrivateCertBase64(beanFactory, attributes));
            factoryBean.setPrivateKeyPath(getPrivateKeyPath(beanFactory, attributes));
            factoryBean.setPrivateCert(getPrivateCertPath(beanFactory, attributes));
            factoryBean.setTransactionNotifyUrl(getTransactionNotifyUrl(beanFactory, attributes));
            factoryBean.setTransactionNotifyUrl(getRefundNotifyUrl(beanFactory, attributes));
            return factoryBean.getObject();
        });
        AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();
        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        definition.setLazyInit(true);

        BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, className, null);
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
    }

    protected ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, this.environment) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                boolean isCandidate = false;
                if (beanDefinition.getMetadata().isIndependent()) {
                    if (!beanDefinition.getMetadata().isAnnotation()) {
                        isCandidate = true;
                    }
                }
                return isCandidate;
            }
        };
    }

    protected Set<String> getBasePackages(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> attributes = importingClassMetadata
                .getAnnotationAttributes(EnableWxPayClients.class.getCanonicalName());

        Set<String> basePackages = new HashSet<>();
        for (String pkg : (String[]) attributes.get("value")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        for (String pkg : (String[]) attributes.get("basePackages")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        for (Class<?> clazz : (Class[]) attributes.get("basePackageClasses")) {
            basePackages.add(ClassUtils.getPackageName(clazz));
        }

        if (basePackages.isEmpty()) {
            basePackages.add(ClassUtils.getPackageName(importingClassMetadata.getClassName()));
        }
        return basePackages;
    }

    private String resolve(ConfigurableBeanFactory beanFactory, String value) {
        if (StringUtils.hasText(value)) {
            if (beanFactory == null) {
                return this.environment.resolvePlaceholders(value);
            }
            BeanExpressionResolver resolver = beanFactory.getBeanExpressionResolver();
            String resolved = beanFactory.resolveEmbeddedValue(value);
            if (resolver == null) {
                return resolved;
            }
            Object evaluateValue = resolver.evaluate(resolved, new BeanExpressionContext(beanFactory, null));
            if (evaluateValue != null) {
                return String.valueOf(evaluateValue);
            }
            return null;
        }
        return value;
    }

    String getAppId(ConfigurableBeanFactory beanFactory, Map<String, Object> attributes) {
        String appId = (String) attributes.get("appId");
        return resolve(beanFactory, appId);
    }

    String getMchId(ConfigurableBeanFactory beanFactory, Map<String, Object> attributes) {
        String appId = (String) attributes.get("mchId");
        return resolve(beanFactory, appId);
    }

    String getApiV3Key(ConfigurableBeanFactory beanFactory, Map<String, Object> attributes) {
        String appId = (String) attributes.get("apiV3Key");
        return resolve(beanFactory, appId);
    }

    String getPrivateKey(ConfigurableBeanFactory beanFactory, Map<String, Object> attributes) {
        String appId = (String) attributes.get("privateKey");
        return resolve(beanFactory, appId);
    }

    String getPrivateCert(ConfigurableBeanFactory beanFactory, Map<String, Object> attributes) {
        String appId = (String) attributes.get("privateCert");
        return resolve(beanFactory, appId);
    }

    String getPrivateKeyBase64(ConfigurableBeanFactory beanFactory, Map<String, Object> attributes) {
        String appId = (String) attributes.get("privateKeyBase64");
        return resolve(beanFactory, appId);
    }

    String getPrivateCertBase64(ConfigurableBeanFactory beanFactory, Map<String, Object> attributes) {
        String appId = (String) attributes.get("privateCertBase64");
        return resolve(beanFactory, appId);
    }

    String getPrivateKeyPath(ConfigurableBeanFactory beanFactory, Map<String, Object> attributes) {
        String appId = (String) attributes.get("privateKeyPath");
        return resolve(beanFactory, appId);
    }

    String getPrivateCertPath(ConfigurableBeanFactory beanFactory, Map<String, Object> attributes) {
        String appId = (String) attributes.get("privateCertPath");
        return resolve(beanFactory, appId);
    }

    String getTransactionNotifyUrl(ConfigurableBeanFactory beanFactory, Map<String, Object> attributes) {
        String appId = (String) attributes.get("transactionNotifyUrl");
        return resolve(beanFactory, appId);
    }

    String getRefundNotifyUrl(ConfigurableBeanFactory beanFactory, Map<String, Object> attributes) {
        String appId = (String) attributes.get("refundNotifyUrl");
        return resolve(beanFactory, appId);
    }

}
