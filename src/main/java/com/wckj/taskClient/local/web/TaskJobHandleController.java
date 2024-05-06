package com.wckj.taskClient.local.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wckj.framework.spring.ContextPropertyConfigurer;
import com.wckj.taskClient.cons.ConfigurationCons;
import com.wckj.taskClient.local.entity.TaskJobObj;
import com.wckj.taskClient.service.TaskManageService;
import com.wckj.taskClient.task.LocalTaskManager;
import com.wckj.taskClient.task.ServerTaskManager;

/**
 * <pre>
 * 名称: TaskJobHandleController(定时任务处理控制器)
 * 功能: 负责该客户端运行的job的管理
 * 作者：dsx
 * Copyright: 杭州威灿科技有限公司 (c) 2017
 * 版本 1.0
 * </pre>
 */
@Controller
@RequestMapping("task/job/handle")
public class TaskJobHandleController {
	
	@Resource(name = "propertyConfigurer")
	private ContextPropertyConfigurer contextPropertyConfigurer;

	@SuppressWarnings("static-access")
	private String taskClientMode = contextPropertyConfigurer.getPropertyValue(ConfigurationCons.TASKCLIENT_MODE);
	
	@Autowired
	private TaskManageService taskManageService;

	/**
	 * 停止任务
	 * @throws Exception
	 */
	@RequestMapping(value = "/stop")
	@ResponseBody
	public Map<String, Object> stop(String categoryMark, String jobMark) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>(0);
		TaskJobObj taskJobObj = new TaskJobObj();
		// 判断应用模式
		if ("local".equals(taskClientMode)) {
			taskJobObj = LocalTaskManager.getInstance().getTaskJob(categoryMark, jobMark);
		}else if ("server".equals(taskClientMode)) {
			taskJobObj = ServerTaskManager.getInstance().getTaskJob(categoryMark, jobMark);
		}
		if(taskJobObj != null){
			//改变任务状态
			taskJobObj.setStatus("0");
			taskManageService.pauseJob(taskJobObj);
			result.put("msg", "任务停止成功！");
		}else{
			result.put("msg", "任务停止失败！");
		}
		return result;
	}

	/**
	 * 启动任务
	 * @throws Exception 
	 */
	@RequestMapping(value = "/start")
	@ResponseBody
	public Map<String, Object> start(String categoryMark, String jobMark) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>(0);
		TaskJobObj taskJobObj = new TaskJobObj();
		// 判断应用模式
		if ("local".equals(taskClientMode)) {
			taskJobObj = LocalTaskManager.getInstance().getTaskJob(categoryMark, jobMark);
		}else if ("server".equals(taskClientMode)) {
			taskJobObj = ServerTaskManager.getInstance().getTaskJob(categoryMark, jobMark);
		}
		if(taskJobObj != null){
			//改变任务状态
			taskJobObj.setStatus("1");
			taskManageService.startJob(taskJobObj);
			result.put("msg", "任务启动成功！");
		}else{
			result.put("msg", "任务启动失败！");
		}
		return result;

	}

}
