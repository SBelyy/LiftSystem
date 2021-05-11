package domain.person;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Person {

    private final int currentFloorNumber;
    private final int numberOfDesiredFloor;
    private final int weightInKilo;
    private final String id;
    private final PersonIntentStatus intent;

    private PersonState state;

    public Person(int currentFloorNumber, int numberOfDesiredFloor, int weightInKilo, String id) {
        this.currentFloorNumber = currentFloorNumber;
        this.numberOfDesiredFloor = numberOfDesiredFloor;
        this.weightInKilo = weightInKilo;
        this.id = id;

        state = PersonState.WAITING_FOR_ELEVATOR;

        if (currentFloorNumber < numberOfDesiredFloor) {
            intent = PersonIntentStatus.MOVE_UP;
        } else {
            intent = PersonIntentStatus.MOVE_DOWN;
        }
    }

}
