package com.tingco.codechallenge.elevator.api;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.tingco.codechallenge.elevator.api.TestFixtureHelper.elevatorOnFloor;
import static com.tingco.codechallenge.elevator.api.TestFixtureHelper.freeElevator;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

public class TingcoElevatorControllerTest {
    private ElevatorController singleElevatorController;
    private TingcoElevatorShaftEngine engine = mock(TingcoElevatorShaftEngine.class);

    @Before
    public void setup() {
        singleElevatorController = new TingcoElevatorController(engine, singletonList(freeElevator()));
    }

    @Test
    public void givenZeroElevatorsWhenCreatingTheControllerThenItShouldThrow() {
        assertThatThrownBy(() -> new TingcoElevatorController(engine, emptyList()))
          .isInstanceOf(IllegalElevatorControllerActionException.class);
    }

    @Test
    public void givenFreeElevatorWhenRequestingAnElevatorThenTheClosestFreeElevatorShouldCome() {
        TingcoElevator firstFloorElevator = elevatorOnFloor(1);
        firstFloorElevator.setBusy(false);
        TingcoElevator fourthFloorElevator = elevatorOnFloor(4);
        fourthFloorElevator.setBusy(false);

        ElevatorController elevatorController =
          new TingcoElevatorController(engine, asList(firstFloorElevator, fourthFloorElevator));
        Elevator elevator = elevatorController.requestElevator(3);

        assertThat(elevator.getId()).isEqualTo(fourthFloorElevator.getId());
    }

    @Test
    public void givenNoFreeElevatorWhenRequestingAnElevatorThenNoneShouldCome() {
        singleElevatorController.requestElevator(1);

        Elevator elevator = singleElevatorController.requestElevator(1);
        assertThat(elevator).isNull();
    }

    @Test
    public void givenAnElevatorWhenReleasedThenItShouldNotBeUsable() {
        Elevator releasedElevator = singleElevatorController.requestElevator(1);
        singleElevatorController.releaseElevator(releasedElevator);

        assertThatThrownBy(() -> releasedElevator.moveElevator(1))
          .isInstanceOf(IllegalElevatorActionException.class);
    }

    @Test
    // given bdd naming standards when writing method names then names become long
    public void givenAnElevatorNotControlledByTheControllerWhenTryingToReleaseTheElevatorThenControllerShouldThrow() {
        Elevator rogueElevator = new TingcoElevator(1, 10);
        assertThatThrownBy(() -> singleElevatorController.releaseElevator(rogueElevator))
          .isInstanceOf(IllegalElevatorControllerActionException.class)
          .hasMessageContaining("this shaft");
    }

    @Test
    public void givenANonTingcoElevatorWhenTryingToReleaseItThenTheControllerShouldThrow() {
        Elevator counterfeitElevator = mock(Elevator.class);

        assertThatThrownBy(() -> singleElevatorController.releaseElevator(counterfeitElevator))
          .isInstanceOf(IllegalElevatorControllerActionException.class)
          .hasMessageContaining("not a Genuine Tingco Elevator™");
    }

    @Test
    public void givenAnElevatorStillMovingWhenTryingToReleaseItThenTheControllerShouldThrow() {
        Elevator releasedElevator = singleElevatorController.requestElevator(2);

        assertThatThrownBy(() -> singleElevatorController.releaseElevator(releasedElevator))
          .isInstanceOf(IllegalElevatorControllerActionException.class)
          .hasMessageContaining("not be moving");
    }

    @Test
    public void givenAnElevatorWhenReleasedAndRerequestingTheSameElevatorThenTheOldReferenceCantBeUsed() {
        Elevator firstElevatorReference = singleElevatorController.requestElevator(1);
        singleElevatorController.releaseElevator(firstElevatorReference);
        Elevator secondElevatorReference = singleElevatorController.requestElevator(1);

        assertThat(firstElevatorReference.getId()).isEqualTo(secondElevatorReference.getId());
        assertThatThrownBy(() -> firstElevatorReference.moveElevator(1))
          .isInstanceOf(IllegalElevatorActionException.class);
    }

    @Test
    public void givenControllersElevatorListWhenTryingToChangeItThenItShouldThrow() {
        List<Elevator> elevatorsShouldBeUnmodifiable = singleElevatorController.getElevators();

        assertThatThrownBy(elevatorsShouldBeUnmodifiable::clear)
          .isInstanceOf(UnsupportedOperationException.class);
    }
}
