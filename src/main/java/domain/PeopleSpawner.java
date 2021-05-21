package domain;

import domain.building.Floor;
import domain.controller.SystemController;
import domain.person.Person;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@Getter
@Slf4j
public class PeopleSpawner implements Runnable {

    private final SystemController controller;
    private final int generationRate;
    private final List<Floor> floors;

    private volatile boolean work;

    public PeopleSpawner(SystemController controller, int peopleGenerationRate, List<Floor> floors) {
        this.controller = controller;
        this.floors = floors;
        generationRate = peopleGenerationRate;
        work = true;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (work) {
            Thread.sleep(generationRate);
            int currentFloorNumber = getNumberInRange(0, floors.size() - 1);
            int numberOfDesiredFloor;
            do {
                numberOfDesiredFloor = getNumberInRange(0, floors.size() - 1);
            } while (currentFloorNumber == numberOfDesiredFloor);

            int weight = getNumberInRange(5, 130);

            Person person = new Person(currentFloorNumber, numberOfDesiredFloor,
                    weight, UUID.randomUUID().toString());
            controller.addPerson(person);
        }
    }

    public void finish() {
        work = false;
    }

    private int getNumberInRange(int min, int max) {
        int difference = max - min;
        return min + (int) (Math.random() * (difference + 1));
    }

}
