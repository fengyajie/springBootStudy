package com.example.job;

import com.example.util.JdSynJob;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author fyj
 */
@RestController
public class RefreshCronController {
    @Autowired
    private com.example.util.JdSynJob JdSynJob;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private JDDao jDDao;
    
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    
    @Autowired
    private JdSynJob jdSynJob;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        
        return new ThreadPoolTaskScheduler();
    }


    /**
     * 手动刷新 定时任务配置
     * @param cron
     */
    @GetMapping("/refreshCron")
    public void refreshCron(String cron){

        String redisCron = jDDao.selectJDTask(JDSynJobConstant.SYN_DETAIL).get(0).getCron();

        if(StringUtils.isNotBlank(redisCron)&&StringUtils.isNotBlank(cron)&&!redisCron.equals(cron)){
            EcmJdTask ecmJdTask = new EcmJdTask();
            ecmJdTask.setCron(cron);
            ecmJdTask.setTaskName(JDSynJobConstant.SYN_DETAIL);
            jDDao.updateTaskCron(ecmJdTask);

            try {
                threadPoolTaskScheduler.schedule(()->jdSynJob.execute(),new Date());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 关停/启用任务
     * @param status
     */
    @GetMapping("/updateTaskStatus")
    public void updateTaskStatus(Integer status){
        EcmJdTask ecmJdTask = new EcmJdTask();
        ecmJdTask.setTaskName(JDSynJobConstant.SYN_DETAIL);
        ecmJdTask.setStatus(status);
        jDDao.updateTaskStatus(ecmJdTask);
    }

    /**
     * 更改配置
     * @param skuCount
     * @param delJd
     */
    @GetMapping("/updateConfig")
    public void updateConfig(Integer skuCount,Integer delJd){
          EcmJdConfig ecmJdConfig = new EcmJdConfig();
          ecmJdConfig.setSkuCount(skuCount);
          ecmJdConfig.setDelJd(delJd);
          jDDao.updateConfig(ecmJdConfig);
    }

}
