package domain.person;

import domain.Direction;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@ToString
public class Person {

    private final int currentFloorNumber;
    private final int numberOfDesiredFloor;
    private final int weightInKilo;
    private final String id;
    private final Direction direction;

    private PersonState state;

    public Person(int currentFloorNumber, int numberOfDesiredFloor, int weightInKilo, String id) {
        this.currentFloorNumber = currentFloorNumber;
        this.numberOfDesiredFloor = numberOfDesiredFloor;
        this.weightInKilo = weightInKilo;
        this.id = id;

        state = PersonState.WAITING_FOR_ELEVATOR;

        if (currentFloorNumber < numberOfDesiredFloor) {
            direction = Direction.MOVE_UP;
        } else {
            direction = Direction.MOVE_DOWN;
        }
        log.debug("{} is created", this);
    }

}
