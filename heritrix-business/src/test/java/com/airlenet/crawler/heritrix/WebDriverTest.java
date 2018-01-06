package com.airlenet.crawler.heritrix;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author airlenet
 * @version 2018-01-05
 */
public class WebDriverTest {
    private   static WebDriver webDriver = new ChromeDriver();
    public static void main(String args[]){
        webDriver.get("http://www.baidu.com");
        webDriver.getPageSource();

        WebElement element = webDriver.findElement(By.xpath("//*[@id=\"kw\"]"));
        element.sendKeys("qq");

        element.sendKeys("text");

        element.submit();
        webDriver.get("http://www.163.com");
//        webDriver.switchTo().frame("dd");
        webDriver.close();
        webDriver.quit();
//        webDriver.getCurrentUrl();
//        webDriver.getWindowHandle();
//        webDriver.getWindowHandles();
    }


}
