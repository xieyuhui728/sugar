package com.web.mvc.service.impl;

import com.web.mvc.service.AopTest;

/**
 * Created by xieyuhui on 2017/2/16.
 */
public class AopTestImpl implements AopTest {
    @Override
    public String foo() {
        return "this is foo method";
    }
}
