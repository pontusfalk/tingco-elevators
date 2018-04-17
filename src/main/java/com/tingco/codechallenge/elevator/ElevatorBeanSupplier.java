package com.tingco.codechallenge.elevator;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.tingco.codechallenge.elevator.api.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Configuration
public class ElevatorBeanSupplier {
    @Bean(destroyMethod = "shutdown")
    public ScheduledExecutorService taskExecutor(ElevatorConfig config) {
        return Executors.newScheduledThreadPool(config.getNumberOfElevators());
    }

    @Bean
    public TingcoElevatorShaftEngine elevatorShaftEngine(ScheduledExecutorService service, ElevatorConfig config) {
        return new TingcoElevatorShaftEngine(service, config.getSpeed());
    }

    @Bean
    public ElevatorController elevatorController(TingcoElevatorShaftEngine engine, ElevatorConfig config) {
        List<TingcoElevator> elevators = new ArrayList<>(config.getNumberOfElevators());

        for (int i = 0; i < config.getNumberOfElevators(); i++) {
            elevators.add(new TingcoElevator(i, config.getNumberOfFloors()));
        }

        return new TingcoElevatorController(engine, elevators);
    }

    @Bean
    public EventBus eventBus() {
        return new AsyncEventBus(Executors.newCachedThreadPool());
    }
}
