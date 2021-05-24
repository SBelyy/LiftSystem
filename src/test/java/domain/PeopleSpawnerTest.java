package domain;

import domain.building.Building;
import domain.building.Floor;
import domain.building.elevator.ElevatorConfiguration;
import domain.controller.SystemController;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import storage.InMemorySystemStorage;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PeopleSpawnerTest {

    @SneakyThrows
    @Test
    void runTest() {
        int generationRate = 100;
        Building building = new Building(2, 1,
                new ElevatorConfiguration(100, 100, 100));
        SystemController controller = new SystemController(building, new InMemorySystemStorage(), generationRate);

        PeopleSpawner spawner = controller.getSpawner();
        Thread spawnerThread = new Thread(spawner);
        spawnerThread.start();

        TimeUnit.MILLISECONDS.sleep(1000);

        spawner.finish();

        assertEquals(9, getTotalPeopleInQueue(building));
    }

    private int getTotalPeopleInQueue(Building building) {
        int result = 0;
        for (Floor floor : building.getFloors()) {
            result += floor.getQueueUp().size();
            result += floor.getQueueDown().size();
        }
        return result;
    }
}