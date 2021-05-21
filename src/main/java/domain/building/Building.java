package domain.building;

import domain.building.elevator.Elevator;
import lombok.Getter;
import domain.building.elevator.ElevatorConfiguration;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Getter
public class Building {

    private final int numberOfFloor;
    private final int numberOfElevator;

    private final List<Floor> floors;
    private final List<Elevator> elevators;

    public Building(int numberOfFloor, int numberOfElevator, ElevatorConfiguration parameters) {
        checkArgument(numberOfElevator > 0, "The number of elevator must be positive");
        checkArgument(numberOfFloor > 0, "The number of floors must be positive");
        checkNotNull(parameters, "The elevator parameters are set incorrectly");

        this.numberOfFloor = numberOfFloor;
        this.numberOfElevator = numberOfElevator;

        floors = IntStream.range(0, numberOfFloor)
                .mapToObj(Floor::new)
                .collect(Collectors.toList());

        elevators = IntStream.range(0, numberOfElevator)
                .mapToObj((i) -> new Elevator(parameters.getSpeedInMillis(),
                        parameters.getDoorClosingSpeedInMillis(), parameters.getCapacity()))
                .collect(Collectors.toList());

    }

}
