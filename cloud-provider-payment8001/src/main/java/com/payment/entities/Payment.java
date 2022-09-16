package com.payment.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 支付类
 *
 * @author elijah
 * @version 1.0
 * @date 2022/9/16 15:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment implements Serializable {

    private Long id;
    /**
     * 流水号
     */
    private String serial;
}
