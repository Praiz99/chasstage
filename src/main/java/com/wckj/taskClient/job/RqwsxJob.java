package com.wckj.taskClient.job;


import com.wckj.chasstage.common.task.RqcsTask;
import com.wckj.chasstage.common.task.RqwsxTask;
import com.wckj.framework.core.ServiceContext;
import com.wckj.taskClient.util.JobDesc;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JobDesc(description = "入区未审讯")
public class RqwsxJob implements Job {
	private static final Logger log= LoggerFactory.getLogger(RqwsxJob.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.debug("RqwsxJob定时任务启动");
		try {
			RqwsxTask task = ServiceContext.getServiceByClass(RqwsxTask.class);
			task.sxAlarm();
		} catch (Exception e) {
			log.error("入区未审讯检查出错", e);
		}
	}

}
