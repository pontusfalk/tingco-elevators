package com.tingco.codechallenge.elevator.api;

import java.util.*;

import static com.tingco.codechallenge.elevator.api.Elevator.Direction.NONE;

public class TingcoElevatorController implements ElevatorController {
    // todo: Set<>?
    private final List<TingcoElevator> elevators;

    TingcoElevatorController(List<TingcoElevator> elevators) {
        if (elevators == null || elevators.isEmpty()) {
            throw new IllegalElevatorControllerActionException("controller must control at least 1 elevator");
        }
        this.elevators = new ArrayList<>(elevators);
    }

    @Override
    public Elevator requestElevator(int toFloor) {
        TingcoElevator freeElevator = elevators.stream()
                                               .filter(elevator -> !elevator.isBusy())
                                               .findFirst().orElse(null);
        if (freeElevator != null) {
            freeElevator.setBusy(true);
            freeElevator.moveElevator(toFloor);
        }

        return freeElevator;
    }

    @Override
    public List<Elevator> getElevators() {
        return Collections.unmodifiableList(elevators);
    }

    /**
     * Only a busy, non-moving Tingco elevator operated by this controller can be released
     */
    @Override
    public void releaseElevator(Elevator elevator) {
        if (!(elevator instanceof TingcoElevator)) {
            throw new IllegalElevatorControllerActionException("that elevator is not a Genuine Tingco Elevator™");
        }

        TingcoElevator tingcoElevator = (TingcoElevator) elevator;
        int i = elevators.indexOf(tingcoElevator);

        if (i == -1) {
            throw new IllegalElevatorControllerActionException("that elevator is not in this shaft");
        }

        if(tingcoElevator.getDirection() != NONE) {
            throw new IllegalElevatorControllerActionException("the elevator must not be moving when released");
        }

        //re-creating the elevator to avoid accidental re-use of an elevator instance already released
        //todo: solve by returning a handle that is hiding the actual instance instead?
        //(the fundamental theorem of software engineering)
        elevators.set(i, new TingcoElevator(tingcoElevator.getId()));
        tingcoElevator.setBusy(false);
    }
}
