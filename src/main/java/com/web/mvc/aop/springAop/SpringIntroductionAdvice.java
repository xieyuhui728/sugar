package com.web.mvc.aop.springAop;

import com.web.mvc.aop.monitor.Monitorable;
import com.web.mvc.aop.monitor.PerformanceMonitor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

/**
 * Created by xieyuhui on 2017/2/18.
 * 引介增强，类级别的增强，该增强由AOP联盟定义。
 * 通过引介增强。可以为目标类添加一个接口的实现，为目标类创建实现某接口的代理
 * 该demo为目标类添加Monitorable接口实现,通过一个boolean值，来控制是否对目标类方法进行性能监控
 */
public class SpringIntroductionAdvice extends DelegatingIntroductionInterceptor implements Monitorable {
    private ThreadLocal<Boolean> MonitorStatusMap = new ThreadLocal<Boolean>();
    @Override
    public void setMonitorActive(boolean active) {
        MonitorStatusMap.set(active);
    }

    //重写DelegatingIntroductionInterceptor的invoke方法
    public Object invoke(MethodInvocation mi) throws Throwable {
        Object obj = null;
        if(MonitorStatusMap.get()!=null&&MonitorStatusMap.get()){
            PerformanceMonitor.begin(mi.getMethod().getName());
            obj = super.invoke(mi);
            PerformanceMonitor.end();
        }else{
            obj = super.invoke(mi);
        }
        return obj;
    }
}
