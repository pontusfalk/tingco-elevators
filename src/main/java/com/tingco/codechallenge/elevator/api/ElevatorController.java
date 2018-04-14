package com.tingco.codechallenge.elevator.api;

import java.util.List;

/**
 * Interface for the Elevator Controller.
 *
 * @author Sven Wesley
 */
interface ElevatorController {

    /**
     * Request an elevator to the specified floor.
     *
     * @param toFloor addressed floor as integer.
     * @return The Elevator that is going to the floor, or null if there is none available.
     *
     * todo
     * requesting an elevator and receiving one is inherently an async operation,
     * return a Future or similar instead?
     * error contract?
     */
    Elevator requestElevator(int toFloor);

    /**
     * A snapshot list of all elevators in the system.
     *
     * @return An immutable List with all {@link Elevator} objects.
     */
    List<Elevator> getElevators();

    /**
     * Telling the controller that the given elevator is free for new
     * operations.
     *
     * @param elevator the elevator that shall be released.
     *
     * todo
     * Problem: a user holding on to an already released elevator, trying to use it.
     * Solution: add indirection using a handle to hold a elevator until it's been released.
     * Any future attempt to use it will then result in an error.
     */
    void releaseElevator(Elevator elevator);
}
