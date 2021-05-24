package domain.controller;

import domain.Direction;
import domain.building.Building;
import domain.building.elevator.ElevatorConfiguration;
import domain.person.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.InMemorySystemStorage;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SystemControllerTest {

    private Building building;

    @BeforeEach
    void setUp() {
        building = new Building(2, 1,
                new ElevatorConfiguration(1, 1, 400));
    }

    @Test
    void addPersonTest() {
        SystemController controller = new SystemController(building, new InMemorySystemStorage(), 10);

        int currentFloorNumber = 0;
        int numberDesiredFloor = 1;

        Person personOne = new Person(currentFloorNumber, numberDesiredFloor, 100, "FFF");
        controller.addPerson(personOne);

        assertEquals(personOne, controller.getBuilding().getFloors().get(0).getQueueUp().poll());

        Person personTwo = new Person(currentFloorNumber, numberDesiredFloor, 100, "AAA");
        controller.addPerson(personTwo);

        assertEquals(building.getFloors().get(currentFloorNumber), controller.getQueueUp().poll());
    }

    @Test
    void getPeopleToLoadIntoElevatorTest() {
        SystemController controller = new SystemController(building, new InMemorySystemStorage(), 10);

        int currentFloorNumber = 0;
        int numberDesiredFloor = 1;

        List<Person> actualPeopleList = new ArrayList<>();
        Person personOne = new Person(currentFloorNumber, numberDesiredFloor, 100, "FFF");
        controller.addPerson(personOne);
        actualPeopleList.add(personOne);

        List<Person> peopleToLoadIntoElevator = controller.getPeopleToLoadIntoElevator(Direction.MOVE_UP,
                currentFloorNumber, building.getElevators().get(0).getCurrentCapacity());

        assertEquals(actualPeopleList, peopleToLoadIntoElevator);
    }

    @Test
    void getPeopleToLoadIntoElevatorSmallCapacityTest() {
        SystemController controller = new SystemController(building, new InMemorySystemStorage(), 10);

        int currentFloorNumber = 0;
        int numberDesiredFloor = 1;

        Person personOne = new Person(currentFloorNumber, numberDesiredFloor, 100, "FFF");
        controller.addPerson(personOne);

        List<Person> peopleToLoadIntoElevator = controller.getPeopleToLoadIntoElevator(Direction.MOVE_UP,
                currentFloorNumber, 10);

        assertEquals(new ArrayList<>(), peopleToLoadIntoElevator);
    }

    @Test
    void constructorInvalidBuildingTest() {
        assertThrows(NullPointerException.class,
                () -> new SystemController(null, new InMemorySystemStorage(), 10));
    }

    @Test
    void constructorInvalidStorageTest() {
        assertThrows(NullPointerException.class,
                () -> new SystemController(building, null, 10));
    }

    @Test
    void constructorInvalidGenerationRateTest() {
        assertThrows(IllegalArgumentException.class,
                () -> new SystemController(building, new InMemorySystemStorage(), -1));
    }

}