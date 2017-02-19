package com.web.mvc.aop.springAop.pointcut;

import com.web.mvc.aop.ForumService;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.ControlFlowPointcut;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

/**
 * Created by xieyuhui on 2017/2/19.
 * spring的复合切点
 * 有时候一个切点难以描述目标连接点的信息
 * spring提供ComposablePointcut把两个切点组合起来，通过起点的复合运算表示。
 */
public class SpringComposablePointcut {

    public Pointcut getIntersectionPointcut() {
        ComposablePointcut composablePointcut = new ComposablePointcut();

        Pointcut pointcut1 = new ControlFlowPointcut(ForumService.class, "foo");//流程切面
        Pointcut pointcut2 = new ControlFlowPointcut(ForumService.class, "bar");//流程切面
        return composablePointcut.intersection(pointcut1).intersection(pointcut2);
    }

}
