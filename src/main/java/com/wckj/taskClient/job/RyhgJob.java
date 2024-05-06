package com.wckj.taskClient.job;

import com.wckj.chasstage.common.task.HgTask;
import com.wckj.chasstage.common.task.RqwsxTask;
import com.wckj.framework.core.ServiceContext;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.wckj.taskClient.util.JobDesc;


@JobDesc(description = "混关预警")
public class RyhgJob implements Job{
	private static final Logger log=LoggerFactory.getLogger(RyhgJob.class);
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.debug("RyhgJob定时任务启动");
		try{
			HgTask task = ServiceContext.getServiceByClass(HgTask.class);
			task.hgAlarm();
		}catch (Exception e){
			log.error("混关定时任务出错", e);
		}
	}

}
