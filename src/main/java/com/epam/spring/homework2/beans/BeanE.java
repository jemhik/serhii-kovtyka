package com.epam.spring.homework2.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class BeanE extends MainBean{

    public BeanE(String name, int value) {
        super(name, value);
    }

    @PostConstruct
    public void beanEPostConstruct() {
        System.out.println(this.getClass().getSimpleName() + " Post Construct");
    }

    @PreDestroy
    public void beanEPreDestroy(){
        System.out.println(this.getClass().getSimpleName() + " Pre Destroy");
    }
}
