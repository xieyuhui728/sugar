package com.web.mvc.aop;

/**
 * Created by xieyuhui on 2017/2/16.
 */
public class ForumServiceImpl implements ForumService {
    @Override
    public void foo() {
        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("this is foo method");
//        throw  new RuntimeException("运行时异常");
    }

    @Override
    public void bar() {
        System.out.println("this is bar method");
        /*try {
            throw new SQLException("SQL异常");
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void arg(String name, int num) {
    }
}
