package com.web.mvc.aop.springAop;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.aop.framework.adapter.ThrowsAdviceInterceptor;

import java.lang.reflect.Method;

/**
 * Created by xieyuhui on 2017/2/18.
 * ThrowsAdvice没有定义任何方法，是一个标识接口
 * ThrowsAdvice 继承 afterAdvice
 * 可以在同一个异常增强定义多个afterThrowing()/多态/，目标类方法抛出异常时，会匹配Exception最近的那个方法
 */
public class SpringThrowsAdvice implements ThrowsAdvice {

    /**
     * 方法名必须定义为afterThrowing(不知道为什么)
     * 前三个方法参数要么都提供，要么都不提供
     * ex参数是Throwable或子类
     * @param method
     * @param args
     * @param target
     * @param ex
     */
    public void afterThrowing(Method method,Object[] args,Object target,Exception ex){
        System.out.println(method.getName()+"抛出异常,异常信息:"+ex.getMessage());
    }
}
