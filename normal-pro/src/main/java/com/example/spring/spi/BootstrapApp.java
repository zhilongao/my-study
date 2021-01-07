package com.example.spring.spi;

import java.util.ServiceLoader;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/1/7 18:45
 * @since v1.0.0001
 */
public class BootstrapApp {
    public static void main(String[] args) {
        ServiceLoader<SpiDemoInterface> loaders = ServiceLoader.load(SpiDemoInterface.class);
        loaders.forEach(SpiDemoInterface::test);
    }
}
