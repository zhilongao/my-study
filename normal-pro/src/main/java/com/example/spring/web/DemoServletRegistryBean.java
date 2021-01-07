package com.example.spring.web;

import org.springframework.boot.web.servlet.ServletRegistrationBean;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/1/7 19:44
 * @since v1.0.0001
 */
public class DemoServletRegistryBean extends ServletRegistrationBean<DemoServlet> {

    public DemoServletRegistryBean(DemoServlet servlet, String... urlMappings) {
        super(servlet, urlMappings);
    }
}
