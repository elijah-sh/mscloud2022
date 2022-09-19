package com.order.order.controller;

import com.order.entities.CommonResult;
import com.order.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @Author: elijah
 * @Title: OrderController
 * @Description:
 * @Date: 2022/9/19 19:14
 */
@RestController
@Slf4j
public class OrderController {

    public static final String PAYMENT_URL = "http://127.0.0.1:8001/";
    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/create/payment")
    public CommonResult<Payment> create(Payment payment) {
        log.info("consumer {}", payment.getSerial());
        return restTemplate.postForObject(PAYMENT_URL + "payment/create", payment, CommonResult.class);
    }

    @GetMapping("/consumer/get/payment/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "get/payment/" + id, CommonResult.class);
    }

}
