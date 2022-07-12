package com.epam.spring.homework2.config;


import com.epam.spring.homework2.beans.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("com.epam.spring.homework2.bpp")
public class OtherConfig {

    @DependsOn("beanC")
    @Bean
    public BeanA beanA() {
        return new BeanA("beanA", 4);
    }

    @DependsOn("beanD")
    @Bean(initMethod = "beanInitMethod", destroyMethod = "beanDestroyMethod")
    public BeanB beanB(@Value("${beanB.name}") final String name, @Value("${beanB.value}") final int value) {
        return new BeanB(name, value);
    }

    @DependsOn("beanB")
    @Bean(initMethod = "beanInitMethod", destroyMethod = "beanDestroyMethod")
    public BeanC beanC(@Value("${beanC.name}") final String name, @Value("${beanC.value}") final int value) {
        return new BeanC(name, value);
    }

    @Bean(initMethod = "beanInitMethod", destroyMethod = "beanDestroyMethod")
    public BeanD beanD(@Value("${beanD.name}") final String name, @Value("${beanD.value}") final int value) {
        return new BeanD(name, value);
    }

    @Bean
    public BeanE beanE() {
        return new BeanE("beanE", 5);
    }

    @Lazy
    @Bean
    public BeanF beanF() {
        return new BeanF("beanF", 6);
    }

}
