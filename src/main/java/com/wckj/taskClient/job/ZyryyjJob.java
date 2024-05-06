package com.wckj.taskClient.job;

import com.wckj.chasstage.common.task.DrsxTask;
import com.wckj.chasstage.common.task.ZyryyjTask;
import com.wckj.framework.core.ServiceContext;
import com.wckj.taskClient.util.JobDesc;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: QYT
 * @Date: 2023/6/15 1:15 下午
 * @Description:在押人员预警JOB
 */
@JobDesc(description = "在押人员预警")
public class ZyryyjJob implements Job {

    private static final Logger log= LoggerFactory.getLogger(ZyryyjJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.debug("ZyryyjJob定时任务启动");
        try {
            ZyryyjTask task = ServiceContext.getServiceByClass(ZyryyjTask.class);
            JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
            String baqid = "";
            if (jobDataMap.get("baqid") == null) {
                throw new Exception("未配置baqid,无法执行定时任务");
            } else {
                baqid = String.valueOf(jobDataMap.get("baqid"));
            }
            task.zyryAlarm(baqid);
        } catch (Exception e) {
            log.error("在押人员预警出错", e);
        }
    }
}
