package com.web.mvc.controller;

import com.sun.istack.internal.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Created by xieyuhui on 2018/7/5.
 */
@Controller
@RequestMapping("/root")
/**
 *@SessionAttributes： SpringMVC会自动将当前Controller类中任何方法属性名为"xxx"的模型存储到HttpSession中。
 * 此时"xxx"为会话属性。
 *
 * @SessionAttributes(value = {"aaa","bbb"}) 将名为aaa,bbb的模型属性添加到会话中
 * @SessionAttributes(types = String.class) 将模型数据中所有String类型到的模型属性添加到会话中
 * @SessionAttributes(value = {"aaa","bbb"},types = String.class)将名为aaa,bbb的模型属性和所有String类型到的模型属性添加到会话中
 */
@SessionAttributes("xxx")
public class RootController {

    /**
     * 请求handle1是，SpringMVC通过@ModelAttribute将请求入参添加到模型中。
     *
     * @param commonAttribute
     */
    @RequestMapping("/handle1")
    public void handle1(@ModelAttribute("xxx") String commonAttribute) {
        //TODO
    }

    /**
     * 通过ModelMap可以访问到模型中的所有数据，这里读取到模型中的"commonAttribute"属性值。
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/handle2")
    public String handle2(ModelMap modelMap) {
        String commonAttribute = (String) modelMap.get("xxx");
        return commonAttribute;
    }
}
