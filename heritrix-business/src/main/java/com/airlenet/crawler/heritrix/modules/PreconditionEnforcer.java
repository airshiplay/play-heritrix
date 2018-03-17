package com.airlenet.crawler.heritrix.modules;

import org.archive.modules.CrawlURI;

/**
 * 取消robots下载，解析限制
 * @author airlenet
 * @version 2018-01-06
 */
public class PreconditionEnforcer extends org.archive.crawler.prefetch.PreconditionEnforcer {
    @Override
    protected boolean considerRobotsPreconditions(CrawlURI curi) {
        return false;
    }
}
