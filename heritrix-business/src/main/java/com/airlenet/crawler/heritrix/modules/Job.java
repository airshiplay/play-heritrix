package com.airlenet.crawler.heritrix.modules;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

/**
 * @author airlenet
 * @version 2018-01-06
 */
@lombok.Data
public class Job {
    private String name;
    private List<Page> pages;
}
