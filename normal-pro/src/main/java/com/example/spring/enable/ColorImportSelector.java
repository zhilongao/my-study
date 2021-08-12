package com.example.spring.enable;

import com.example.spring.entity.Green;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/1/7 11:45
 * @since v1.0.0001
 */
public class ColorImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{Green.class.getName()};
    }
}
