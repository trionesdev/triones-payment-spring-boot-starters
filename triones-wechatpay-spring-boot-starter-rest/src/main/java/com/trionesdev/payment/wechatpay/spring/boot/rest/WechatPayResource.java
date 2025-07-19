package com.trionesdev.payment.wechatpay.spring.boot.rest;

import com.trionesdev.payment.util.GsonUtils;
import com.trionesdev.payment.wechatpay.v3.WechatPay;
import com.trionesdev.payment.wechatpay.v3.payment.model.notify.WechatPayNotifyParseRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payment/wechatpay")
public class WechatPayResource {

    private final ObjectProvider<WechatPayNotifyCallback> notifyCallbackObjectFactory;
    private final WechatPay wechatPay;

    /**
     * 支付回调
     *
     * @param nonce nonce
     * @param signature signature
     * @param timestamp 时间戳
     * @param serial 序列号
     * @param body 内容
     * @param servletResponse HttpServletResponse
     * @return 结果
     */
    @PostMapping(value = "transaction-notify")
    public TransactionNotifyVO transactionNotify(
            @RequestHeader("Wechatpay-Nonce") String nonce,
            @RequestHeader("Wechatpay-Signature") String signature,
            @RequestHeader("Wechatpay-Timestamp") String timestamp,
            @RequestHeader("Wechatpay-Serial") String serial,
            @RequestBody String body,
            HttpServletResponse servletResponse
    ) {
        try {
            var request = WechatPayNotifyParseRequest.builder().nonce(nonce).signature(signature).timestamp(timestamp).serial(serial).body(body).build();
            var response = wechatPay.getPayment().transactionNotify(request);
            var callback = notifyCallbackObjectFactory.getIfAvailable();
            if (callback != null) {
                callback.transactionNotify(response);
            }
        } catch (Exception e) {
            try {
                servletResponse.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), GsonUtils.toJson(TransactionNotifyVO.builder().code("FAIL").message(e.getMessage()).build()));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return TransactionNotifyVO.builder().code("SUCCESS").message("Transaction notify success").build();
    }

    /**
     * 退款回调
     *
     * @param nonce nonce
     * @param signature signature
     * @param timestamp 时间戳
     * @param serial 序列号
     * @param body 内容
     * @param servletResponse HttpServletResponse
     * @return 结果
     */
    @PostMapping(value = "refund-notify")
    public TransactionNotifyVO refundNotify(
            @RequestHeader("Wechatpay-Nonce") String nonce,
            @RequestHeader("Wechatpay-Signature") String signature,
            @RequestHeader("Wechatpay-Timestamp") String timestamp,
            @RequestHeader("Wechatpay-Serial") String serial,
            @RequestBody String body,
            HttpServletResponse servletResponse
    ) {
        try {
            var request = WechatPayNotifyParseRequest.builder().nonce(nonce).signature(signature).timestamp(timestamp).serial(serial).body(body).build();
            var response = wechatPay.getPayment().refundNotify(request);
            var callback = notifyCallbackObjectFactory.getIfAvailable();
            if (callback != null) {
                callback.refundNotify(response);
            }
        } catch (Exception e) {
            try {
                servletResponse.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), GsonUtils.toJson(TransactionNotifyVO.builder().code("FAIL").message(e.getMessage()).build()));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return TransactionNotifyVO.builder().code("SUCCESS").message("Transaction notify success").build();
    }


    /**
     * 转账回调
     *
     * @param nonce nonce
     * @param signature signature
     * @param timestamp 时间戳
     * @param serial 序列号
     * @param body 内容
     * @param servletResponse HttpServletResponse
     * @return 结果
     */
    @PostMapping(value = "transfer-notify")
    public TransactionNotifyVO transferNotify(
            @RequestHeader("Wechatpay-Nonce") String nonce,
            @RequestHeader("Wechatpay-Signature") String signature,
            @RequestHeader("Wechatpay-Timestamp") String timestamp,
            @RequestHeader("Wechatpay-Serial") String serial,
            @RequestBody String body,
            HttpServletResponse servletResponse
    ) {
        try {
            var request = WechatPayNotifyParseRequest.builder().nonce(nonce).signature(signature).timestamp(timestamp).serial(serial).body(body).build();
            var response = wechatPay.getOperation().transferNotify(request);
            var callback = notifyCallbackObjectFactory.getIfAvailable();
            if (callback != null) {
                callback.transferNotify(response);
            }
        } catch (Exception e) {
            try {
                servletResponse.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), GsonUtils.toJson(TransactionNotifyVO.builder().code("FAIL").message(e.getMessage()).build()));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return TransactionNotifyVO.builder().code("SUCCESS").message("Transfer notify success").build();
    }

}
