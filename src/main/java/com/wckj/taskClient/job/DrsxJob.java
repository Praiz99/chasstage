package com.wckj.taskClient.job;

import com.wckj.chasstage.common.task.DrsxTask;
import com.wckj.chasstage.common.task.DutyTask;
import com.wckj.framework.core.ServiceContext;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.wckj.taskClient.util.JobDesc;


@JobDesc(description = "单人审讯")
public class DrsxJob implements Job{
	private static final Logger log=LoggerFactory.getLogger(DrsxJob.class);

	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.debug("DrsxJob定时任务启动");

		try {
			DrsxTask task = ServiceContext.getServiceByClass(DrsxTask.class);
			task.sxAlarm();
		} catch (Exception e) {
			log.error("单人审讯检查出错", e);
		}

	}
		
}
