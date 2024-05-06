package com.wckj.taskClient.job;

import com.wckj.chasstage.common.task.SxsOutTimeTask;
import com.wckj.chasstage.common.task.TaTask;
import com.wckj.framework.core.ServiceContext;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.wckj.taskClient.util.JobDesc;
import org.springframework.beans.factory.annotation.Autowired;

@JobDesc(description = "审讯室延时断电")
public class SxsJob implements Job {

	private static final Logger log = LoggerFactory.getLogger(RyhgJob.class);


	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.debug("SxsJob定时任务启动");
		try {
			//办案区数量太多了，改成笔录结束后，调用延时断电
			//SxsOutTimeTask task = ServiceContext.getServiceByClass(SxsOutTimeTask.class);
			//task.outTime();
		} catch (Exception e) {
			log.error("审讯室延时断电定时任务出错", e);
		}

	}
}
