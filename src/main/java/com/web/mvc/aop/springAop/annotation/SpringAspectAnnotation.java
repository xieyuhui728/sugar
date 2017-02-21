package com.web.mvc.aop.springAop.annotation;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * Created by xieyuhui on 2017/2/21.
 * SpringAspectAnnotation 相当于spring的advice,pointcut,advisor三者联合表达的信息
 */

//利用注解@Aspect将class标识为一个切面
@Aspect
public class SpringAspectAnnotation {
    /**
     * 注解@Before @After定义增强，
     * 参数execution()定义切点 execution(<修饰符模式>? <返回类型模式> <方法名模式>(<参数模式>)<异常模式>?)
     * 除了返回类型模式、方法名模式和参数模式外，其它项都是可选的
     * execution表达式详解：http://blog.csdn.net/lk7688535/article/details/51989746#t0
     */
    @Before("execution(* *(..))")
    public void beforeAdvice() {
        System.out.println("基于aspectJ注解得前置增强。");
    }

    @After("execution(* *(..))")
    public void afterAdvice() {
        System.out.println("基于aspectJ注解得后置增强。");
    }

}
