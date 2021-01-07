package com.example.spring.spi.impl;

import com.example.spring.spi.SpiDemoInterface;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/1/7 18:43
 * @since v1.0.0001
 */
public class SpiDemoInterfaceImplV1 implements SpiDemoInterface {
    @Override
    public void test() {
        System.err.println("SpiDemoInterfaceImplV1#test() run...");
    }
}
