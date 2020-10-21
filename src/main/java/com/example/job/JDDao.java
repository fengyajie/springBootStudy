package com.example.job;

import com.mall.entity.jd.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* Describe:京东商品对接
* Author: yangbo
* Date: 2019年5月10日下午2:52:57
*/
public interface JDDao {
	
	/**
	 * 查询京东任务表达式
	 * @param
	 * @return
	 */
	List<EcmJdTask> selectJDTask(@Param("taskName") String taskName);

	/**
	 * 更新任务定时表达式
	 * @param ecmJdTask
	 */
	void updateTaskCron(EcmJdTask ecmJdTask);


	void updateConfig(EcmJdConfig ecmJdConfig);

	/**
	 * 启用/关停
	 * @param ecmJdTask
	 */
	void updateTaskStatus(EcmJdTask ecmJdTask);
}
