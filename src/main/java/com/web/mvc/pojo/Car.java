package com.web.mvc.pojo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;

/**
 * Created by xieyuhui on 2018/6/15.
 * 编程方式管理Bean声明周期
 * BeanFactoryAware, BeanNameAware, InitializingBean, DisposableBean 都是Spring提供的Bean的生命周期控制接口。
 */
public class Car implements BeanFactoryAware, BeanNameAware, InitializingBean, DisposableBean {

    private String brand;
    private String color;
    private Integer maxSpeed;

    private BeanFactory beanFactory;
    private String beanName;

    public Car() {
    }

    /**
     * BeanFactoryAware接口方法
     *
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("invoke BeanFactoryAware.setBeanFactory() 。 ");
        this.beanFactory = beanFactory;
    }

    /**
     * BeanNameAware接口方法
     *
     * @param beanName
     */
    @Override
    public void setBeanName(String beanName) {
        System.out.println("invoke BeanNameAware.setBeanName() 。 ");
        this.beanName = beanName;
    }

    /**
     * DisposableBean接口方法
     *
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        System.out.println("invoke DisposableBean.destroy() 。 ");
    }

    /**
     * InitializingBean接口方法
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("invoke InitializingBean.afterPropertiesSet() 。 ");
    }

    public void myInit() {
        System.out.println("invoke init-method指定的myInit() 。 ");
        this.maxSpeed = 240;
    }

    public void myDestroy() {
        System.out.println("invoke destroy-method指定的myDestroy 。 ");
    }


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Integer maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public String getBeanName() {
        return beanName;
    }
}
