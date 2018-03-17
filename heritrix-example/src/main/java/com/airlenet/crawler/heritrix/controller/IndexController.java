package com.airlenet.crawler.heritrix.controller;

import com.airlenet.core.SpringContext;
import com.airlenet.crawler.heritrix.HeritrixComponent;
import org.archive.crawler.framework.Engine;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author airlenet
 * @version 2018-01-01
 */
@Controller
@RequestMapping()
public class IndexController {


    @RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
    public String index(){
//        SpringContext.getBean(HeritrixComponent.class);
        return "redirect:heritrix3/engine";
    }
}
