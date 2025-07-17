package com.trionesdev.payment.alipay.spring.boot.rest;

import com.trionesdev.payment.alipay.v3.Alipay;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payment/alipay")
public class AlipayResource {
    private final ObjectProvider<AlipayNotifyCallback> notifyCallbackObjectFactory;
    private final Alipay alipay;

    @PostMapping("/notify")
    public String alipayNotify(
            HttpServletRequest request,
            @RequestHeader("alipay-signature") String signature,
            @RequestHeader("alipay-sn") String sn,
            @RequestHeader("alipay-timestamp") String timestamp,
            @RequestHeader("alipay-nonce") String nonce
    ) {
        Map<String, String[]> params = request.getParameterMap();
        return "success";
    }

}
