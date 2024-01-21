package com.trionesdev.payment.wxpay.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(value = {WxPayClientRegister.class})
public @interface EnableWxPayClients {
    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

    Class<?>[] clients() default {};
}
