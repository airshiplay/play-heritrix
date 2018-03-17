package com.airlenet.crawler.heritrix.modules;

import java.util.List;

/**
 * @author airlenet
 * @version 2018-01-06
 */
@lombok.Data
public class Data {
    private String type;
    private String name;
    private String xpath;
    private List<Item> items;
}
