package com.wckj.chasstage.common.delaytask;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executors;

@Component
public class DelayQueueManager {
    private final Logger logger = Logger.getLogger(DelayQueueManager.class);
    private DelayQueue<TaskBase> delayQueue = new DelayQueue<>();
    private boolean isRun = true;
    /**
     * 加入到延时队列中
     * @param task
     */
    public void put(TaskBase task) {
        logger.info("加入延时任务：{}"+ task);
        delayQueue.put(task);
    }

    /**
     * 取消延时任务
     * @param task
     * @return
     */
    public boolean remove(TaskBase task) {
        logger.info("取消延时任务：{}"+ task);
        return delayQueue.remove(task);
    }
    /**
     * 取消延时任务
     * @param taskid
     * @return
     */
    public boolean remove(String taskid) {
        return remove(new DelayTask(taskid,null,0));
    }
    @PostConstruct
    public void init() throws Exception {
        logger.info("初始化延时队列");
        Executors.newSingleThreadExecutor().execute(new Thread(this::excuteThread));
    }
    private void excuteThread() {
        while (true) {
            try {
                TaskBase task = delayQueue.take();
                processTask(task);
            } catch (Exception e) {
                logger.error("分配延时任务出错", e);
            }
        }
    }
    /**
     * 内部执行延时任务
     * @param task
     */
    private void processTask(TaskBase task) {
        logger.info("执行延时任务："+task);
        Executors.newCachedThreadPool().execute(task);
    }

    public boolean isRun() {
        return isRun;
    }

    public void setRun(boolean run) {
        isRun = run;
    }
}
