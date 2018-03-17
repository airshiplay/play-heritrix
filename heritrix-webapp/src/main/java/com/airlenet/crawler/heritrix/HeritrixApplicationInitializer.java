package com.airlenet.crawler.heritrix;

import com.airlenet.integration.core.ApplicationInitializer;
import com.airlenet.play.main.InitDataTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author airlenet
 * @version 2018-01-02
 */
@Component
public class HeritrixApplicationInitializer extends ApplicationInitializer {
    @Autowired
    InitDataTools initDataTools;
    @Override
    public void onRootContextRefreshed() {

    }

    @Override
    public void onServletContextRefreshed() {


    }
}
