package com.wckj.taskClient.job;

import com.wckj.chasstage.common.task.MjtgTask;
import com.wckj.chasstage.common.task.RqcsTask;
import com.wckj.framework.core.ServiceContext;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.wckj.taskClient.util.JobDesc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JobDesc(description = "入区超时")
public class RqcsJob implements Job {
	private static final Logger log= LoggerFactory.getLogger(RqcsJob.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.debug("RqcsJob定时任务启动");
		try {
			RqcsTask task = ServiceContext.getServiceByClass(RqcsTask.class);
			task.sxAlarm();
		} catch (Exception e) {
			log.error("入区超时检查出错", e);
		}
	}

}
