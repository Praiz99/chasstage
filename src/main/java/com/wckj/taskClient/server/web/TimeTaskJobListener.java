package com.wckj.taskClient.server.web;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;

import javax.annotation.Resource;

import com.wckj.chasstage.common.util.DateTimeUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;


import com.wckj.framework.spring.ContextPropertyConfigurer;
import com.wckj.taskClient.cons.ConfigurationCons;
import com.wckj.taskClient.util.HttpClientUtil;

public class TimeTaskJobListener implements JobListener {

	@Resource(name = "propertyConfigurer")
	private ContextPropertyConfigurer contextPropertyConfigurer;
	private Date startTime;

	@Override
	public String getName() {
		return getClass().getSimpleName();
	}

	/**
	 * Scheduler 在 JobDetail 将要被执行时调用这个方法。
	 */
	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		startTime = new Date();
	}

	/**
	 * Scheduler 在 JobDetail 即将被执行，但又被 TriggerListener 否决了时调用这个方法。
	 */
	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {

	}

	/**
	 * Scheduler 在 JobDetail 被执行之后调用这个方法。
	 */
	@SuppressWarnings("static-access")
	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		// 执行成功
		int isExeOk = 1;
		if (jobException != null && !jobException.getMessage().equals("")) {
			// 执行失败
			isExeOk = 0;
		}
		try {
			String exeServerIp = InetAddress.getLocalHost().getHostAddress();
			String url = contextPropertyConfigurer.getPropertyValue(ConfigurationCons.TASKCLIENT_SERVER) + "rest/jdone/task/http/addTaskLogData?"
					+ "taskMark=" + context.getJobDetail().getKey().getName() + "&taskType="
					+ context.getJobDetail().getKey().getGroup() + "&exeCmpltTime=" + DateTimeUtils.getDateString(15)
					+ "&exeStartTime=" + DateTimeUtils.getDateStr(startTime, 15) + "&isExeOk=" + isExeOk + "&exeServerIp="
					+ exeServerIp;
			// 把空格替换掉
			url = url.replace(" ", "%20");
			// 发送添加日志请求
			HttpClientUtil.getMethod(url);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
