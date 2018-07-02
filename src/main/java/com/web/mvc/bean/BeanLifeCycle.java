package com.web.mvc.bean;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Created by xieyuhui on 2018/6/15.
 */
public class BeanLifeCycle {

    private static void LifeCycleInBeanFactory() {
        Resource res = new ClassPathResource("beans.xml");
    }

    public static void main(String[] args) {
        LifeCycleInBeanFactory();
    }
}
