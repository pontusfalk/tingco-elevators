package com.tingco.codechallenge.elevator.api;

import org.junit.*;
import org.mockito.Mockito;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TingcoElevatorControllerTest {
    private ElevatorController singleElevatorController;

    @Before
    public void setup() {
        singleElevatorController = new TingcoElevatorController(singletonList(new TingcoElevator(1)));
    }

    @Test
    public void givenZeroElevatorsWhenCreatingTheControllerThenItShouldThrow() {
        assertThatThrownBy(() -> new TingcoElevatorController(emptyList()))
          .isInstanceOf(IllegalElevatorControllerActionException.class);
    }

    @Test
    public void givenFreeElevatorWhenRequestingAnElevatorThenOneShouldCome() {
        Elevator elevator = singleElevatorController.requestElevator(1);
        assertThat(elevator).isNotNull();
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
        Elevator rogueElevator = new TingcoElevator(1);
        assertThatThrownBy(() -> singleElevatorController.releaseElevator(rogueElevator))
          .isInstanceOf(IllegalElevatorControllerActionException.class)
          .hasMessageContaining("this shaft");
    }

    @Test
    public void givenANonTingcoElevatorWhenTryingToReleaseItThenTheControllerShouldThrow() {
        Elevator counterfeitElevator = Mockito.mock(Elevator.class);
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
