package com.epam.spring.homework2;

import com.epam.spring.homework2.beans.MainBean;
import com.epam.spring.homework2.config.BeansConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(BeansConfig.class);
        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
        ((ConfigurableApplicationContext) context).close();
    }
}
