package com.payment.dao;

import com.payment.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: elijah
 * @Title: PaymentDao
 * @Description: 支付dao
 * @Date: 2022/9/16 15:27
 */
@Mapper
public interface PaymentDao {

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
    Payment getPaymentById(@Param("id") Long id);
}
