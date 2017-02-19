package com.web.mvc.aop.springAop;

import com.web.mvc.aop.monitor.PerformanceMonitor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Created by xieyuhui on 2017/2/18.
 * 使用环绕增强监控目标方法的执行时长
 */
public class SpringAroundAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        PerformanceMonitor.begin(methodInvocation.getMethod().getName());//前增强
        Object obj  =  methodInvocation.proceed();//调用目标方法
        PerformanceMonitor.end();//后增强
        return obj;
    }
}
