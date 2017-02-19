package com.web.mvc.aop.springAop;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;

import java.lang.reflect.Method;

/**
 * Created by xieyuhui on 2017/2/18.
 * spring的后置增强
 */
public class SpringAfterAdvice implements AfterReturningAdvice {

    @Override
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        System.out.println("spring的后置增强");
    }
}
