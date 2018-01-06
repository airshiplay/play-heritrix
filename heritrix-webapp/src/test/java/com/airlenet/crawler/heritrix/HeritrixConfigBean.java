//package com.airlenet.crawler.heritrix;
//
//import org.archive.crawler.restlet.EngineResource;
//import org.restlet.Context;
//import org.restlet.ext.spring.SpringComponent;
//import org.restlet.ext.spring.SpringFinder;
//import org.restlet.ext.spring.SpringRouter;
//import org.springframework.context.annotation.Bean;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author airlenet
// * @version 2018-01-01
// */
//public class HeritrixConfigBean {
//
//    @Bean
//    public SpringComponent getSpringComponent(){
//        SpringComponent springComponent = new SpringComponent();
//        springComponent.setDefaultTarget(getSpringRouter());
//        return springComponent;
//    }
//
//    @Bean
//    public SpringRouter getSpringRouter(){
//        SpringRouter springRouter = new SpringRouter();
//        Map attachments= new HashMap();
//        attachments.put("/engine", new SpringFinder(Context.getCurrent(), EngineResource.class));
//        springRouter.setAttachments(attachments);
//        return springRouter;
//    }
//}
