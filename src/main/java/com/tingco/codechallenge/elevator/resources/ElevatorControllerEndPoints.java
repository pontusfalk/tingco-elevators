package com.tingco.codechallenge.elevator.resources;

import org.springframework.web.bind.annotation.*;

/**
 * Rest Resource.
 *
 * @author Sven Wesley
 */
@RestController
@RequestMapping("/rest/v1")
public final class ElevatorControllerEndPoints {

    /**
     * Ping service to test if we are alive.
     *
     * @return String pong
     */
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public String ping() {

        return "pong";
    }
}
