package com.epam.spring.homework2.beans;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class BeanC extends MainBean{

    public BeanC(String name, int value) {
        super(name, value);
    }

    public void beanCInitMethod(){
        System.out.println(this.getClass().getSimpleName() + " init");
    }

    public void beanCDestroyMethod(){
        System.out.println(this.getClass().getSimpleName() + " destroy");
    }
}
