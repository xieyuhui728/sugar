package com.web.mvc.aop.springAop.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * Created by xieyuhui on 2017/2/21.
 * 基于AspectJ注解的方式配置切面
 * SpringAspectAnnotation 相当于spring的advice,pointcut,advisor三者联合表达的信息
 */

//利用注解@Aspect将class标识为一个切面
@Aspect
public class SpringAspectAnnotation {
    /**
     * 注解@Before @After定义增强，
     * 参数execution()定义切点(匿名切点) execution(<修饰符模式>? <返回类型模式> <方法名模式>(<参数模式>)<异常模式>?)
     * 除了返回类型模式、方法名模式和参数模式外，其它项都是可选的
     * execution表达式详解：http://blog.csdn.net/lk7688535/article/details/51989746#t0
     */
    @Before("execution(* *(..))&& args(name,num,..)")
    public void beforeAdvice(int num ,String name) {
        System.out.println("基于aspectJ注解得前置增强。");
        /**
         * 通过args绑定目标类方法的入参
         */
        System.out.println("----name:"+name);
        System.out.println("----num:"+num);
    }

    //这里的切点 使用切点命名方式
    @After("com.web.mvc.aop.springAop.pointcut.TestNamePointcut.inClass()")
    public void afterAdvice() {
        System.out.println("基于aspectJ注解得后置增强。");
    }

    /**
     *  任何增强方法都可以通过将第一个入参声明为JoinPoint访问到目标类连接点上下文的信息
     * @param joinPoint
     * @throws Throwable
     */
    @Around("com.web.mvc.aop.springAop.pointcut.TestNamePointcut.inClass()")
    public void aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("基于aspectJ注解得环绕增强--前置。");
        joinPoint.proceed();
        System.out.println("基于aspectJ注解得环绕增强--后置。");
    }
}
