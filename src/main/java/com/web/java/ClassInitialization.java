package com.web.java;

import java.util.Random;

/**
 * Created by xieyuhui on 2018/11/15.
 */

class Initable {
    //编译期静态常量
    static final int staticFinal = 47;
    //非编译期静态常量
    static final int staticFinal2 =
            ClassInitialization.rand.nextInt(1000);

    static {
        System.out.println("Initializing Initable");
    }
}

class Initable2 {
    //静态成员变量
    static int staticNonFinal = 147;

    static {
        System.out.println("Initializing Initable2");
    }
}

class Initable3 {
    //静态成员变量
    static int staticNonFinal = 74;

    static {
        System.out.println("Initializing Initable3");
    }
}

public class ClassInitialization {
    public static Random rand = new Random(47);

    public static void main(String[] args) throws Exception {
        Class initable = Initable.class;
        Class initable2 = Initable2.class;
        Class initable3 = Initable3.class;

        Class initableF = Class.forName("com.web.java.Initable");
        Class initableF2 = Class.forName("com.web.java.Initable2");
        Class initableF3 = Class.forName("com.web.java.Initable3");

//        //字面常量获取方式获取Class对象
//        Class initable = Initable.class;
//        System.out.println("After creating Initable ref");
//        //不触发类初始化
        System.out.println(Initable.staticFinal);
//        //会触发类初始化
//        System.out.println(Initable.staticFinal2);
//        //会触发类初始化
        System.out.println(Initable2.staticNonFinal);
//        //forName方法获取Class对象
//        Class initable3 = Class.forName("com.web.java.Initable3");
//        System.out.println("After creating Initable3 ref");
//        System.out.println(Initable3.staticNonFinal);
    }
}
