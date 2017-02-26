package com.web.mvc.aop.springAop.annotation;

import com.web.mvc.aop.ForumService;
import com.web.mvc.aop.ForumServiceImpl;
import com.web.mvc.aop.springAop.annotation.SpringAspectAnnotation;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Created by xieyuhui on 2017/2/25.
 */
public class Test {
    public static void main(String[] args) {
        ForumService target = new ForumServiceImpl();
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
        aspectJProxyForumService.arg("jay",10);
        System.out.println("------------------基于AspectJ注解的springAop测试-end--------------------");
    }
}
