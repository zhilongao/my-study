package com.example.spring.import1.enable;

import com.example.spring.import1.enable.entity.Blue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/1/7 11:43
 * @since v1.0.0001
 */
@Configuration
public class ColorRegistrarConfiguration {

    @Bean
    public Blue blue() {
        return new Blue();
    }
}
