package com.web.mvc;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Created by xieyuhui on 2018/12/13.
 */
public class Demo1 {

    public static void main(String[] args) {
        int[] arr = {10, 11};
        if (ArrayUtils.contains(arr, 10)) {
            System.out.println(1);
        } else {
            System.out.println(2);
        }
    }
}
