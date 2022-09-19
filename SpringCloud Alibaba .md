

#### 微服务架构理论



<img src="SpringCloud Alibaba .assets/image-20220902111832727.png" alt="image-20220902111832727" style="zoom:50%;" />



![image-20220902112326373](SpringCloud Alibaba .assets/image-20220902112326373.png)





<img src="SpringCloud Alibaba .assets/image-20220902112523515.png" alt="image-20220902112523515" style="zoom:50%;" />





#### Boot和Cloud版本





<img src="SpringCloud Alibaba .assets/image-20220902115725085.png" alt="image-20220902115725085" style="zoom:50%;" />





<img src="SpringCloud Alibaba .assets/image-20220902150609292.png" alt="image-20220902150609292" style="zoom:50%;" />





约定》配置〉编码

maven父工程

###### dependencyManagement

Maven 使用 dependencyManagement元素来提供一种管理依赖版本号的方式

通常在一个组织或者项目的最顶层的父POM中看到depencyManagement元素



使用pom.xml中的dependencyManagement元素让所有在子项目中的引用一个依赖而不用显式列出版本号.

Maven会沿着父子层级向上走,直到找到一个拥有dependencyManagement元素的项目,然后使用dependencyManagement元素指定的版本号, 避免子项目的重复声明

dependencyManagement只声明依赖,不实现引入.需要子项目去引依赖



#### 构建项目



###### maven父工程



pom文件

```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.elijah.cloud</groupId>
  <artifactId>cloud2022</artifactId>
  <version>1.0-SNAPSHOT</version>
  <packaging>pom</packaging>

  <modules>
    <module>cloud-provider-payment8001</module>
  </modules>


  <!-- 统一jar包版本 -->
  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
    <junit.version>4.12</junit.version>
    <log4j.version>1.2.17</log4j.version>
    <lombok.version>1.2.17</lombok.version>
    <mysql.version>5.1.47</mysql.version>
    <druid.version>1.1.16</druid.version>
    <mybatis.spring.boot.version>1.3.0</mybatis.spring.boot.version>
  </properties>

<!--  子模块继承之后， 提供作用：锁定版本+子module 不用写groupId和version-->
  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-dependencies</artifactId>
        <version>2.2.2.RELEASE</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>

      <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-dependencies</artifactId>
        <version>Hoxton.SR1</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>

      <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-alibaba-dependencies</artifactId>
        <version>2.1.0.RELEASE</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>

      <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>${mysql.version}</version>
      </dependency>
      <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid</artifactId>
        <version>${druid.version}</version>
      </dependency>

      <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-sql-spring-boot-starter</artifactId>
        <version>${mybatis.spring.boot.version}</version>
      </dependency>
    </dependencies>
  </dependencyManagement>


</project>

```



1、cloud-provider-payment8001 微服务提供者支付 

2、cloud-consumer-order80 微服务消费者订单



下单之后会调用支付服务

消费者会调用提供者



微服务模版开发步骤

1、建module

2、改POM

3、写YML

4、主启动

5、业务类





热部署只允许在开发环境使用,不然在生产环节以更变代码就重启了





#### 支付模版

cloud-provider-payment8001 微服务提供者支付 

包含业务处理逻辑



##### 配置代码

新建模块cloud-provider-payment8001



修改pom文件



```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.elijah.cloud</groupId>
        <artifactId>cloud2022</artifactId>
        <version>1.0-SNAPSHOT</version>
        <relativePath/>
    </parent>
    <artifactId>cloud-provider-payment8001</artifactId>
    <name>cloud-provider-payment8001</name>
    <description>cloud-provider-payment8001</description>
    <properties>
        <java.version>1.8</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>

    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
```



Application.yml

```yaml
server:
  port: 8001

spring:
  application:
    name: cloud-payment-service
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: org.gjt.mm.mysql.Driver
    url: jdbc:mysql://127.0.0.1:3306/db2022?useUnicode=true&characterEncoding=utf-8&useSSL=false
    username: root
    password: mysql77&

mybatis:
  mapperLocations: classpath:mapper/*.xml
  type-aliases-package: com.payment.entity
```



启动类

```
public class CloudProviderPayment8001Application {

    public static void main(String[] args) {
        SpringApplication.run(CloudProviderPayment8001Application.class, args);
    }

}
```



##### 业务代码



对于业务逻辑的开发过程,一般从前端到后端的开发

vue——controller——service——dao——mysql

- 建表SQL
- entities
- dao
- service
- controller



###### 建表SQL

```sql
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db2022` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `db2022`;

/*Table structure for table `t_admin` */

DROP TABLE IF EXISTS `payment`;


create table payment
(
	id bigint auto_increment,
	serial varchar(200) default '' null,
	constraint payment_pk
		primary key (id)
)ENGINE = InnoDB
  auto_increment = 1
  DEFAULT CHARSET = utf8 COMMENT ='支付表';

```



###### entities

Payment.java

```java
package com.payment.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
```



与前端交互类

```java
package com.payment.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: elijah
 * @Title: CommonResult
 * @Description: 通过返回结果类 json 串
 * @Date: 2022/9/16 15:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult<T> {
    private Integer code;
    private String message;
    private T data;

    public CommonResult(Integer code, String message) {
        this(code, message, null);
    }
}
```



###### dao



```java
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
```



mapper

```xml
<?xml version="1.0" encoding="UTF-8"?>

<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.payment.dao.PaymentDao">
    
    <resultMap id="BaseResultMap" type="com.payment.entities.Payment">
    <id column="id" property="id" jdbcType="BIGINT"/>
        <result column="serial" property="serial" jdbcType="VARCHAR"/>
    </resultMap>

    <insert id="create" parameterType="Payment" useGeneratedKeys="true" keyProperty="id">
        insert into payment (serial) values (#{serial})
    </insert>

    <select id="getPaymentById" parameterType="Long" resultMap="BaseResultMap">
        select * from payment where id = #{id};
    </select>
</mapper>
```



###### service

```java
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
```



实现类

```java
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
```



###### controller



```java
package com.payment.controller;

import com.payment.entities.CommonResult;
import com.payment.entities.Payment;
import com.payment.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
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
       log.info("insert result id {}", result);
        if (result > 0) {
            return new CommonResult(200, "insert success", payment);
        } else {
            return new CommonResult(444, "insert error", null);
        }
    }

    @GetMapping(value = "/get/payment/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){

        Payment payment = paymentService.getPaymentById(id);
        log.info("get result id {}", payment);
        if (payment != null) {
            return new CommonResult(200, "select success", payment);
        } else {
            return new CommonResult(404, "select is null", null);
        }
    }
}
```



#### 热部署插件

当项目修改代码时,会自动重启项目

热部署只允许在开发环境使用,不然在生产环节以更变代码就重启了





1、修改工程pom.xml

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```



2、修改父工程pom.xml 添加插件plugin

```xml
<build>
  <plugins>
    <plugin>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-maven-plugin</artifactId>
      <configuration>
        <fork>true</fork>
        <addResources>true</addResources>
      </configuration>
    </plugin>
  </plugins>
</build>
```



3、设置idea 代码自动编译 ABCD勾选

4、idea设置 update the value of  搜索registry

compiler.automae.allow.when.app.running 打勾

也看个人习惯,是否去设置



---

#### 视频地址





9,09_支付模块构建(中).mp4,http://p8t8qct2x.bkt.clouddn.com/624901110-DEC6FC63A9A26E41.mp4
10,10_支付模块构建(下).mp4,http://p8t8qct2x.bkt.clouddn.com/624901110-2FEE19AD64CAC40E.mp4
11,11_热部署Devtools.mp4,http://p8t8qct2x.bkt.clouddn.com/624901110-355C4CE840BDAF3B.mp4
12,12_消费者订单模块(上).mp4,http://p8t8qct2x.bkt.clouddn.com/624901110-704FC6E0FE0DE190.mp4
13,13_消费者订单模块(下).mp4,http://p8t8qct2x.bkt.clouddn.com/624901110-0196BFDEA2733941.mp4
14,14_工程重构.mp4,http://p8t8qct2x.bkt.clouddn.com/624901110-CB6969B1704EE8BB.mp4
15,15_Eureka基础知识.mp4,http://p8t8qct2x.bkt.clouddn.com/624901110-19147B7109156C04.mp4
16,16_EurekaServer服务端安装.mp4,http://p8t8qct2x.bkt.clouddn.com/624901110-1D99379832E4E83A.mp4
17,17_支付微服务8001入驻进eurekaServer.mp4,http://p8t8qct2x.bkt.clouddn.com/624901110-3813E2AB6CF8B4C9.mp4
18,18_订单微服务80入驻进eurekaServer.mp4,http://p8t8qct2x.bkt.clouddn.com/624901110-5AFEBED4FAA8D2AA.mp4
19,19_Eureka集群原理说明.mp4,http://p8t8qct2x.bkt.clouddn.com/624901110-575EE1EECCCA31A7.mp4
20,20_Eureka集群环境构建.mp4,http://p8t8qct2x.bkt.clouddn.com/624901110-1A70E31EEEC1E38D.mp4
21,21_订单支付两微服务注册进Eureka集群.mp4,http://p8t8qct2x.bkt.clouddn.com/624901110-F10551083C7C04F2.mp4
22,22_支付微服务集群配置.mp4,http://p8t8qct2x.bkt.clouddn.com/624901110-3AC7DF25927B89D9.mp4
23,23_actuator微服务信息完善.mp4,http://p8t8qct2x.bkt.clouddn.com/624901110-4C3A809402B26662.mp4
24,24_服务发现Discovery.mp4,http://p8t8qct2x.bkt.clouddn.com/624901110-908BBD1EF2F669B3.mp4
25,25_Eureka自我保护理论知识.mp4,http://p8t8qct2x.bkt.clouddn.com/624901110-8A6D31413636FE88.mp4
26,26_怎么禁止自我保护.mp4,http://p8t8qct2x.bkt.clouddn.com/624901110-583161F066353656.mp4
27,27_Eureka停更说明.mp4,http://p8t8qct2x.bkt.clouddn.com/624901110-5DF884B5210CDCFF.mp4
28,28_支付服务注册进zookeeper.mp4,http://p8t8qct2x.bkt.clouddn.com/624901110-15826B3A31C1A8C9.mp4
29,29_临时还是持久节点.mp4,http://p8t8qct2x.bkt.clouddn.com/624901110-EB16DD4F12C88DE1.mp4
30,30_订单服务注册进zookeeper.mp4,http://p8t8qct2x.bkt.clouddn.com/624901110-7AF26F608806B968.mp4
