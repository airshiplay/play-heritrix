package com.airlenet.crawler.heritrix;

import org.archive.crawler.framework.Engine;
import org.archive.crawler.restlet.EngineApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.io.File;

/**
 * @author airlenet
 * @version 2018-01-07
 */
@Configuration
@Order(-1)
public class HeritrixConfigBean {

    @Value("${heritrix.jobs-dir?:./jobs}")
    File jobsDir;

    @Bean
    public HeritrixComponent heritrixComponent(){
        HeritrixComponent heritrixComponent = new HeritrixComponent();
        heritrixComponent.setEngineApplication(engineApplication());
        return heritrixComponent;
    }
    @Bean
    public Engine engine(){
        return new Engine(jobsDir);
    }

    @Bean public EngineApplication engineApplication(){
      return   new EngineApplication(engine());
    }

}
