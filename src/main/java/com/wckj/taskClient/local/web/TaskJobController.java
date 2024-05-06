package com.wckj.taskClient.local.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wckj.framework.spring.ContextPropertyConfigurer;
import com.wckj.taskClient.cons.ConfigurationCons;
import com.wckj.taskClient.local.entity.TaskJobObj;
import com.wckj.taskClient.task.LocalTaskManager;
import com.wckj.taskClient.task.ServerTaskManager;

/**
 * 名称：TaskJobController(定时任务控制器)
 * 功能：进入定时任务列表页，和获取列表页数据
 * 作者：xtt
 * Copyright:杭州威灿科技有限公司(c) 2017
 * 版本 1.0
 */
@Controller
@RequestMapping("/taskClient")
public class TaskJobController {

	@Resource(name = "propertyConfigurer")
	private ContextPropertyConfigurer contextPropertyConfigurer;

	@SuppressWarnings("static-access")
	private String taskClientMode = contextPropertyConfigurer.getPropertyValue(ConfigurationCons.TASKCLIENT_MODE);

	/**
	 * 进入任务列表页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/taskJobObjList")
	public String taskJobObjList() {
		return "taskClient/taskJobObjList";
	}

	/**
	 * 进入任务详情页
	 * 
	 * @param categoryMark
	 *            分组标识
	 * @param jobMark
	 *            任务标识
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/taskJobDetalis")
	public String taskJobDetalis(String categoryMark, String jobMark, Model model) {
		// 判断应用模式
		if ("local".equals(taskClientMode)) {
			TaskJobObj taskJobObj = LocalTaskManager.getInstance().getTaskJob(categoryMark, jobMark);
			model.addAttribute("taskJobObj", taskJobObj);
		} else if ("server".equals(taskClientMode)) {
			TaskJobObj taskJobObj = ServerTaskManager.getInstance().getTaskJob(categoryMark, jobMark);
			model.addAttribute("taskJobObj", taskJobObj);
		}
		return "taskClient/taskJobDetalis";
	}

	/**
	 * 获取所有的任务数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getTaskJobObjData")
	@ResponseBody
	public Map<String, Object> getTaskJobObjData() {
		Map<String, Object> result = new HashMap<String, Object>(0);
		// 判断应用模式
		if ("local".equals(taskClientMode)) {
			List<TaskJobObj> taskJobList = LocalTaskManager.getInstance().getTaskJobs();
			result.put("total", taskJobList.size());
			result.put("rows", taskJobList);
		} else if ("server".equals(taskClientMode)) {
			List<TaskJobObj> taskJobList = ServerTaskManager.getInstance().getTaskJobs();
			result.put("total", taskJobList.size());
			result.put("rows", taskJobList);
		}
		return result;
	}
}
