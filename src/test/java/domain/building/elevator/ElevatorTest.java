package domain.building.elevator;

import domain.building.Building;
import domain.controller.SystemController;
import domain.person.Person;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import storage.InMemorySystemStorage;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorTest {

    @SneakyThrows
    @Test
    void runTest() {
        Building building = new Building(2, 1,
                new ElevatorConfiguration(100, 100, 100));
        SystemController controller = new SystemController(building, new InMemorySystemStorage(), 100);

        Person person = new Person(0, 1, 60, "1");
        controller.addPerson(person);

        Elevator elevator = controller.getBuilding().getElevators().get(0);
        assertEquals(ElevatorStatus.GOT_A_NEW_TASK, elevator.getStatus());
        assertEquals(0, controller.getStorage().loadTotalNumberOfPeopleTransportedByElevator(elevator));

        Thread elevatorThread = new Thread(elevator);
        elevatorThread.start();

        TimeUnit.MILLISECONDS.sleep(500);
        elevator.finish();

        assertEquals(ElevatorStatus.WAITS, elevator.getStatus());
        assertEquals(1, elevator.getCurrentFloor());
        assertEquals(1, controller.getStorage().loadTotalNumberOfPeopleTransportedByElevator(elevator));
    }

}