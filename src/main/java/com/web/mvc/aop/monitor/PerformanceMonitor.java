package com.web.mvc.aop.monitor;

/**
 * Created by xieyuhui on 2017/2/18.
 * 监控方法耗时的demo
 */
public class PerformanceMonitor {
    private static ThreadLocal<MethodPerformance> methodPerformanceThreadLocalMap = new ThreadLocal<MethodPerformance>();

    public static void  begin(String methodName){
        System.out.println("begin monitor...");
        MethodPerformance methodPerformance = new MethodPerformance(methodName);
        methodPerformanceThreadLocalMap.set(methodPerformance);
    }

    public static void end(){
        System.out.println("end monitor...");
        MethodPerformance methodPerformance = methodPerformanceThreadLocalMap.get();
        methodPerformance.printPerformance();
    }
}
