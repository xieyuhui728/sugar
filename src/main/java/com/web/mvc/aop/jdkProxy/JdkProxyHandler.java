package com.web.mvc.aop.jdkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by xieyuhui on 2017/2/16.
 */
public class JdkProxyHandler implements InvocationHandler {
    private Object target;
    public JdkProxyHandler(Object target){
        this.target = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("jdk动态代理");
        Object obj = method.invoke(target,args);
        return obj;
    }
}
