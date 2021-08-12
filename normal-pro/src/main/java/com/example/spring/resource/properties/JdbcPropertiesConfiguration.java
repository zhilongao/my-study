package com.example.spring.resource.properties;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * 引入配置文件,将其写入到Environment中
 *
 * @author gaozhilong
 * @date 2021/1/29 17:32
 * @since v1.0.0001
 */
@Configuration
@ComponentScan("com.example.spring.resource.properties")
//@PropertySource(value = {"classpath:jdbc.properties", "classpath:jdbc.xml", "classpath:jdbc.yml"})
@PropertySources(value = {
        // 加载.properties和.xml文件
        @PropertySource(value = {"classpath:jdbc.properties", "classpath:jdbc.xml"}),
        // 加载.yml文件, 需要配置factory属性
        @PropertySource(value = "classpath:jdbc.yml", factory = YmlPropertySourceFactory.class)
})
public class JdbcPropertiesConfiguration {

}
