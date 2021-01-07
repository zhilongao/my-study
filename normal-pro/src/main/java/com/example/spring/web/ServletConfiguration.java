package com.example.spring.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/1/7 19:47
 * @since v1.0.0001
 */
@Configuration
public class ServletConfiguration {

    @Bean
    public DemoServletRegistryBean demoServletRegistryBean() {
        return new DemoServletRegistryBean(new DemoServlet(), "/demo/servlet");
    }
}
