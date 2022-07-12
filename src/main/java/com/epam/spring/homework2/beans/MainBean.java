package com.epam.spring.homework2.beans;

public class MainBean {
    private String name;
    private int value;

    public void beanInitMethod(){
        System.out.println(this.getClass().getSimpleName() + " init");
    }

    public void beanDestroyMethod(){
        System.out.println(this.getClass().getSimpleName() + " destroy");
    }

    public MainBean(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "MainBean{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
