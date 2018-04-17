package com.tingco.codechallenge.elevator.api.tingco;

class TestFixtureHelper {
    private static int nextId = 0;

    static TingcoElevator freeElevator() {
        return new TingcoElevator(nextId++, 10);
    }

    static TingcoElevator busyElevator() {
        TingcoElevator elevator = freeElevator();
        elevator.setBusy(true);
        return elevator;
    }

    static TingcoElevator elevatorMovingToFloor(int floor) {
        TingcoElevator elevator = busyElevator();
        elevator.moveElevator(floor);
        return elevator;
    }

    static TingcoElevator elevatorOnFloor(int floor) {
        TingcoElevator elevator = elevatorMovingToFloor(floor);

        for (int i = elevator.currentFloor(); i < floor; i++) {
            elevator.tick();
        }

        return elevator;
    }
}
