package storage;

import domain.building.elevator.Elevator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class InMemorySystemStorageTest {

    private SystemStorage storage;
    private Elevator elevator;

    @BeforeEach
    void setUp() {
        storage = new InMemorySystemStorage();
        elevator = new Elevator(1, 1, 1);
    }

    @Test
    void persistInitialFloorsForElevatorTest() {
        int floorNumber = 1;
        int numberOfIterations = 20;

        for (int i = 0; i < numberOfIterations; i++) {
            storage.persistInitialFloorsForElevator(elevator, floorNumber);
        }

        assertEquals(numberOfIterations, storage.loadAllInitialFloorsForElevator(elevator).get(floorNumber));
    }

    @Test
    void persistFinalFloorsForElevatorTest() {
        int floorNumber = 3;
        int numberOfIterations = 10;

        for (int i = 0; i < numberOfIterations; i++) {
            storage.persistFinalFloorsForElevator(elevator, floorNumber);
        }

        assertEquals(numberOfIterations, storage.loadAllFinalFloorsForElevator(elevator).get(floorNumber));
        assertEquals(numberOfIterations, storage.loadTotalNumberOfPeopleTransportedByElevator(elevator));
    }

    @Test
    void loadEmptyTotalNumberOfPeopleTransportedByElevatorTest() {
        assertEquals(0, new InMemorySystemStorage().loadTotalNumberOfPeopleTransportedByElevator(elevator));
    }

    @Test
    void loadEmptyInitialFloorsForElevatorTest() {
        assertEquals(Collections.emptyMap(), new InMemorySystemStorage().loadAllInitialFloorsForElevator(elevator));
    }

    @Test
    void loadEmptyFinalFloorsForElevatorTest() {
        assertEquals(Collections.emptyMap(), new InMemorySystemStorage().loadAllFinalFloorsForElevator(elevator));
    }

}