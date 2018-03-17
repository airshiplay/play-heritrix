//package com.airlenet.crawler.heritrix;
//
//import com.airlenet.crawler.heritrix.modules.Data;
//import com.airlenet.crawler.heritrix.modules.Item;
//import com.airlenet.crawler.heritrix.modules.Job;
//import com.airlenet.crawler.heritrix.modules.Page;
//import groovy.lang.Binding;
//import groovy.lang.GroovyClassLoader;
//import groovy.lang.GroovyShell;
//import org.codehaus.groovy.ant.Groovy;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.safari.SafariDriver;
//import org.openqa.selenium.support.ui.Select;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.regex.Pattern;
//
///**
// * @author airlenet
// * @version 2018-01-05
// */
//public class WebDriverTest {
//    private   static WebDriver webDriver = new SafariDriver();
//    public static void main(String args[]){
//        Job job = XpathTest.getJob();
//
//        for(Page page:job.getPages()){
//
//            webDriver.get(page.getUrlRegex());
//            webDriver.getTitle();
//            List<Data> dataList = page.getDatas();
//            for(Data data:dataList){
//               if("list".equals(data.getType())) {
//                   data.getName();
//                   List<WebElement> list = webDriver.findElements(By.xpath(data.getXpath()));
//                  List result=  new ArrayList();
//                   for(WebElement webElement:list){
//                       HashMap<String, Object> objectHashMap = new HashMap<String, Object>();
//                       result.add(objectHashMap);
//                       List<Item> itemList = data.getItems();
//                       for(Item item:itemList){
//                           WebElement element = webElement.findElement(By.xpath(item.getXpath()));
//
//                           if("select".equals(item.getType())){
//                               Select select = new Select(element);
//                               List<WebElement> selectOptions = select.getOptions();
//                           }
//                            if(item.getGroovy()!=null){
//                                Binding binding = new Binding();
//                                GroovyShell groovyShell = new GroovyShell(binding);
//                                binding.setProperty("element",element);
//                                binding.setProperty("url",webDriver.getCurrentUrl());
//                                Object evaluate = groovyShell.evaluate(item.getGroovy());
//                                binding.getVariables().remove("element");
//                                String value= evaluate.toString();
//                                objectHashMap.put(item.getColName(),value);
//                            }else{
//                                objectHashMap.put(item.getColName(),element.getText());
//                            }
//                       }
//                   }
//                   System.out.println(result);
//               }else if("object_list".equals(data.getType())){
//                   data.getName();
//                   List<Item> itemList = data.getItems();
//                   for(Item item:itemList){
//                       WebElement element = webDriver.findElement(By.xpath(item.getXpath()));
//
//                       if("select".equals(item.getType())){
//                           Select select = new Select(element);
//                           List<WebElement> selectOptions = select.getOptions();
//                           for(WebElement selectElement:selectOptions){
//                               String text = selectElement.getText();
//                           }
//                       }else{
//
//                       }
//
//                   }
//               }else if("object".equals(data.getType())){
//
//               }
//            }
//
//        }
//    }
//
//
//}
