package com.tingco.codechallenge.elevator.api;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

class TingcoElevatorShaftEngine {
    private final ScheduledExecutorService service;
    private final int speed;
    private final Map<Integer, Future> runningElevators = new HashMap<>();

    TingcoElevatorShaftEngine(ScheduledExecutorService service, int speed) {
        this.speed = speed;
        this.service = service;
    }

    void startElevator(TingcoElevator elevator) {
        ScheduledFuture elevatorRunner = service.scheduleAtFixedRate(elevator::tick, 0, speed, MILLISECONDS);
        runningElevators.put(elevator.getId(), elevatorRunner);
    }

    void stopElevator(TingcoElevator elevator) {
        if (runningElevators.containsKey(elevator.getId())) {
            runningElevators.remove(elevator.getId()).cancel(false);
        }
        else {
            throw new IllegalElevatorActionException("that elevator is not running");
        }
    }
}
