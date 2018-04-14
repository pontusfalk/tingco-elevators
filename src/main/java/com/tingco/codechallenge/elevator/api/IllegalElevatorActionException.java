package com.tingco.codechallenge.elevator.api;

class IllegalElevatorActionException extends RuntimeException {
    IllegalElevatorActionException(String message) {
        super(message);
    }
}
