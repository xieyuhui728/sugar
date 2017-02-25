package com.web.mvc.aop.springAop.annotation;

import com.web.mvc.aop.ForumServiceImpl;
import com.web.mvc.aop.monitor.Monitorable;
import com.web.mvc.aop.springAop.SpringIntroductionAdvice;
import org.aspectj.lang.annotation.*;

/**
 * Created by xieyuhui on 2017/2/21.
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
    @Before("execution(* *(..))")
    public void beforeAdvice() {
        System.out.println("基于aspectJ注解得前置增强。");
    }

    //这里的切点 使用切点命名方式
    @After("com.web.mvc.aop.springAop.pointcut.TestNamePointcut.inClass()")
    public void afterAdvice() {
        System.out.println("基于aspectJ注解得后置增强。");
    }

}
