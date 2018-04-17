package com.tingco.codechallenge.elevator.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.tingco.codechallenge.elevator.api.Elevator.Direction.NONE;
import static java.lang.Math.abs;

public class TingcoElevatorController implements ElevatorController {
    private static final Logger logger = LoggerFactory.getLogger(TingcoElevatorController.class);

    // todo: Set<>?
    private final List<TingcoElevator> elevators;
    private final List<TingcoElevator> freeElevators;
    private final TingcoElevatorShaftEngine engine;

    public TingcoElevatorController(TingcoElevatorShaftEngine engine, List<TingcoElevator> elevators) {
        if (elevators == null || elevators.isEmpty()) {
            throw new IllegalElevatorControllerActionException("controller must control at least 1 elevator");
        }
        this.elevators = new ArrayList<>(elevators);
        this.freeElevators = new ArrayList<>(elevators);

        this.engine = engine;
    }

    @Override
    public Elevator requestElevator(int toFloor) {
        if (freeElevators.isEmpty()) {
            return null;
        }

        TingcoElevator closest = freeElevators.get(0);
        for (TingcoElevator elevator : freeElevators) {
            //todo: break if diff = 0
            closest = abs(closest.currentFloor() - toFloor) < abs(elevator.currentFloor() - toFloor)
                      ? closest : elevator;
        }

        logger.debug("elevator {} is requested to floor {}", closest.getId(), toFloor);

        freeElevators.remove(closest);

        closest.setBusy(true);
        closest.moveElevator(toFloor);
        engine.startElevator(closest);

        return closest;
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

        if (tingcoElevator.getDirection() != NONE) {
            throw new IllegalElevatorControllerActionException("the elevator must not be moving when released");
        }

        logger.debug("elevator {} is released on floor {}", elevator.getId(), elevator.currentFloor());

        //re-creating the elevator to avoid accidental re-use of an already released elevator instance
        //todo: solve by returning a handle that is hiding the actual instance instead?
        //(the fundamental theorem of software engineering)
        tingcoElevator.setBusy(false);
        engine.stopElevator(tingcoElevator);
        TingcoElevator newTingcoElevator = new TingcoElevator(tingcoElevator.getId(), tingcoElevator.getFloors());
        freeElevators.add(newTingcoElevator);
        elevators.set(i, newTingcoElevator);
    }
}
