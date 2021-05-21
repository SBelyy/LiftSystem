package domain;

import domain.Direction;
import domain.building.Building;
import domain.building.Floor;
import domain.building.elevator.Elevator;
import domain.building.elevator.ElevatorStatus;
import domain.PeopleSpawner;
import domain.person.Person;
import domain.person.PersonState;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import storage.SystemStorage;

import java.util.*;

import static com.google.common.base.Preconditions.*;

@Slf4j
@Getter
public class SystemController {

    private final Queue<Floor> queueUp = new LinkedList<>();
    private final Queue<Floor> queueDown = new LinkedList<>();

    private final Building building;
    private final PeopleSpawner spawner;
    private final SystemStorage storage;

    public SystemController(Building building, SystemStorage storage, int generationRateInMillis) {
        checkNotNull(building, "There is no such building");
        checkArgument(generationRateInMillis > 0, "The generation rate must be positive");
        this.building = building;
        this.storage = storage;

        building.getElevators().forEach(elevator -> elevator.setController(this));

        spawner = new PeopleSpawner(this, generationRateInMillis, building.getFloors());

    }

    public synchronized void addPerson(Person person) {
        Floor floor = building.getFloors().get(person.getInitialFloorNumber());

        Direction direction = person.getDirection();
        switch (direction) {
            case MOVE_UP:
                floor.addPersonInQueueUp(person);
                break;
            case MOVE_DOWN:
                floor.addPersonInQueueDown(person);
                break;
        }

        if (!checkAndAssignTaskToElevator(floor, person)) {
            addFloorInQueue(direction, floor);
        }

    }

    public synchronized List<Person> getPeopleToLoadIntoElevator(Direction direction, int floorNumber, int currentCapacity) {
        Floor floor = building.getFloors().get(floorNumber);

        Queue<Person> peopleInQueue;
        switch (direction) {
            case MOVE_UP:
                peopleInQueue = floor.getQueueUp();
                break;
            case MOVE_DOWN:
                peopleInQueue = floor.getQueueDown();
                break;
            default:
                throw new IllegalStateException("The queue on the floor has not yet been created");
        }

        List<Person> peopleInElevator = new ArrayList<>();
        for (Person person : peopleInQueue) {
            int weight = person.getWeightInKilo();

            if (currentCapacity - weight >= 0) {
                peopleInElevator.add(person);
                person.setState(PersonState.MOVES_IN_ELEVATOR);
                currentCapacity -= weight;
            }
        }
        peopleInQueue.removeAll(peopleInElevator);

        if (!peopleInQueue.isEmpty()) {
            addFloorInQueue(direction, floor);
        }

        return peopleInElevator;
    }

    private void addFloorInQueue(Direction direction, Floor floor) {
        switch (direction) {
            case MOVE_UP:
                if (!queueUp.contains(floor)) {
                    queueUp.offer(floor);
                }
                break;
            case MOVE_DOWN:
                if (!queueDown.contains(floor)) {
                    queueDown.offer(floor);
                }
                break;
        }
    }

    private boolean checkAndAssignTaskToElevator(Floor floor, Person person) {
        for (Elevator elevator : building.getElevators()) {

            if (!ElevatorStatus.WAITS.equals(elevator.getStatus())) {
                continue;
            }

            elevator.doAction(floor, person.getDirection());

            synchronized (this) {
                this.notifyAll();
            }

            return true;
        }

        return false;
    }

    public synchronized void getTaskIfAny(Elevator elevator) {
        if (queueDown.size() > queueUp.size()) {
            elevator.doAction(queueDown.poll(), Direction.MOVE_DOWN);
        } else if (queueDown.size() < queueUp.size()) {
            elevator.doAction(queueUp.poll(), Direction.MOVE_UP);
        }
        synchronized (this) {
            this.notifyAll();
        }
    }

    public void startSystem() {
        final List<Thread> elevatorThreads = new ArrayList<>();
        for (Elevator elevator : building.getElevators()) {
            Thread elevatorThread = new Thread(elevator);
            elevatorThreads.add(elevatorThread);
        }

        final Thread spawnerThread = new Thread(spawner);

        elevatorThreads.forEach(Thread::start);
        spawnerThread.start();
    }

    public void stopSystem() {
        for (Elevator elevator : building.getElevators()) {
            elevator.finish();
        }
        spawner.finish();
    }

    public synchronized void updateStatistics(Elevator elevator, List<Person> removedPeople) {
        /*TODO сделать два метода(вход - выход)*/
        for (Person person : removedPeople) {
            storage.persistInitialFloorsForElevator(elevator, person.getNumberOfDesiredFloor());
            storage.persistFinalFloorsForElevator(elevator, person.getInitialFloorNumber());
        }

    }

}
