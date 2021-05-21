package storage;

import domain.building.elevator.Elevator;

import java.util.*;

public class InMemorySystemStorage implements SystemStorage {

    private final Map<Elevator, Integer> totalNumberPeopleTransported = new HashMap<>();
    private final Map<Elevator, Map<Integer, Integer>> initialFloors = new HashMap<>();
    private final Map<Elevator, Map<Integer, Integer>> finalFloors = new HashMap<>();

    @Override
    public int loadTotalNumberOfPeopleTransportedByElevator(Elevator elevator) {
        return totalNumberPeopleTransported.getOrDefault(elevator, 0);
    }

    @Override
    public Map<Integer, Integer> loadAllInitialFloorsForElevator(Elevator elevator) {
        return initialFloors.getOrDefault(elevator, Collections.emptyMap());
    }

    @Override
    public Map<Integer, Integer> loadAllFinalFloorsForElevator(Elevator elevator) {
        return finalFloors.getOrDefault(elevator, Collections.emptyMap());
    }

    @Override
    public void persistInitialFloorsForElevator(Elevator elevator, int floorNumber) {
        Map<Integer, Integer> initialFloorsMap = initialFloors.computeIfAbsent(elevator, v -> new HashMap<>());

        initialFloorsMap.compute(floorNumber, (k, count) -> (count == null) ? 1 : count + 1);
    }

    @Override
    public void persistFinalFloorsForElevator(Elevator elevator, int floorNumber) {
        Map<Integer, Integer> finalFloorsMap = finalFloors.computeIfAbsent(elevator, v -> new HashMap<>());

        finalFloorsMap.compute(floorNumber, (k, count) -> (count == null) ? 1 : count + 1);
        totalNumberPeopleTransported.compute(elevator, (k, count) -> (count == null) ? 1 : count + 1);
    }

}
