package com.payment.controller;

import com.payment.entities.CommonResult;
import com.payment.entities.Payment;
import com.payment.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: elijah
 * @Title: PaymentController
 * @Description:
 * @Date: 2022/9/16 16:56
 */
@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @PostMapping(value = "/payment/create")
    public CommonResult create(Payment payment){

       int result = paymentService.create(payment);
       log.info("insert result id {}， serial ", result);
        if (result > 0) {
            return new CommonResult(200, "insert success", payment);
        } else {
            return new CommonResult(444, "insert error", null);
        }
    }

    @GetMapping(value = "/get/payment/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){

        Payment payment = paymentService.getPaymentById(id);
        log.info("get result id : {} ", payment);
        if (payment != null) {
            return new CommonResult(200, "select success", payment);
        } else {
            return new CommonResult(404, "select is null", null);
        }
    }
}
