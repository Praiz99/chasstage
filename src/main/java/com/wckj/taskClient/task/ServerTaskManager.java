package com.wckj.taskClient.task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.wckj.framework.core.ServiceContext;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.json.jackson.JsonUtil;
import com.wckj.framework.spring.ContextPropertyConfigurer;
import com.wckj.taskClient.cons.ConfigurationCons;
import com.wckj.taskClient.local.entity.TaskJobObj;
import com.wckj.taskClient.server.web.TaskController;
import com.wckj.taskClient.server.web.TimeTaskJobListener;
import com.wckj.taskClient.service.TaskManageService;
import com.wckj.taskClient.service.imp.TaskManageServiceImp;
import com.wckj.taskClient.util.HttpClientUtil;

public class ServerTaskManager {

	private static ServerTaskManager manager = null;

	private List<TaskJobObj> taskJobObjList = new ArrayList<TaskJobObj>();

	@Resource(name = "propertyConfigurer")
	private ContextPropertyConfigurer contextPropertyConfigurer;

	private ServerTaskManager() {
		init();
	}

	public static ServerTaskManager getInstance() {
		if (manager == null) {
			manager = new ServerTaskManager();
		}
		return manager;
	}

	public List<TaskJobObj> getTaskJobs() {
		return taskJobObjList;
	}

	public TaskJobObj getTaskJob(String categoryMark, String jobMark) {
		if (StringUtils.isEmpty(categoryMark) || StringUtils.isEmpty(jobMark)) {
			return null;
		}
		for (TaskJobObj t : taskJobObjList) {
			if (categoryMark.equals(t.getCategoryMark()) && jobMark.equals(t.getMark())) {
				return t;
			}
		}
		return null;
	}

	@SuppressWarnings("static-access")
	private void init() {
		TaskManageService taskManageService = ServiceContext.getServiceByClass(TaskManageServiceImp.class);
		String consoleIpPort = contextPropertyConfigurer.getPropertyValue(ConfigurationCons.TASKCLIENT_SERVER);
		String deployMark = contextPropertyConfigurer.getPropertyValue(ConfigurationCons.TASKCLIENT_MARK);
		String url = consoleIpPort + "rest/jdone/task/http/getTaskJobByDeployMark?deployMark=" + deployMark;
		try {
			// 添加全局的任务监听器
			taskManageService.addJobListener(new TimeTaskJobListener());
			// 根据部署标识获取任务数据
			String jsonStr = HttpClientUtil.getMethod(url);
			if (!"".equals(jsonStr)) {
				taskJobObjList = JsonUtil.getListFromJsonString(jsonStr, TaskJobObj.class);
				String classPath = TaskController.class.getClassLoader().getResource("/").getPath();
				for (TaskJobObj job : taskJobObjList) {
					String clsPath = classPath + job.getJobClass().replace(".", "/") + ".class";
					File newFile = new File(clsPath);
					if (!newFile.exists()) {
						// 文件不存在则去后台服务器下载
						String fileDownloadUrl = consoleIpPort + "task/http/fileDownload?jobMark=" + job.getMark()
								+ "&taskType=" + job.getCategoryMark();
						HttpClientUtil.fileDownload(fileDownloadUrl, clsPath);
					}
					if ("1".equals(job.getStatus())) {
						// 如果任务状态为1，则启动任务
						taskManageService.startJob(job);
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
