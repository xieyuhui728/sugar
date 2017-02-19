package com.web.mvc.controller;

import com.web.mvc.service.AopTest;
import com.web.mvc.service.impl.AopTestImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by xieyuhui on 2017/2/16.
 */
@Controller
@RequestMapping("/aop")
public class AopTestController {

    @RequestMapping("/beforeAdvice.html")
    public String beforeAdvice(ModelMap model){
        String filePath = "E:\\dev\\workspace\\sugar\\src\\main\\webapp\\WEB-INF\\applicaitonContext-servlet.xml";
        ApplicationContext cxt = new FileSystemXmlApplicationContext(filePath);
        AopTestImpl aopTest = (AopTestImpl)cxt.getBean("proxyAopTest");
        model.put("message",aopTest.foo());
        return "hello";
    }
}
