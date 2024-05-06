package com.wckj.taskClient.web;

import com.wckj.framework.core.ServiceContext;
import com.wckj.framework.core.ServiceFactory;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;
import com.wckj.framework.spring.ContextPropertyConfigurer;
import com.wckj.framework.web.servlet.SystemStartServlet;
import com.wckj.jdone.modules.task.util.JdoneTaskManager;
import com.wckj.taskClient.cons.ConfigurationCons;
import com.wckj.taskClient.task.LocalTaskManager;
import com.wckj.taskClient.task.ServerTaskManager;

public class SysStart extends SystemStartServlet {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(SysStart.class);

	/**
	 * 初始化
	 * 
	 * @param factory
	 *            ServiceFactory
	 */
	public void extraInit(ServiceFactory factory) {

		logger.info("开始初始化扩展功能------------");

		logger.info("--------------------------------------------");

//		logger.info("----------------初始化任务器管理器--------------");

		ContextPropertyConfigurer contextPropertyConfigurer = ServiceContext
				.getServiceByClass(ContextPropertyConfigurer.class);
		String taskClientMode = contextPropertyConfigurer
				.getPropertyValue(ConfigurationCons.TASKCLIENT_MODE);
//		// 判断应用模式
		if ("local".equals(taskClientMode)) {
			LocalTaskManager.getInstance();
		} else if ("server".equals(taskClientMode)) {
			ServerTaskManager.getInstance();
		}

//		logger.info("----------------初始化任务管理器完毕--------------");
		logger.info("----------------初始化启动部署任务--------------");
		JdoneTaskManager.getInstance();
		logger.info("----------------初始化启动任务完毕--------------");

		logger.info("--------------------------------------------");

		logger.info("扩展功能初始化完毕------------");
	}
}
