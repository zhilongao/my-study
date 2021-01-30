package com.example.spring.resource.properties;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/1/30 11:46
 * @since v1.0.0001
 */
public class YmlPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(resource.getResource());
        Properties properties = yamlPropertiesFactoryBean.getObject();
        return new PropertiesPropertySource((name != null ? name : resource.getResource().getFilename()), properties);
    }
}
