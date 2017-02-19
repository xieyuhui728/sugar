package com.web.mvc.aop.springAop.advisor;

import com.web.mvc.aop.ForumService;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;

import java.lang.reflect.Method;

/**
 * Created by xieyuhui on 2017/2/19.
 * spring的静态切面
 * 通过继承StaticMethodMatcherPointcutAdvisor(通过静态普通方法名匹配方法，不够灵活)
 */
public class SpringAfterAdvisor extends StaticMethodMatcherPointcutAdvisor {

    //方法名过滤
    @Override
    public boolean matches(Method method, Class<?> aClass) {
        return "foo".equals(method.getName());
    }

    //类过滤(如果目标类明确，getClassFilter方法可省略)
    public ClassFilter getClassFilter() {
        return new ClassFilter() {
            @Override
            public boolean matches(Class<?> clazz) {
                return ForumService.class.isAssignableFrom(clazz);
            }
        };
    }
}
