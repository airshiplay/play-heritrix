package com.airlenet.crawler.heritrix.modules;

/**
 * @author airlenet
 * @version 2018-01-06
 */
@lombok.Data
public class Item {
    private String xpath;
    private String colName;
    private String key;
    private String type;
    private String groovy;
}
