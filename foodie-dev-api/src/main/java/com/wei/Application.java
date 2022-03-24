package com.wei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author www
 * @date 2022/3/21 17:13
 * @description: TODO
 */

@SpringBootApplication
@MapperScan(basePackages = "com.wei.mapper")
@ComponentScan(basePackages = {"com.wei", "org.n3r.idworker"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
