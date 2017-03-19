package com.web.mvc.splider;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by xieyuhui on 17-3-19.
 */
public class JsoupDemo {
    public static void main(String[] args) {
        try {
            Document doc =Jsoup.connect("https://movie.douban.com/top250?start=0&filter=").timeout(5000).get();
            System.out.println(doc.title());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
