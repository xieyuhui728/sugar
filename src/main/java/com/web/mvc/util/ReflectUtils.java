package com.web.mvc.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by xieyuhui on 2018/11/9.
 */
public class ReflectUtils {

    public static void getReflectElement(Class<?> mLocalClass) {
        Class<?> c;
        c = mLocalClass;
        Method m[] = c.getDeclaredMethods(); // 取得全部的方法
        for (int i = 0; i < m.length; i++) {
            String mod = Modifier.toString(m[i].getModifiers()); // 取得访问权限
            String metName = m[i].getName(); // 取得方法名称
            Class<?> ret = m[i].getReturnType();// 取得返回值类型
            Class<?> param[] = m[i].getParameterTypes(); // 得到全部的参数类型
            Class<?> exc[] = m[i].getExceptionTypes(); // 得到全部的异常
            System.out.print(mod + " ");//输出每一方法的访问权限
            System.out.print(ret + " ");//输出每一方法的返回值类型
        }
    }
}
