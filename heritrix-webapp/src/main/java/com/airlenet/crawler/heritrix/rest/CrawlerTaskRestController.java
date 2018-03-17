package com.airlenet.crawler.heritrix.rest;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


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
@RestController
@RequestMapping("/crawlerHeritrix/crawlerTask")
public class CrawlerTaskRestController {

	@Autowired
	private LogService logService;
	
	@Autowired
	private CrawlerTaskEntityService crawlerTaskEntityService;

    @RequiresPermissions("data:crawlerHeritrix:CrawlerTask:view")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	public Page<CrawlerTaskEntity> doPage(Predicate predicate, Pageable pageable) {
		return crawlerTaskEntityService.findAll(predicate, pageable);
	}

    @RequiresPermissions(value = {"data:crawlerHeritrix:CrawlerTask:create","data:crawlerHeritrix:CrawlerTask:edit"} ,logical = Logical.OR)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Result doSave(@ModelAttribute @Valid CrawlerTaskEntity job, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Result.validateError();
		}

		crawlerTaskEntityService.save(job);

		if(job.isNew()){
			logService.addLog(OperateType.INSERT, LogLevel.INFO, "创建爬取任务成功");
		}else{
			logService.addLog(OperateType.UPDATE, LogLevel.INFO, "更新爬取任务成功");
		}
		
		return Result.success();
	}

    @RequiresPermissions("data:crawlerHeritrix:CrawlerTask:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "id")
	@ResponseBody
	public Result delete(@RequestParam(value = "id") CrawlerTaskEntity entity) {
		crawlerTaskEntityService.delete(entity);
		logService.addLog(OperateType.DEL, LogLevel.INFO, "删除爬取任务成功");
		return Result.success();
	}
    @RequiresPermissions("data:crawlerHeritrix:CrawlerTask:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "ids")
	@ResponseBody
	public Result batchDelete(@RequestParam(value = "ids") CrawlerTaskEntity[] entities) {
		for (CrawlerTaskEntity entity : entities) {
			crawlerTaskEntityService.delete(entity);
		}
		logService.addLog(OperateType.DEL, LogLevel.INFO, "删除爬取任务成功");
		return Result.success();
	}
}
