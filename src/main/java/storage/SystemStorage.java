package storage;

import domain.building.elevator.Elevator;

import java.util.List;
import java.util.Map;

public interface SystemStorage {

    int loadTotalNumberOfPeopleTransportedByElevator(Elevator elevator);

    Map<Integer, Integer> loadAllInitialFloorsForElevator(Elevator elevator);

    Map<Integer, Integer> loadAllFinalFloorsForElevator(Elevator elevator);

    void persistInitialFloorsForElevator(Elevator elevator, int number);

    void persistFinalFloorsForElevator(Elevator elevator, int number);

}
