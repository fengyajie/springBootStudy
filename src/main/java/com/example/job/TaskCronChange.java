package com.example.job;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author fyj
 */
@Component
@Configurable
public class TaskCronChange implements SchedulingConfigurer {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private JDDao jDDao;
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        List<EcmJdTask> list = jDDao.selectJDTask(null);
        for (EcmJdTask ecmJdTask : list) {
            Class<?> clazz;
            Object task;
            try {
                clazz = Class.forName(ecmJdTask.getTaskName());
                task = context.getBean(clazz);
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException("spring_scheduled_cron表数据" + ecmJdTask.getTaskName() + "有误", e);
            } catch (BeansException e) {
                throw new IllegalArgumentException(ecmJdTask.getTaskName() + "未纳入到spring管理", e);
            }

            Assert.isAssignable(ScheduledOfTask.class, task.getClass(), "定时任务类必须实现ScheduledOfTask接口");
            // 可以通过改变数据库数据进而实现动态改变执行周期
            Trigger trigger = new Trigger() {
                @Override
                public Date nextExecutionTime(TriggerContext triggerContext) {
                    //任务触发，可修改任务的执行周期.
                    //每一次任务触发，都会执行这里的方法一次，重新获取下一次的执行时间
                    String cron = jDDao.selectJDTask(JDSynJobConstant.SYN_DETAIL).get(0).getCron();
                    CronTrigger trigger = new CronTrigger(cron);
                    Date nextExec = trigger.nextExecutionTime(triggerContext);
                    return nextExec;
                }
            };
            taskRegistrar.addTriggerTask((Runnable) task, trigger);
        }
    }
    @Bean
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(10);
    }
}
