package com.tingco.codechallenge.elevator.api;

class IllegalElevatorControllerActionException extends RuntimeException {
    IllegalElevatorControllerActionException(String message) {
        super(message);
    }
}
