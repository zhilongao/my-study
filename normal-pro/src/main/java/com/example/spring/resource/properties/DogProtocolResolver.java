package com.example.spring.resource.properties;

import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/1/29 11:50
 * @since v1.0.0001
 */
public class DogProtocolResolver implements ProtocolResolver {

    public static final String DOG_PATH_PREFIX = "dog:";

    @Override
    public Resource resolve(String location, ResourceLoader resourceLoader) {
        if (!location.startsWith(DOG_PATH_PREFIX)) {
            return null;
        }
        String realpath = location.substring(DOG_PATH_PREFIX.length());
        String classpathLocation = "classpath:resource/" + realpath;
        return resourceLoader.getResource(classpathLocation);
    }
}
