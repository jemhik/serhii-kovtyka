package com.epam.spring.homework2.config;


import com.epam.spring.homework2.beans.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

import java.util.Objects;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("com.epam.spring.homework2.bpp")
public class OtherConfig {

    @Autowired
    private Environment env;

    @DependsOn("beanC")
    @Bean
    public BeanA beanA(){
        return new BeanA("beanA", 4);
    }

    @DependsOn("beanD")
    @Bean(initMethod = "beanInitMethod", destroyMethod = "beanDestroyMethod")
    public BeanB beanB(){
        return new BeanB(env.getProperty("beanB.name"), Integer.parseInt(Objects.requireNonNull(env.getProperty("beanB.value"))));
    }

    @DependsOn("beanB")
    @Bean(initMethod = "beanInitMethod", destroyMethod = "beanDestroyMethod")
    public BeanC beanC(){
        return new BeanC(env.getProperty("beanC.name"), Integer.parseInt(Objects.requireNonNull(env.getProperty("beanC.value"))));
    }

    @Bean(initMethod = "beanInitMethod", destroyMethod = "beanDestroyMethod")
    public BeanD beanD(){
        return new BeanD(env.getProperty("beanD.name"), Integer.parseInt(Objects.requireNonNull(env.getProperty("beanD.value"))));
    }

    @Bean
    public BeanE beanE(){
        return new BeanE("beanE", 5);
    }

    @Lazy
    @Bean
    public BeanF beanF(){
        return new BeanF("beanF", 6);
    }

}
