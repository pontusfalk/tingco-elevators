package com.tingco.codechallenge.elevator.api;

/**
 * Interface for an elevator object.
 *
 * @author Sven Wesley
 */
public interface Elevator {

    /**
     * Enumeration for describing elevator's direction.
     */
    enum Direction {
        UP,
        DOWN,
        NONE
    }

    /**
     * Tells which direction is the elevator going in.
     *
     * @return Direction Enumeration value describing the direction.
     */
    Direction getDirection();

    /**
     * If the elevator is moving. This is the target floor.
     *
     * @return primitive integer number of floor
     *
     * todo: add sentinel value if elevator is not moving?
     */
    int getAddressedFloor();

    /**
     * Get the Id of this elevator.
     *
     * @return primitive integer representing the elevator.
     */
    int getId();

    /**
     * Command to move the elevator to the given floor.
     *
     * @param toFloor int where to go.
     *
     * todo: error contract?
     */
    void moveElevator(int toFloor);

    /**
     * Check if the elevator is occupied at the moment.
     *
     * @return true if busy.
     *
     * todo:
     * busy is vague in the elevator business context. Does it mean it's occupied like the javadoc says?
     * If so, is it not busy when it's begun moving to get the user but not yet picked her up?
     * How to keep track if it's occupied? What if the user hit a button and then left before the elevator doors shut?
     */
    boolean isBusy();

    /**
     * Reports which floor the elevator is at right now.
     *
     * @return int actual floor at the moment.
     */
    int currentFloor();
}
