package com.airlenet.crawler.heritrix.modules;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

/**
 * @author airlenet
 * @version 2018-01-06
 */
@lombok.Data
public class Task {
    private String name;
    private String webDriver;
    private List<Page> pages;

    public Page matchPage(String url){
        if(pages ==null)
            return null;
        for(Page page:pages){
            if(page.matchUrl(url)){
                return page;
            }
        }
        return null;
    }
}
