package com.web.mvc.aop.springAop.pointcut;

import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by xieyuhui on 2017/2/25.
 * @ Pointcut 注解对切点命名
 */
public class TestNamePointcut {
    @Pointcut("within(com.web.mvc.aop.springAop.*)")
    public void inPackage(){
    }
    @Pointcut("execution(* *(..))")
    public void inClass(){
    }
    @Pointcut("inPackage() and inClass()")
    public void inPkgClass(){

    }

}
