package domain.building.elevator;

import lombok.Getter;

@Getter
public class Elevator {

    private final int speedInMillis;
    private final int doorClosingSpeedInMillis;
    private final int capacity;

    private int currentWeight;
    private ElevatorCondition condition;

    public Elevator(int speedInMillis, int doorClosingSpeedInMillis, int capacityInKilo) {
        this.speedInMillis = speedInMillis;
        this.doorClosingSpeedInMillis = doorClosingSpeedInMillis;
        this.capacity = capacityInKilo;
        condition = ElevatorCondition.WAITS;
        currentWeight = 0;
    }

}
