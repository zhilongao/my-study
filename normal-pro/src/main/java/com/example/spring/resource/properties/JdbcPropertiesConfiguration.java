package com.example.spring.resource.properties;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/1/29 17:32
 * @since v1.0.0001
 */
@Configuration
@ComponentScan("com.example.spring.resource")
// @PropertySource(value = {"classpath:jdbc.properties", "classpath:jdbc.xml", "classpath:jdbc.yml"})
@PropertySources(value = {
        @PropertySource(value = {"classpath:jdbc.properties", "classpath:jdbc.xml"}),
        @PropertySource(value = "classpath:jdbc.yml", factory = YmlPropertySourceFactory.class)
})
public class JdbcPropertiesConfiguration {

}
