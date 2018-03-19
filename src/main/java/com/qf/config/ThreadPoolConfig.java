package com.qf.config;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Description:
 * User: JianHuangsh
 * Date: 2018-03-14
 * Time: 18:36
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {

  @Bean("executorService")
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    //如果池中的实际线程数小于corePoolSize,无论是否其中有空闲的线程，都会给新的任务产生新的线程
    taskExecutor.setCorePoolSize(5);
    //连接池中保留的最大连接数。Default: 15 maxPoolSize
    taskExecutor.setMaxPoolSize(10);
    //queueCapacity 线程池所使用的缓冲队列
    taskExecutor.setQueueCapacity(25);
    taskExecutor.initialize();
    return taskExecutor;
  }

}
