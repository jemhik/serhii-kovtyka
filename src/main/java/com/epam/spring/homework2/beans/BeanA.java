package com.epam.spring.homework2.beans;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class BeanA extends MainBean implements InitializingBean, DisposableBean {

    public BeanA(String name, int value) {
        super(name, value);
    }

    @Override
    public void destroy() {
        System.out.println(this.getClass().getSimpleName() + " destroy");
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println(this.getClass().getSimpleName() + " After Properties Set");
    }
}
