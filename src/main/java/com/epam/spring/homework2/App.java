package com.epam.spring.homework2;

import com.epam.spring.homework2.beans.MainBean;
import com.epam.spring.homework2.config.BeansConfig;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(BeansConfig.class);
        String[] beanNames = context.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            System.out.println(context.getBean(beanName));
        }
    }
}
