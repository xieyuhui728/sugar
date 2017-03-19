package com.web.mvc.splider;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ListIterator;

/**
 * Created by xieyuhui on 17-3-19.
 */
public class JsoupDemo {
    public static void main(String[] args) {
        try {
            Document doc =Jsoup.connect("https://movie.douban.com/top250?start=0&filter=").timeout(5000).get();
            Elements elements = doc.body().getElementsByAttributeValue("class","item");
            ListIterator<Element> listIteratorItem = elements.listIterator();
            while(listIteratorItem.hasNext()){
                Element elementItem = listIteratorItem.next();
//            System.out.println(element);
                Element  elementPic = elementItem.getElementsByClass("pic").listIterator().next();
                System.out.println(elementPic.getElementsByTag("em").html());//rank
                System.out.println(elementPic.getElementsByTag("img").attr("src"));//img

                Element  elementInfo = elementItem.getElementsByClass("info").listIterator().next();
                ListIterator<Element> listIteratorTitle = elementInfo.getElementsByClass("title").listIterator();
                while(listIteratorTitle.hasNext()){
                    System.out.println(listIteratorTitle.next().html());
                }
                System.out.println(elementInfo.getElementsByClass("other").html());
                System.out.println(elementInfo.getElementsByClass("inq").html());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
