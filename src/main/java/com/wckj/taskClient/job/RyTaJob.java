package com.wckj.taskClient.job;

import com.wckj.chasstage.common.task.HgTask;
import com.wckj.chasstage.common.task.TaTask;
import com.wckj.framework.core.ServiceContext;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.wckj.taskClient.util.JobDesc;


@JobDesc(description = "同案预警")
public class RyTaJob implements Job{
private static final Logger log=LoggerFactory.getLogger(RyTaJob.class);

	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.debug("RyTaJob定时任务启动");
		try{
			TaTask task = ServiceContext.getServiceByClass(TaTask.class);
			task.taAlarm();
		}catch (Exception e){
			log.error("同案定时任务出错", e);
		}
		
	}
}
