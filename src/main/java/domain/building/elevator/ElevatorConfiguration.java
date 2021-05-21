package domain.building.elevator;

import com.google.common.base.Preconditions;
import lombok.Getter;

import static com.google.common.base.Preconditions.*;

@Getter
public class ElevatorConfiguration {

    private final int speedInMillis;
    private final int doorClosingSpeedInMillis;
    private final int capacity;

    public ElevatorConfiguration(int speedInMillis, int doorClosingSpeedInMillis, int capacity) {
        checkArgument(speedInMillis > 0, "SpeedInMillis must be positive");
        checkArgument(doorClosingSpeedInMillis > 0, "DoorClosingSpeedInMillis must be positive");
        checkArgument(capacity > 0, "Capacity must be positive");
        this.speedInMillis = speedInMillis;
        this.doorClosingSpeedInMillis = doorClosingSpeedInMillis;
        this.capacity = capacity;
    }

}
