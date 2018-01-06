package com.airlenet.crawler.heritrix.modules;

import org.archive.modules.CrawlURI;
import org.archive.modules.extractor.ExtractorHTML;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author airlenet
 * @version 2018-01-02
 */
public class PlayWebDriverExtractorHTML extends ExtractorHTML {
    private   static  WebDriver webDriver = new ChromeDriver();
    private static Logger logger =
            Logger.getLogger(PlayWebDriverExtractorHTML.class.getName());
    public PlayWebDriverExtractorHTML() {
//        SpringContext.getBean()
    }


    @Override
    public boolean innerExtract(CrawlURI curi) {
        try {
            webDriver.switchTo().frame(Thread.currentThread().getId()+"");
            webDriver.get(curi.getURI());
            String pageSource = webDriver.getPageSource();
            extract(curi,pageSource);
            // Set flag to indicate that link extraction is completed.
            return true;
        } catch (Exception e) {
            curi.getNonFatalFailures().add(e);
            logger.log(Level.WARNING,"Failed WebDriver get of replay char sequence in " +
                    Thread.currentThread().getName(), e);
            return super.innerExtract(curi);
        }


    }
}
