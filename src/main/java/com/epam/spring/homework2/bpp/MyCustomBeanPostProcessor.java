package com.epam.spring.homework2.bpp;

import com.epam.spring.homework2.beans.MainBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyCustomBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof MainBean) {
            if (((MainBean) bean).getName() != null && ((MainBean) bean).getValue() > 0) {
                System.out.println("Valid values " + ((MainBean) bean).getName() + " " + ((MainBean) bean).getValue());
            } else {
                System.out.println("Invalid values");
            }
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
