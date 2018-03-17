package com.airlenet.crawler.heritrix.modules;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author airlenet
 * @version 2018-01-06
 */
@lombok.Data
@JacksonXmlRootElement(localName = "page")
public class Page {
    private String urlRegex;
    private List<Data> datas;

    public boolean matchUrl(String url) {
      return   Pattern.matches(urlRegex, url);
    }
}
