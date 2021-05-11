package model;

import lombok.Getter;

@Getter
public class ElevatorConfiguration {

    private final int speedInMillis;
    private final int doorClosingSpeedInMillis;
    private final int capacity;

    public ElevatorConfiguration(int speedInMillis, int doorClosingSpeedInMillis, int capacity) {
        this.speedInMillis = speedInMillis;
        this.doorClosingSpeedInMillis = doorClosingSpeedInMillis;
        this.capacity = capacity;
    }

}
