package com.airlenet.crawler.heritrix.controller;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.Logical;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.airlenet.play.main.api.LogService;
import com.airlenet.play.main.api.LogService.LogLevel;
import com.airlenet.play.main.api.LogService.OperateType;

import com.airlenet.crawler.heritrix.entity.CrawlerTaskEntity;
import com.airlenet.crawler.heritrix.service.CrawlerTaskEntityService;
import com.airlenet.repo.domain.Result;
import com.querydsl.core.types.Predicate;
/**
 * 爬取任务
 *
 * @author  airlenet
 * @version  2018-01-04
 */
@Controller
@RequestMapping("/crawlerHeritrix/crawlerTask")
public class CrawlerTaskController {

	@Autowired
	private LogService logService;
	
	@Autowired
	private CrawlerTaskEntityService crawlerTaskEntityService;

    @RequiresPermissions("page:crawlerHeritrix:CrawlerTask:view")
	@RequestMapping(value = "/crawlerTaskList.view", method = RequestMethod.GET)
	public String getList() {
		logService.addLog(OperateType.VIEW, LogLevel.INFO, "查询爬取任务列表");
		return "classpath:/crawlerHeritrix/crawlerTask/crawlerTaskList";
	}

    @RequiresPermissions("page:crawlerHeritrix:CrawlerTask:create")
	@RequestMapping(value = { "/create.view" }, method = RequestMethod.GET)
	public String create(Model model) {
		return "classpath:/crawlerHeritrix/crawlerTask/crawlerTaskForm";
	}

    @RequiresPermissions("page:crawlerHeritrix:CrawlerTask:edit")
	@RequestMapping(value = { "/edit/{id}.view" }, method = RequestMethod.GET)
	public String edit(Model model, @PathVariable Long id) {
		model.addAttribute("crawlerTask", crawlerTaskEntityService.findOne(id));
		return "classpath:/crawlerHeritrix/crawlerTask/crawlerTaskForm";
	}

    @RequiresPermissions("page:crawlerHeritrix:CrawlerTask:view")
	@RequestMapping(value = { "/view/{id}.view" }, method = RequestMethod.GET)
	public String view(Model model, @PathVariable Long id) {
		model.addAttribute("crawlerTask", crawlerTaskEntityService.findOne(id));
		logService.addLog(OperateType.VIEW, LogLevel.INFO, "查询爬取任务信息");
		return "classpath:/crawlerHeritrix/crawlerTask/crawlerTaskView";
	}

}
