package com.epam.spring.homework2;

import com.epam.spring.homework2.config.BeansConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeansConfig.class);
        Arrays.stream(context.getBeanDefinitionNames()).forEach(s -> System.out.println(context.getBeanDefinition(s)));
        context.close();
    }
}
