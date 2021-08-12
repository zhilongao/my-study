package com.example.spring.resource.protocol;

import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * 自定义协议解析器
 *
 * @author gaozhilong
 * @date 2021/1/29 11:50
 * @since v1.0.0001
 */
public class DogProtocolResolver implements ProtocolResolver {

    public static final String DOG_PATH_PREFIX = "dog:";

    public static final String RESOURCE_LOCATION_PREFIX = "classpath:resource/";

    @Override
    public Resource resolve(String location, ResourceLoader resourceLoader) {
        if (!location.startsWith(DOG_PATH_PREFIX)) {
            return null;
        }
        String realPath = location.substring(DOG_PATH_PREFIX.length());
        String classpathLocation = RESOURCE_LOCATION_PREFIX + realPath;
        return resourceLoader.getResource(classpathLocation);
    }
}
