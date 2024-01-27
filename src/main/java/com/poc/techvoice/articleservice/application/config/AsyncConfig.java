package com.poc.techvoice.articleservice.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;


@EnableAsync
@Configuration
public class AsyncConfig {

    @Value("${task.executor.pool.core-size}")
    private int corePoolSize;

    @Value("${task.executor.pool.max-size}")
    private int maxPoolSize;

    @Value("${task.executor.queue-capacity}")
    private int queueCapacity;


    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("Async Thread - ");
        executor.initialize();
        return executor;
    }
}
