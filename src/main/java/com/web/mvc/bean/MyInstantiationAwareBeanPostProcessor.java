package com.web.mvc.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

import java.beans.PropertyDescriptor;

/**
 * Created by xieyuhui on 2018/6/15.
 * 扩展InstantiationAwareBeanPostProcessorAdapter
 */
public class MyInstantiationAwareBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {

    /**
     * 在实例化Bean前调用
     *
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if("car".equalsIgnoreCase(beanName)){
            System.out.println("invoke MyInstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation()");
        }
        return null;
    }

    /**
     * 在实例化Bean后调用
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if("car".equalsIgnoreCase(beanName)){
            System.out.println("invoke MyInstantiationAwareBeanPostProcessor.postProcessAfterInstantiation()");
        }
        return true;
    }

    /**
     * 在Bean设置某个属性时调用
     *
     * @param pvs
     * @param pds
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public PropertyValues postProcessPropertyValues(
            PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
        if("car".equalsIgnoreCase(beanName)){
            System.out.println("invoke MyInstantiationAwareBeanPostProcessor.postProcessPropertyValues()");
        }
        return pvs;
    }
}


