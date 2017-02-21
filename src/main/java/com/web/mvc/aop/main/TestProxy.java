package com.web.mvc.aop.main;

import com.web.mvc.aop.ForumService;
import com.web.mvc.aop.ForumServiceImpl;
import com.web.mvc.aop.cglibProxy.CglibProxyHandler;
import com.web.mvc.aop.jdkProxy.JdkProxyHandler;
import com.web.mvc.aop.monitor.Monitorable;
import com.web.mvc.aop.springAop.SpringBeforeAdvice;
import com.web.mvc.aop.springAop.annotation.SpringAspectAnnotation;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.lang.reflect.Proxy;

/**
 * Created by xieyuhui on 2017/2/16.
 */
public class TestProxy {
    public static void main(String[] args) {
        //------------------------------------------------------
        ForumService target = new ForumServiceImpl();

        System.out.println("-----------------基于jdk动态代理测试-begin--------------------");
        JdkProxyHandler jdkProxyHandler = new JdkProxyHandler(target);
        ForumService jdkProxy = (ForumService) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), jdkProxyHandler);
        jdkProxy.foo();
        jdkProxy.bar();
        System.out.println("-----------------基于jdk动态代理测试-end----------------------");

        System.out.println("-----------------基于cglib动态代理测试-begin--------------------");
        CglibProxyHandler cglibProxyHandler = new CglibProxyHandler();
        ForumServiceImpl cglibProxy = (ForumServiceImpl) cglibProxyHandler.getProxy(ForumServiceImpl.class);
        cglibProxy.foo();
        cglibProxy.bar();
        System.out.println("-----------------基于cglib动态代理测试-end--------------------");

        System.out.println("-----------------基于springAop测试-begin--------------------");
        SpringBeforeAdvice springBeforeAdvice = new SpringBeforeAdvice();
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addAdvice(springBeforeAdvice);
        proxyFactory.setTarget(target);
        ForumService springBeforeProxy = (ForumService) proxyFactory.getProxy();
        springBeforeProxy.foo();
        springBeforeProxy.bar();
        System.out.println("-----------------基于springAop测试-end--------------------");

        //---------基于spring配置文件的测试-------------------
        String filePath = "E:\\dev\\workspace\\sugar\\src\\main\\webapp\\WEB-INF\\springAop.xml";

        System.out.println("-----------------基于IOC的springAop测试-begin--------------------");
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext(filePath);
        ForumServiceImpl springProxy = (ForumServiceImpl) applicationContext.getBean("springProxy");
        springProxy.foo();
        springProxy.bar();
        System.out.println("-----------------基于IOC的springAop测试-end--------------------");

        System.out.println("-----------------基于IOC的springAop（引介增强）测试-begin--------------------");
        ForumServiceImpl springIntroductionProxy = (ForumServiceImpl) applicationContext.getBean("springIntroductionProxy");
        springIntroductionProxy.foo();
        springIntroductionProxy.bar();

        //开启性能监控开关
        Monitorable monitorable = (Monitorable) springIntroductionProxy;
        monitorable.setMonitorActive(true);
        springIntroductionProxy.foo();
        springIntroductionProxy.bar();
        System.out.println("------------------基于IOC的springAop（引介增强）测试-end--------------------");

        System.out.println("------------------基于IOC的springAop切面测试-begin--------------------");
        ForumServiceImpl springAdvisorProxy = (ForumServiceImpl) applicationContext.getBean("springAdvisorProxy");
        springAdvisorProxy.foo();
        springAdvisorProxy.bar();
        Monitorable monitorableAdvisor = (Monitorable) springAdvisorProxy;
        monitorableAdvisor.setMonitorActive(true);
        springAdvisorProxy.foo();
        springAdvisorProxy.bar();
        System.out.println("------------------基于IOC的springAop切面测试-end--------------------");

        System.out.println("------------------基于IOC的springAop生成代理测试-begin--------------------");
        //使用时直接获取目标bean，调用目标类方法时会被spring自动生成的代理类拦截
        ForumServiceImpl forumServiceImpl = (ForumServiceImpl) applicationContext.getBean("forumServiceImpl");
        forumServiceImpl.foo();
        forumServiceImpl.bar();
        System.out.println("------------------基于IOC的springAop生成代理测试-begin--------------------");

        System.out.println("------------------基于AspectJ注解的springAop测试-begin--------------------");
        AspectJProxyFactory aspectJProxyFactory = new AspectJProxyFactory();
        aspectJProxyFactory.setTarget(target);
        aspectJProxyFactory.addAspect(SpringAspectAnnotation.class);
        ForumService aspectJProxy = aspectJProxyFactory.getProxy();
        aspectJProxy.foo();
        aspectJProxy.bar();

        //自动创建代理测试
        String filePath2="E:\\dev\\workspace\\sugar\\src\\main\\webapp\\WEB-INF\\springAspectJ.xml";
        ApplicationContext applicationContext2 = new FileSystemXmlApplicationContext(filePath2);
        ForumService aspectJProxyForumService = (ForumService) applicationContext2.getBean("forumServiceImpl");
        aspectJProxyForumService.foo();
        aspectJProxyForumService.bar();
        System.out.println("------------------基于AspectJ注解的springAop测试-end--------------------");


    }
}
