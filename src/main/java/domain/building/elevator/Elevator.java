package domain.building.elevator;

import domain.Direction;
import domain.building.Floor;
import domain.controller.SystemController;
import domain.person.Person;
import domain.person.PersonState;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.*;

@Getter
@Slf4j
public class Elevator implements Runnable {

    private final int speedInMillis;
    private final int doorClosingSpeedInMillis;
    private final int capacity;
    private final String id;

    private final List<Person> people = new ArrayList<>();

    private int currentFloor;
    private Floor targetFloor;
    private Direction direction;

    private SystemController controller;
    private int currentCapacity;
    private ElevatorStatus status;

    private volatile boolean work;

    public Elevator(int speedInMillis, int doorClosingSpeedInMillis, int capacityInKilo) {
        this.speedInMillis = speedInMillis;
        this.doorClosingSpeedInMillis = doorClosingSpeedInMillis;
        this.capacity = capacityInKilo;
        currentCapacity = capacity;

        status = ElevatorStatus.WAITS;
        id = UUID.randomUUID().toString();
        currentFloor = 0;

        work = true;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (work) {
            if (targetFloor == null) {
                //log.debug("{} is waiting for a task", this);
                synchronized (controller) {
                    controller.wait();
                }
                continue;
            }

            if (currentFloor == targetFloor.getFloorNumber()) {
                status = ElevatorStatus.WAITING_TO_LOADING;

                log.debug("{} has arrived at the desired floor", this);

                List<Person> newPeople = controller.getPeopleToLoadIntoElevator(direction, currentFloor, currentCapacity);
                loadPeopleIntoElevator(newPeople);

                Thread.sleep(doorClosingSpeedInMillis);
                status = ElevatorStatus.MOVING;

                List<Integer> stopovers = getStopovers();
                do {
                    moving(direction);

                    if (stopovers.contains(currentFloor)) {
                        status = ElevatorStatus.WAITING_FOR_UNLOADING;
                        Thread.sleep(doorClosingSpeedInMillis);

                        dropPeopleOff(currentFloor);

                        stopovers.remove(Integer.valueOf(currentFloor));
                    }
                } while (!stopovers.isEmpty());

                status = ElevatorStatus.WAITS;
                targetFloor = null;

                controller.getTaskIfAny(this);
            } else {
                movingToTheTask(targetFloor.getFloorNumber());
            }

        }
    }

    private void movingToTheTask(int target) {
        if (target < currentFloor) {
            moving(Direction.MOVE_DOWN);
        } else {
            moving(Direction.MOVE_UP);
        }
    }

    @SneakyThrows
    private void moving(Direction direction) {
        Thread.sleep(speedInMillis);
        status = ElevatorStatus.MOVING;

        if (direction == Direction.MOVE_UP) {
            currentFloor++;
        } else {
            currentFloor--;
        }

        log.debug("{} arrived at the {} floor", this, currentFloor);
    }

    private void dropPeopleOff(int destinationFloor) {
        List<Person> peopleToRemove = new ArrayList<>();

        for (Person person : people) {

            if (person.getNumberOfDesiredFloor() != destinationFloor) {
                continue;
            }

            peopleToRemove.add(person);
            person.setState(PersonState.ARRIVED);

            log.debug("{} got out of the {}", person, this);

            currentCapacity += person.getWeightInKilo();

        }

        people.removeAll(peopleToRemove);
        controller.peopleGotOut(this, peopleToRemove);
    }

    public void finish() {
        work = false;
    }

    public void doAction(Floor targetFloor, Direction direction) {
        checkArgument(status == ElevatorStatus.WAITS, "The elevator is busy now");
        this.targetFloor = targetFloor;
        this.direction = direction;
        log.debug("{} got a new task", this);
    }

    private List<Integer> getStopovers() {
        return people.stream()
                .map(Person::getNumberOfDesiredFloor)
                .distinct()
                .collect(Collectors.toList());
    }

    private void loadPeopleIntoElevator(List<Person> persons) {
        for (Person person : persons) {
            people.add(person);
            currentCapacity -= person.getWeightInKilo();
            log.debug("{} entered {}", person, this);
        }
        controller.peopleEntered(this, persons);
    }

    public void setController(SystemController controller) {
        this.controller = controller;
    }

    public SystemController getController() {
        checkNotNull(controller, "System controller not assigned yet");
        return controller;
    }

    @Override
    public String toString() {
        return "Elevator{" +
                "id=" + id +
                ", capacity='" + capacity + '\'' +
                ", currentCapacity=" + currentCapacity +
                '}';
    }

}
