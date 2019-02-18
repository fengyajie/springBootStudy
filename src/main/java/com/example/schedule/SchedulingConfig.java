package com.example.schedule;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulingConfig {

	@Scheduled(cron="0 0 1 * * ?")
	public void task() {
		System.out.println("任务执行>>>>>>>>>>");
	}
}
