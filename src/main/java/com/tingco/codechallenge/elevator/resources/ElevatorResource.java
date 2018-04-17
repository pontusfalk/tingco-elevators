package com.tingco.codechallenge.elevator.resources;

import com.tingco.codechallenge.elevator.ElevatorConfig;
import com.tingco.codechallenge.elevator.api.*;
import com.tingco.codechallenge.elevator.api.tingco.IllegalElevatorControllerActionException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/elevators")
public final class ElevatorResource {
    private final ElevatorConfig config;
    private final ElevatorController elevatorController;
    private final Map<Integer, Elevator> elevatorsInUse = new HashMap<>();

    public ElevatorResource(ElevatorController elevatorController, ElevatorConfig config) {
        this.elevatorController = elevatorController;
        this.config = config;
    }

    @GetMapping
    public Collection<Elevator> getElevators() {
        return elevatorController.getElevators();
    }

    @GetMapping("config")
    public ConfigDTO getConfig() {
        //elevatorConfig is a spring proxy object, won't serialize without a proper DTO class
        return new ConfigDTO(config);
    }

    @PostMapping("floors/{floor}")
    public Elevator requestElevator(@PathVariable int floor) {
        Elevator elevator = elevatorController.requestElevator(floor);
        if (elevator == null) {
            throw new IllegalElevatorControllerActionException("no elevators available");
        }

        elevatorsInUse.put(elevator.getId(), elevator);

        return elevator;
    }

    @PutMapping("{id}")
    public void moveToFloor(@PathVariable int id, @RequestBody Map<String, Integer> body) {
        Elevator elevator = elevatorsInUse.get(id);
        if (elevator == null) {
            throw new IllegalElevatorControllerActionException("that elevator is not in use");
        }

        Integer addressedFloor = body.get("addressedFloor");
        if (addressedFloor == null) {
            throw new IllegalElevatorControllerActionException("must pass in addressedFloor");
        }

        elevator.moveElevator(addressedFloor);
    }

    @DeleteMapping("{id}")
    public void releaseElevator(@PathVariable int id) {
        Elevator elevator = elevatorsInUse.get(id);
        if (elevator == null) {
            throw new IllegalElevatorControllerActionException("that elevator is not in use");
        }

        elevatorController.releaseElevator(elevator);
        elevatorsInUse.remove(id);
    }
}
