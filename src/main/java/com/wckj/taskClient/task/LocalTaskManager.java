package com.wckj.taskClient.task;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.wckj.framework.core.ServiceContext;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.spring.utils.ClassUtil;
import com.wckj.taskClient.local.entity.TaskCategoryObj;
import com.wckj.taskClient.local.entity.TaskJobObj;
import com.wckj.taskClient.service.TaskManageService;
import com.wckj.taskClient.service.imp.TaskManageServiceImp;

public final class LocalTaskManager implements TaskManager {

	private static LocalTaskManager manager = null;

	/** 任务分组【 key:分组标识】 */
	private Map<String, TaskCategoryObj> categoryMap = new HashMap<String, TaskCategoryObj>(0);

	/** 任务map【key:分组标识-job标识】 */
	private Map<String, TaskJobObj> taskJobMap = new HashMap<String, TaskJobObj>(0);

	private LocalTaskManager() {
		init();
	}

	public static LocalTaskManager getInstance() {
		if (manager == null) {
			manager = new LocalTaskManager();
		}
		return manager;
	}

	@Override
	public List<TaskCategoryObj> getTaskCategorys() {
		List<TaskCategoryObj> result = new ArrayList<TaskCategoryObj>(0);
		TaskCategoryObj cat = null;
		for (Map.Entry<String, TaskCategoryObj> entry : categoryMap.entrySet()) {
			cat = entry.getValue();
			if (cat != null) {
				result.add(cat);
			}
		}

		return result;
	}

	@Override
	public List<TaskJobObj> getTaskJobs() {
		List<TaskJobObj> result = new ArrayList<TaskJobObj>(0);
		TaskJobObj taskJob = null;
		for (Map.Entry<String, TaskJobObj> entry : taskJobMap.entrySet()) {
			taskJob = entry.getValue();
			if (taskJob != null) {
				result.add(taskJob);
			}
		}

		return result;
	}

	@Override
	public TaskJobObj getTaskJob(String categoryMark, String jobMark) {
		if (StringUtils.isEmpty(categoryMark) || StringUtils.isEmpty(jobMark)) {
			return null;
		}
		String mark = categoryMark + "-" + jobMark;
		return taskJobMap.get(mark);
	}

	@Override
	public TaskCategoryObj getTaskCategoryObj(String categoryMark) {
		if (StringUtils.isEmpty(categoryMark))
			return null;
		return categoryMap.get(categoryMark);
	}

	private void init() {
		// 读取xml
		Resource[] resourceArr = ClassUtil.scanResources(new String[] { "/**/jobs/**.xml" });
		if (resourceArr == null)
			return;

		try {
			// 得到DOM解析器的工厂实例
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			// 从DOM工厂中获得DOM解析器
			DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
			for (Resource resource : resourceArr) {
				try {
					analyXml(docBuilder, resource.getInputStream());
				} catch (Exception e) {

				} finally {
					resource.getInputStream().close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void analyXml(DocumentBuilder docBuilder, InputStream in) throws Exception {
		// 把要解析的xml文档读入DOM解析器
		Document doc = docBuilder.parse(in);
		// 获取分组信息集合
		NodeList categoryNodeList = doc.getElementsByTagName("task-category");
		for (int i = 0; i < categoryNodeList.getLength(); i++) {
			Element category = (Element) categoryNodeList.item(i);
			String categoryName = category.getElementsByTagName("name").item(0).getTextContent();
			String categoryMark = category.getElementsByTagName("mark").item(0).getTextContent();
			String categoryDesc = category.getElementsByTagName("desc").item(0).getTextContent();
			TaskCategoryObj taskCategoryObj = new TaskCategoryObj();
			taskCategoryObj.setName(categoryName);
			taskCategoryObj.setMark(categoryMark);
			taskCategoryObj.setDesc(categoryDesc);
			// 把分组信息放入Map中
			categoryMap.put(categoryMark, taskCategoryObj);
			// 获取该分组下的任务信息集合
			NodeList jobList = category.getElementsByTagName("job");
			for (int j = 0; j < jobList.getLength(); j++) {
				Element job = (Element) jobList.item(j);
				String jobName = job.getElementsByTagName("name").item(0).getTextContent();
				String jobMark = job.getElementsByTagName("mark").item(0).getTextContent();
				String jobDesc = job.getElementsByTagName("desc").item(0).getTextContent();
				String jobStatus = job.getElementsByTagName("status").item(0).getTextContent();
				String jobClass = job.getElementsByTagName("job-class").item(0).getTextContent();
				String jobCron = job.getElementsByTagName("cron-expression").item(0).getTextContent();
				TaskJobObj taskJobObj = new TaskJobObj();
				taskJobObj.setName(jobName);
				taskJobObj.setMark(jobMark);
				taskJobObj.setDesc(jobDesc);
				taskJobObj.setStatus(jobStatus);
				taskJobObj.setJobClass(jobClass);
				taskJobObj.setCronExp(jobCron);
				taskJobObj.setCategoryMark(categoryMark);
				// 获取任务参数信息集合
				NodeList entryList = job.getElementsByTagName("entry");
				Map<String, String> jobParams = new HashMap<String, String>(0);
				for (int t = 0; t < entryList.getLength(); t++) {
					Element entry = (Element) entryList.item(t);
					String key = entry.getElementsByTagName("key").item(0).getTextContent();
					String value = entry.getElementsByTagName("value").item(0).getTextContent();
					jobParams.put(key, value);
				}
				taskJobObj.setJobParams(jobParams);
				//判断任务状态
				if("1".equals(jobStatus)){
					TaskManageService taskManageService = ServiceContext.getServiceByClass(TaskManageServiceImp.class);
					taskManageService.startJob(taskJobObj);
				}
				// 把任务信息放入Map中
				taskJobMap.put(categoryMark + "-" + jobMark, taskJobObj);
			}
		}
	}

	/**
	 * 添加任务分类
	 * 
	 * @param obj
	 *            TaskCategoryObj
	 */
	private void addTaskCategoryObj(TaskCategoryObj obj) {
		if (obj == null || StringUtils.isEmpty(obj.getMark())) {
			return;
		}
		categoryMap.put(obj.getMark(), obj);
	}

	/**
	 * 添加任务
	 * 
	 * @param obj
	 *            TaskJobObj
	 */
	private void addTaskJobObj(TaskJobObj obj) {
		if (obj == null || StringUtils.isEmpty(obj.getMark())) {
			return;
		}
		String mark = obj.getCategoryMark() + "-" + obj.getMark();
		taskJobMap.put(mark, obj);
	}
}
