package com.web.mvc.bean;

import com.web.mvc.pojo.Car;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Created by xieyuhui on 2018/6/15.
 */
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("invoke MyBeanPostProcessor.postProcessBeforeInitialization()");
        if ("car".equalsIgnoreCase(beanName)) {
            Car car = (Car) bean;
            if (car.getColor() == null) {
                car.setColor("黑色");
            }

        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("invoke MyBeanPostProcessor.postProcessAfterInitialization()");
        if ("car".equalsIgnoreCase(beanName)) {
            Car car = (Car) bean;
            if (car.getMaxSpeed() >= 200) {
                car.setMaxSpeed(200);
            }

        }
        return bean;
    }
}
