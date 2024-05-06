package com.wckj.taskClient.job;

import com.wckj.chasstage.common.task.DrsxTask;
import com.wckj.chasstage.common.task.MjtgTask;
import com.wckj.framework.core.ServiceContext;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.wckj.taskClient.util.JobDesc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@JobDesc(description = "民警脱岗")
public class MjtgJob implements Job {
	private static final Logger log= LoggerFactory.getLogger(MjtgJob.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.debug("MjtgJob定时任务启动");
		try {
			MjtgTask task = ServiceContext.getServiceByClass(MjtgTask.class);
			task.sxAlarm();
		} catch (Exception e) {
			log.error("民警脱岗检查出错", e);
		}
	}

}
