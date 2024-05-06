package com.wckj.taskClient.job;

import com.wckj.chasstage.api.server.imp.device.internal.IWpgOper;
import com.wckj.chasstage.common.task.DutyTask;
import com.wckj.framework.core.ServiceContext;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wckj.taskClient.util.JobDesc;

@JobDesc(description = "巡更预警")
public class XgyjJob implements Job{
	private static final Logger log=LoggerFactory.getLogger(XgyjJob.class);
	

	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.debug("XgyjJob定时任务启动");
		try {
			DutyTask dutyTask = ServiceContext.getServiceByClass(DutyTask.class);
			dutyTask.dutyAlarm();
		} catch (Exception e) {
			log.error("巡更定时任务出错", e);
		}
	}

}
