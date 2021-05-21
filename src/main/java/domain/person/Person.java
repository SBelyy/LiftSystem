package domain.person;

import domain.Direction;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import static com.google.common.base.Preconditions.*;

@Getter
@Setter
@Slf4j
@ToString
public class Person {

    private final int initialFloorNumber;
    private final int numberOfDesiredFloor;
    private final int weightInKilo;
    private final String id;
    private final Direction direction;

    private PersonState state;

    public Person(int currentFloorNumber, int numberOfDesiredFloor, int weightInKilo, String id) {
        checkArgument(currentFloorNumber >= 0, "The current floor number must be non-negative");
        checkArgument(numberOfDesiredFloor >= 0, "The number of desired floor must be non-negative");

        this.initialFloorNumber = currentFloorNumber;
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
