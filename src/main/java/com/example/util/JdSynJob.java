package com.example.util;

import com.example.job.ScheduledOfTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fyj
 */
@Service
public class JdSynJob implements ScheduledOfTask {
    private Logger logger = LoggerFactory.getLogger(JdSynJob.class);

    //过期时间6个小时--秒
    //测试
    private final long timeOut = 3*60;
    //private final long timeOut = 6 * 60 *60;

    //获取锁尝试时间 ---秒
    private final long trySeconds = 3;

    private  String LOCK_KEY= "JdGoodsDetaiLock#";

    @Autowired
    private RedisLockUtil redisLockUtil;


    //定时同步京东上架状态订单详情
    //测试每分钟
    //@Scheduled(cron = "0 */1 * * * ?")
    //@Scheduled(cron = "0 0 23 * * ?")
    public void synchronousDetail() {
         logger.info("同步京东上架商品详情开始》》》》》》》》》》》start");
         String value = String.valueOf(System.currentTimeMillis() + timeOut*1000);
         try {
             if (redisLockUtil.tryLock(LOCK_KEY, value, timeOut, trySeconds)) {
                 //jDService.synchronousDetail();
             }
         }catch(Exception e){
             logger.error("synchronousDetail ERROR>>>>>>>>",e);
         }finally {
             redisLockUtil.unLock(LOCK_KEY);
         }
        logger.info("同步京东上架商品详情结束》》》》》》》》》》》end");
    }

    /**
     * 定时任务方法
     */
    @Override
    public void execute() {

        logger.info("同步京东上架商品详情开始》》》》》》》》》》》start");
        String value = String.valueOf(System.currentTimeMillis() + timeOut*1000);
        try {
            if (redisLockUtil.tryLock(LOCK_KEY, value, timeOut, trySeconds)) {
                //do something
            }
        }catch(Exception e){
            logger.error("synchronousDetail ERROR>>>>>>>>",e);
        }finally {
            redisLockUtil.unLock(LOCK_KEY);
        }
        logger.info("同步京东上架商品详情结束》》》》》》》》》》》end");
    }
}
