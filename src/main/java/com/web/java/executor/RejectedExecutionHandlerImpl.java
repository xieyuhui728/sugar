package com.web.java.executor;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by xieyuhui on 2018/12/19.
 * 自定义实现处理不适合工作队列的作业
 */
public class RejectedExecutionHandlerImpl implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println(r.toString() + " is rejected");
    }
}
