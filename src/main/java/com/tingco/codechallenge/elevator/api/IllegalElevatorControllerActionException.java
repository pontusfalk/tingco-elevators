package com.tingco.codechallenge.elevator.api;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class IllegalElevatorControllerActionException extends RuntimeException {
    public IllegalElevatorControllerActionException(String message) {
        super(message);
    }
}
