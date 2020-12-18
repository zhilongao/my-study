package com.study.code;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/18 19:58
 * @since v1.0.0001
 */
public class App {
    public static void main(String[] args) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(SimpleConfiguration.class);
        SimpleCls bean = context.getBean(SimpleCls.class);
        System.err.println(bean);
    }
    //	Return an instance, which may be shared or independent, of the specified bean.
    //	 * @param name the name of the bean to retrieve
    //	 * @param requiredType the required type of the bean to retrieve
    //	 * @param args arguments to use when creating a bean instance using explicit arguments
    //	 * (only applied when creating a new instance as opposed to retrieving an existing one)
    //	 * @param typeCheckOnly whether the instance is obtained for a type check,
    //	 * not for actual use
    //	 * @return an instance of the bean
    //	 * @throws BeansException if the bean could not be created
    //	 */

    @Configuration
    public static class SimpleConfiguration {

        @Bean
        public SimpleCls simpleCls() {
            return new SimpleCls();
        }
    }


    public static class SimpleCls{
        private String name;

        private int age;
    }
}
