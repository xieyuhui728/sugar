package com.web.mvc.aop.springAop;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * Created by xieyuhui on 2017/2/18.
 */
public class SpringBeforeAdvice implements MethodBeforeAdvice {
    private Object target;
    public SpringBeforeAdvice(Object target){
        this.target = target;
    }

    public SpringBeforeAdvice(){

    }
    @Override
    public void before(Method method, Object[] args, Object obj) throws Throwable {
        System.out.println("spring的前置增强");
    }
}
