package com.tingco.codechallenge.elevator.api.tingco;

import com.tingco.codechallenge.elevator.api.Elevator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.tingco.codechallenge.elevator.api.Elevator.Direction.*;

public class TingcoElevator implements Elevator {
    private static final Logger logger = LoggerFactory.getLogger(TingcoElevator.class);

    private final int id;
    private final int floors;
    private int currentFloor = 1;
    private int toFloor;
    private Direction direction = NONE;
    private boolean busy;

    public TingcoElevator(int id, int floors) {
        this.id = id;
        this.floors = floors;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public int getAddressedFloor() {
        return toFloor;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void moveElevator(int toFloor) {
        if (!busy) {
            throw new IllegalElevatorActionException("elevator must be busy before it is allowed to move");
        }
        if (toFloor < 1 || toFloor > floors) {
            throw new IllegalElevatorActionException("floor must be between 1 and " + floors + ", is: " + toFloor);
        }

        this.toFloor = toFloor;
        updateDirection();

        if (direction != NONE) {
            logger.info("elevator {} on floor {} moving {} to floor {}", id, currentFloor, direction, toFloor);
        }
    }

    private void updateDirection() {
        if (toFloor == currentFloor) {
            direction = NONE;
        }
        else {
            direction = toFloor > currentFloor ? UP : DOWN;
        }
    }

    void setBusy(boolean busy) {
        this.busy = busy;
    }

    @Override
    public boolean isBusy() {
        return busy;
    }

    @Override
    public int currentFloor() {
        return currentFloor;
    }

    int getFloors() {
        return floors;
    }

    /**
     * A tick is where the elevator is doing the actual moving
     * as well as keeping direction and the current floor in sync
     */
    void tick() {
        if (!busy) {
            throw new IllegalElevatorActionException("elevator must be busy before it is allowed to move");
        }
        if (direction == NONE) {
            return;
        }

        currentFloor += direction == UP ? 1 : -1;
        direction = currentFloor == toFloor ? NONE : direction;

        if (direction != NONE) {
            logger.debug("elevator {} on floor {} moving {} to floor {}", id, currentFloor, direction, toFloor);
        }
        else {
            logger.info("elevator {} has arrived to floor {}. DING!", id, currentFloor);
        }
    }
}
