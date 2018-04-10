package com.tingco.codechallenge.elevator;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Preconfigured Spring Application boot class.
 */
@SpringBootApplication
public class ElevatorApplication {
    @Value("${com.tingco.elevator.numberofelevators}")
    private int numberOfElevators;

    /**
     * Start method that will be invoked when starting the Spring context.
     *
     * @param args Not in use
     */
    public static void main(final String[] args) {
        SpringApplication.run(ElevatorApplication.class, args);
    }

    /**
     * Create a default thread pool for your convenience.
     *
     * @return ExecutorService thread pool
     */
    @Bean(destroyMethod = "shutdown")
    public ExecutorService taskExecutor() {
        return Executors.newScheduledThreadPool(numberOfElevators);
    }

    /**
     * Create an event bus for your convenience.
     *
     * @return EventBus for async task execution
     */
    @Bean
    public EventBus eventBus() {
        return new AsyncEventBus(Executors.newCachedThreadPool());
    }
}
