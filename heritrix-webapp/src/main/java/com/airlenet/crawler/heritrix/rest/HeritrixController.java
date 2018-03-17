package com.airlenet.crawler.heritrix.rest;

import org.archive.crawler.framework.Engine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author airlenet
 * @version 2018-01-01
 */
@RestController
@RequestMapping("/heritrix")
public class HeritrixController {

    @Autowired
    Engine engine;

    @RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
    public String index(){
        engine.getJobConfigs();
        return "index";
    }
}
