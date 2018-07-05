package com.web.mvc.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by xieyuhui on 2018/7/4.
 */
@Controller
@RequestMapping("/base")
public class BaseController {

    /**
     * url : 请求路径 select
     * method : 请求方法 post
     * params :  请求参数 userId
     * headers : 请求头 content-type=text/*
     *
     * @param userId：
     * @return
     */
    @RequestMapping(path = "/select", method = RequestMethod.POST, params = "userId", headers = "content-type=text/*")
    public String test(@RequestParam("userId") String userId) {
        //TODO
        return "";
    }

    /**
     * @param ownerId 绑定 {ownerId}的值
     * @param petId   绑定 {petId}的值
     */
    @RequestMapping("/select/{petId}")
    public void select(@PathVariable String ownerId, @PathVariable String petId) {
        //TODO
    }

    /**
     * userName和age绑定到handle()方法的 aaa和bbb中，并自动完成类型转换。
     * 如果不存在age请求参数，则抛出400错误。
     *
     * @param aaa
     * @param bbb
     * @return
     */
    @RequestMapping("/handle")
    public String handle(@RequestParam(value = "aaa", required = false) String aaa, @RequestParam("bbb") int bbb) {
        //TODO
        return "";
    }

    /**
     * HTTP请求中如果有name为id的 cookie，则绑定到handleCookie()方法id参数中。
     * required = false 即使没有 也不会抛出异常。
     *
     * @param id
     * @return
     */
    @RequestMapping("/handleCookie")
    public String handleCookie(@CookieValue(value = "id", required = false) int id) {
        //TODO
        return "";
    }


    /**
     * HTTP请求头中的Accept-Encoding和Keep-Alive属性将被绑定到handleHeader()方法encoding和keepAlive参数
     *
     * @param encoding
     * @param keepAlive
     * @return
     */
    @RequestMapping("/handleHeader")
    public String handleHeader(@RequestHeader(value = "Accept-Encoding") String encoding,
                               @RequestHeader(value = "Keep-Alive", required = false) long keepAlive) {
        //TODO
        return "";
    }

    /**
     * @param body
     * @return
     * @RequestBody: 根据方法中body的类型匹配HttpMessageConverter。
     * 由于这里的body是String类型，所以匹配到了StringHttpMessageConverter。
     * 然后用StringHttpMessageConverter把HTTP请求体信息进行转换并将结果绑定到body参数中。
     * @ResponseBody: 由于方法返回类型是byte[]，所以SpringMVC匹配到了ByteArrayHttpMessageConverter。
     * ByteArrayHttpMessageConverter对返回值做处理 并返回给客户端。
     */
    @ResponseBody
    @RequestMapping("/handle2")
    public byte[] handle2(@RequestBody String body) {
        return body.getBytes();
    }

    /**
     * HttpEntity<T>：范型指定入参的类型，这里范型是String类型，则匹配到了StringHttpMessageConverter。
     * <p>
     * ResponseEntity<T>：范型指定返回的类型，这里是byte[]，所以SpringMVC匹配到了ByteArrayHttpMessageConverter。
     *
     * @param httpEntity
     * @return
     */
    @RequestMapping("/handle3")
    public ResponseEntity<byte[]> handle3(HttpEntity<String> httpEntity) {
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(httpEntity.getBody().getBytes(), HttpStatus.OK);
        return responseEntity;
    }

    /**
     * 在访问BaseController中任何一个请求处理方法前，
     * SpringMVC先执行该方法，把"requiredString"这个字符串添加到模型中。
     *
     * @return
     */
    @ModelAttribute
    public String requiredAttribute() {
        return "requiredString";
    }

    /**
     * 先把"requiredString" 赋值给str，再根据HTTP请求参数进一步覆盖str的值。
     *
     * @param str
     * @return
     */
    @RequestMapping("/handle4")
    public String handle4(@ModelAttribute("requiredString") String str) {
        return str;
    }
}
