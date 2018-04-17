package com.tingco.codechallenge.elevator;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.tingco.codechallenge.elevator.api.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ElevatorBeanSupplier {
    @Bean(destroyMethod = "shutdown")
    public ExecutorService taskExecutor(@Value("${com.tingco.elevator.numberofelevators:4}") int numberOfElevators) {
        return Executors.newScheduledThreadPool(numberOfElevators);
    }

    @Bean
    public ElevatorController elevatorController(@Value("${com.tingco.elevator.numberoffloors:5}") int numberOfFloors,
                                                 @Value("${com.tingco.elevator.numberofelevators:4}") int numberOfElevators) {
        List<TingcoElevator> elevators = new ArrayList<>(numberOfElevators);

        for (int i = 0; i < numberOfElevators; i++) {
            elevators.add(new TingcoElevator(i, numberOfFloors));
        }

        return new TingcoElevatorController(elevators);
    }

    @Bean
    public EventBus eventBus() {
        return new AsyncEventBus(Executors.newCachedThreadPool());
    }
}
