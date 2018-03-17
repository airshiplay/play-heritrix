package com.airlenet.crawler.heritrix.modules;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.io.FileUtils;
import org.archive.modules.CrawlURI;
import org.archive.modules.extractor.ExtractorHTML;
import org.archive.spring.ConfigPath;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author airlenet
 * @version 2018-01-02
 */
public class PlayWebDriverExtractorHTML extends ExtractorHTML {
    private WebDriver webDriver;
    protected ConfigPath taskConfig = new ConfigPath("taskConfig", "task_config.xml");
    private static Task task;
    private XmlMapper xmlMapper;

    private static Logger logger =
            Logger.getLogger(PlayWebDriverExtractorHTML.class.getName());

    public ConfigPath getTaskConfig() {
        return taskConfig;
    }

    public void setTaskConfig(ConfigPath taskConfig) {
        this.taskConfig = taskConfig;
    }

    public PlayWebDriverExtractorHTML() throws IOException {

    }


    @Override
    public boolean innerExtract(CrawlURI curi) {
        try {
            if (task == null) {
                String readFileToString = FileUtils.readFileToString(taskConfig.getFile(), "UTF-8");
                task = xmlMapper.readValue(readFileToString, Task.class);
            }
            if (webDriver == null) {
                String webDriver = task.getWebDriver();
                if ("ChromeDriver".equals(webDriver)) {
                    this.webDriver = new ChromeDriver();
                } else if ("SafariDriver".equals(webDriver)) {
                    this.webDriver = new SafariDriver();
                } else {
                    this.webDriver = new HtmlUnitDriver(true);
                }
            }
            String mime = curi.getContentType().toLowerCase();
            if(curi.getURI().endsWith(".js") || curi.getURI().endsWith(".css")){
                return super.innerExtract(curi);
            }else{
                webDriver.get(curi.getURI());
                String pageSource = webDriver.getPageSource();
                extract(curi, pageSource);
            }
            // Set flag to indicate that link extraction is completed.
            return true;
        } catch (Exception e) {
            curi.getNonFatalFailures().add(e);
            logger.log(Level.WARNING, "Failed WebDriver get of replay char sequence in " +
                    Thread.currentThread().getName(), e);
            return super.innerExtract(curi);
        }


    }

    @Autowired
    public void setXmlMapper(XmlMapper xmlMapper) {
        this.xmlMapper = xmlMapper;
    }
}
