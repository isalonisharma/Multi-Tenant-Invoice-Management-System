package com.caseStudy.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * Class: Scheduler Configuration
 * 
 * @author saloni.sharma
 */
@Configuration
public class SchedulerConfiguration implements SchedulingConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(SchedulerConfiguration.class);

	private final int POOL_SIZE = 10;

	@Override
	public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
		logger.info("SchedulerConfiguration--->>configureTasks--->>Start");

		/**
		 * By default, all the @Scheduled tasks are executed in a default thread pool of
		 * size one created by Spring.
		 *
		 * creating own thread pool and configuring Spring to use that thread pool for
		 * executing all the scheduled tasks
		 */
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

		threadPoolTaskScheduler.setPoolSize(POOL_SIZE);
		threadPoolTaskScheduler.setThreadNamePrefix("my-scheduled-task-pool-");
		threadPoolTaskScheduler.initialize();

		scheduledTaskRegistrar.setTaskScheduler(threadPoolTaskScheduler);

		logger.info("SchedulerConfiguration--->>configureTasks--->>Ended");
	}
}
