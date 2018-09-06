package com.leoyang.mongol.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by yang.liu on 2018/9/5
 */
@SpringBootApplication
public class RestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class,args);
    }

}
/**
 * public class RestApplication extends SpringBootServletInitializer {
 *
 *     @Override
 *     protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
 *         return builder.sources(RestApplication.class);
 *     }
 *
 *     public static void main(String[] args) {
 *         SpringApplication.run(RestApplication.class,args);
 *     }
 *
 * }
 */
