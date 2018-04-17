package com.tingco.codechallenge.elevator.resources;

import com.tingco.codechallenge.elevator.ElevatorConfig;

class ConfigDTO {
    public int numberOfFloors;
    public int numberOfElevators;
    public int speed;

    ConfigDTO(ElevatorConfig config) {
        numberOfFloors = config.getNumberOfFloors();
        numberOfElevators = config.getNumberOfElevators();
        speed = config.getSpeed();
    }
}
