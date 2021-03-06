package domain.building;

import domain.person.Person;
import lombok.Getter;

import java.util.LinkedList;
import java.util.Queue;

import static com.google.common.base.Preconditions.*;

@Getter
public class Floor {

    private final Queue<Person> queueUp = new LinkedList<>();
    private final Queue<Person> queueDown = new LinkedList<>();

    private final int floorNumber;

    public Floor(int floorNumber) {
        checkArgument(floorNumber >= 0, "The floor number must be non-negative");
        this.floorNumber = floorNumber;
    }

    public void addPersonInQueueUp(Person person) {
        queueUp.offer(person);
    }

    public void addPersonInQueueDown(Person person) {
        queueDown.offer(person);
    }

}
