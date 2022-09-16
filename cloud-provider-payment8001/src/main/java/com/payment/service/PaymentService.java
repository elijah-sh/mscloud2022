package com.payment.service;

import com.payment.entities.Payment;

/**
 *
 * @author elijah
 * @version 1.0
 * @date 2022/9/16 16:50
 */
public interface PaymentService {
    /**
     * 新增数据
     *
     * @param payment
     * @return id值
     */
    int create(Payment payment);

    /**
     * 通过ID查找数据
     * @param id 主键
     * @return
     */
    Payment getPaymentById(Long id);
}
