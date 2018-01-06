package com.airlenet.crawler.heritrix.modules;

import org.archive.modules.CrawlURI;
import org.archive.modules.writer.MirrorWriterProcessor;

/**
 * @author airlenet
 * @version 2018-01-04
 */
public class XPathWriterProcessor extends MirrorWriterProcessor {
    @Override
    protected boolean shouldProcess(CrawlURI curi) {
        return super.shouldProcess(curi);
    }

    @Override
    protected void innerProcess(CrawlURI curi) {
        super.innerProcess(curi);
    }
}
