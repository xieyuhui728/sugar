package com.web.mvc.aop.monitor;

import javax.sound.midi.Soundbank;

/**
 * Created by xieyuhui on 2017/2/18.
 * 记录目标方法的耗时
 */
public class MethodPerformance {
    private long begin;
    private long end;
    private String methodName;

    public MethodPerformance(String methodName){
        this.methodName = methodName;
        this.begin = System.currentTimeMillis();
    }

    public void printPerformance(){
        end = System.currentTimeMillis();
        long elapse = end - begin;
        System.out.println(methodName + "耗时：" + elapse + "毫秒");
    }
}
