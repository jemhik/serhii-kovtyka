package com.epam.spring.homework2.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class BeanB extends MainBean{

    public BeanB(String name, int value) {
        super(name, value);
    }

    public void otherBeanBInitMethod(){
        System.out.println(this.getClass().getSimpleName() + " other init");
    }
}
