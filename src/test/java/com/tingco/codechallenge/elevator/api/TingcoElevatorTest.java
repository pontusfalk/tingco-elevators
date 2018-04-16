package com.tingco.codechallenge.elevator.api;

import org.junit.Test;

import static com.tingco.codechallenge.elevator.api.Elevator.Direction.*;
import static com.tingco.codechallenge.elevator.api.TestFixtureHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TingcoElevatorTest {
    @Test
    public void newElevatorShouldStartOutAsFreeOnTheFirstFloorNotMoving() {
        TingcoElevator elevator = freeElevator();

        assertThat(elevator.currentFloor()).isEqualTo(1);
        assertThat(elevator.isBusy()).isFalse();
        assertThat(elevator.getDirection()).isEqualTo(NONE);
    }

    @Test
    public void freeElevatorShouldNotBeAbleToMove() {
        TingcoElevator elevator = freeElevator();

        assertThatThrownBy(() -> elevator.moveElevator(2))
          .isInstanceOf(IllegalElevatorActionException.class)
          .hasMessageContaining("must be busy before");
    }

    @Test
    public void elevatorMovingUpShouldHaveDirectionUp() {
        TingcoElevator elevator = elevatorMovingToFloor(2);

        assertThat(elevator.getDirection()).isEqualTo(UP);
    }

    @Test
    public void elevatorMovingDownShouldHaveDirectionDown() {
        TingcoElevator elevator = elevatorOnFloor(5);
        elevator.moveElevator(1);

        assertThat(elevator.getDirection()).isEqualTo(DOWN);
    }

    @Test
    public void elevatorShouldNotMoveBelowGroundFloor() {
        TingcoElevator elevator = busyElevator();

        assertThatThrownBy(() -> elevator.moveElevator(-1))
          .isInstanceOf(IllegalElevatorActionException.class)
          .hasMessageContaining("floor must be between");
    }

    @Test
    public void elevatorShouldNotMoveAboveTopFloor() {
        TingcoElevator elevator = new TingcoElevator(1, 10);
        elevator.setBusy(true);

        assertThatThrownBy(() -> elevator.moveElevator(11))
          .isInstanceOf(IllegalElevatorActionException.class)
          .hasMessageContaining("floor must be between");
    }

    @Test
    public void elevatorMovingUpShouldMoveItOneFloorUpPerTick() {
        TingcoElevator elevator = elevatorMovingToFloor(3);
        elevator.tick();

        assertThat(elevator.currentFloor()).isEqualTo(2);
    }

    @Test
    public void elevatorMovingDownShouldMoveItOneFloorDownPerTick() {
        TingcoElevator elevator = elevatorOnFloor(2);
        elevator.moveElevator(1);
        elevator.tick();

        assertThat(elevator.currentFloor()).isEqualTo(1);
    }

    @Test
    public void elevatorMovingOneFloorShouldBeStoppedAfterOneTick() {
        TingcoElevator elevator = elevatorMovingToFloor(2);
        elevator.tick();

        assertThat(elevator.getDirection()).isEqualTo(NONE);
    }

    @Test
    public void elevatorShouldBeAtAddressedFloorAfterMoved() {
        TingcoElevator elevator = elevatorMovingToFloor(3);
        elevator.tick();
        elevator.tick();

        assertThat(elevator.currentFloor()).isEqualTo(elevator.getAddressedFloor());
    }

    @Test
    public void elevatorNotMovingShouldStayPutAfterTick() {
        TingcoElevator elevator = busyElevator();
        elevator.tick();

        assertThat(elevator.currentFloor()).isEqualTo(1);
        assertThat(elevator.getDirection()).isEqualTo(NONE);
    }

    @Test
    public void freeElevatorShouldNotAcceptTick() {
        TingcoElevator elevator = freeElevator();

        assertThatThrownBy(elevator::tick)
          .isInstanceOf(IllegalElevatorActionException.class)
          .hasMessageContaining("must be busy before");
    }
}
