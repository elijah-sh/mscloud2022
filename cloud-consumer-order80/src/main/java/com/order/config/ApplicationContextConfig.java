package com.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: elijah
 * @Title: ApplicationContextConfig
 * @Description:
 * @Date: 2022/9/19 17:42
 */

@Configuration
public class ApplicationContextConfig {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
    // 类似与  application.xml <bean id = "" class="" />
}
