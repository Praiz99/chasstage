package com.wckj.taskClient.server.web;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wckj.framework.spring.ContextPropertyConfigurer;
import com.wckj.framework.spring.utils.ClassUtil;
import com.wckj.taskClient.cons.ConfigurationCons;
import com.wckj.taskClient.local.entity.TaskJobObj;
import com.wckj.taskClient.service.TaskManageService;
import com.wckj.taskClient.util.JobDesc;

@Controller
@RequestMapping("/task/http")
public class TaskController {

	@Autowired
	private TaskManageService taskManageService;
	@Resource(name = "propertyConfigurer")
	private ContextPropertyConfigurer contextPropertyConfigurer;

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/getTimeTaskImpClassData")
	@ResponseBody
	public List<Map<String, Object>> getTimeTaskImpClassData() throws ClassNotFoundException {
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String packagePaths = contextPropertyConfigurer.getPropertyValue(ConfigurationCons.TASKCLIENT_ANOTATION_PACKAGES);
		String[] str = new String[] { packagePaths };
		List<String> packageList = ClassUtil.scanPackage(str);
		for (String st : packageList) {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("name", st);
			Class<?> cl = Class.forName(st);
			JobDesc ma=cl.getAnnotation(JobDesc.class);
			if(ma!=null){
				result.put("text", ma.description());
				list.add(result);
			}
			/*Annotation[] classAnnotation = cl.getAnnotations();
			for (Annotation a : classAnnotation) {
				JobDesc ma = (JobDesc) a;
				result.put("text", ma.description());
			}
			list.add(result);
			*/


		}

		return list;
	}

	/**
	 * 根据ID启动定时任务
	 * 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/startTimeTask")
	@ResponseBody
	public Map<String, Object> startTimeTask(TaskJobObj taskJobObj) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>(0);
		taskManageService.startJob(taskJobObj);
		result.put("msg", "任务启动成功！");
		return result;
	}

	/**
	 * 停止定时任务
	 * 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/stopTimeTask")
	@ResponseBody
	public Map<String, Object> stopTimeTask(TaskJobObj taskJobObj) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>(0);
		taskManageService.pauseJob(taskJobObj);
		result.put("msg", "任务停止成功！");
		return result;
	}

	/**
	 * 删除定时任务
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delectTimeTaskData")
	@ResponseBody
	public Map<String, Object> delectTimeTaskData(TaskJobObj taskJobObj) {
		Map<String, Object> result = new HashMap<String, Object>(0);
		taskManageService.removeJob(taskJobObj);
		result.put("msg", "任务删除成功！");
		return result;
	}

}
