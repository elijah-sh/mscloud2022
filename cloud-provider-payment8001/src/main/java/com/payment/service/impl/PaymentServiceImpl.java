package com.payment.service.impl;

import com.payment.dao.PaymentDao;
import com.payment.entities.Payment;
import com.payment.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: elijah
 * @Title: PaymentServiceImpl
 * @Description:
 * @Date: 2022/9/16 16:51
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
