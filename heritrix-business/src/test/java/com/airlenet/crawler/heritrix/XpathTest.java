package com.airlenet.crawler.heritrix;

import com.airlenet.crawler.heritrix.modules.Task;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author airlenet
 * @version 2018-01-06
 */
public class XpathTest {
  static  XmlMapper xmlMapper = new XmlMapper();
  static {
      xmlMapper.setDefaultUseWrapper(false);
      xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

    public static void main(String args[]){

        HtmlUnitDriver htmlUnitDriver = new HtmlUnitDriver(true);
        htmlUnitDriver.get("http://www.fuyouhuiyi.cn/shop/%E8%AE%A2%E5%8D%95.html");
        htmlUnitDriver.getTitle();
//        InputStream inputStream = XpathTest.class.getClassLoader().getResourceAsStream("xpath.xml");
//        try {
//            Job job2 = xmlMapper.readValue(inputStream, Job.class);
//            System.out.println(job2);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static Task getJob(){
        InputStream inputStream = XpathTest.class.getClassLoader().getResourceAsStream("xpath.xml");
        try {
            Task job2 = xmlMapper.readValue(inputStream, Task.class);
           return job2;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
