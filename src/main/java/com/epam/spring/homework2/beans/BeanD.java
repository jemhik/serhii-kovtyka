package com.epam.spring.homework2.beans;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class BeanD extends MainBean{

    public BeanD(String name, int value) {
        super(name, value);
    }

    public void beanDInitMethod(){
        System.out.println(this.getClass().getSimpleName() + " init");
    }

    public void beanDDestroyMethod(){
        System.out.println(this.getClass().getSimpleName() + " destroy");
    }
}
