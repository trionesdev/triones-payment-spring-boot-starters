package com.trionesdev.payment.alipay.spring.boot.rest;

import com.trionesdev.payment.alipay.v3.Alipay;
import com.trionesdev.payment.alipay.v3.modal.AlipayNotifyModel;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payment/alipay")
public class AlipayResource {
    Logger logger = LoggerFactory.getLogger(AlipayResource.class);
    private final ObjectProvider<AlipayNotifyCallback> notifyCallbackObjectFactory;
    private final Alipay alipay;

    @PostMapping("/notify")
    public String alipayNotify(
            HttpServletRequest request
    ) {
        String tradeStatus = request.getParameter("trade_status");
        if ("TRADE_SUCCESS".equals(tradeStatus)) {
            try {
                AlipayNotifyModel model = alipay.getPayment().notifyParseFromMaps(request.getParameterMap());
                AlipayNotifyCallback callback = notifyCallbackObjectFactory.getIfAvailable();
                if (callback != null) {
                    callback.transactionNotify(model);
                }
            } catch (Exception e) {
                logger.error("alipayNotify error", e);
                return "fail";
            }
            return "success";
        }
        return "fail";
    }

}
