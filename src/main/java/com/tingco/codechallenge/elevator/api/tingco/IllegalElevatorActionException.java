package com.tingco.codechallenge.elevator.api.tingco;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
class IllegalElevatorActionException extends RuntimeException {
    IllegalElevatorActionException(String message) {
        super(message);
    }
}
