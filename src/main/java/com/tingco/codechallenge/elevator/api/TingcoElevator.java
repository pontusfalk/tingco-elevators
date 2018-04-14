package com.tingco.codechallenge.elevator.api;

import static com.tingco.codechallenge.elevator.api.Elevator.Direction.*;

public class TingcoElevator implements Elevator {
    private final int id;
    private int currentFloor = 1;
    private int toFloor;
    private Direction direction = NONE;
    private boolean busy;

    TingcoElevator(int id) {
        this.id = id;
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
        if (toFloor < 1) {
            throw new IllegalElevatorActionException("to floor must be at least 1, is: " + toFloor);
        }

        this.toFloor = toFloor;
        updateDirection();
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

    /**
     * A tick is where the elevator is doing the actual moving
     * as well as keeping direction and the current floor in sync
     */
    void tick() {
        if (!busy || direction == NONE) {
            return;
        }
        currentFloor += direction == UP ? 1 : -1;
        direction = currentFloor == toFloor ? NONE : direction;
    }
}
