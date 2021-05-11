package domain.building;

import domain.building.elevator.Elevator;
import lombok.Getter;
import model.ElevatorConfiguration;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

@Getter
public class Building {

    private final int numberOfFloor;
    private final int numberOfElevator;

    private final List<Floor> floors = new ArrayList<>();
    private final List<Elevator> elevators = new ArrayList<>();


    public Building(int numberOfFloor, int numberOfElevator, ElevatorConfiguration parameters) {
        checkArgument(numberOfElevator > 0, "The number of elevator must be positive");
        checkArgument(numberOfFloor > 0, "The number of floors must be positive");

        this.numberOfFloor = numberOfFloor;
        this.numberOfElevator = numberOfElevator;

        for (int i = 0; i < numberOfFloor; i++) {
            floors.add(new Floor(i));
        }

        for (int i = 0; i < numberOfElevator; i++) {
            elevators.add(new Elevator(parameters.getSpeedInMillis(),
                    parameters.getDoorClosingSpeedInMillis(), parameters.getCapacity()));
        }
    }


}
