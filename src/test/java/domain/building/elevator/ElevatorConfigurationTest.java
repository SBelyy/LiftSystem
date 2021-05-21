package domain.building.elevator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorConfigurationTest {

    @Test
    public void constructorInvalidSpeedTest() {
        assertThrows(IllegalArgumentException.class, () -> new ElevatorConfiguration(-1, 1,1));
    }

    @Test
    public void constructorInvalidDoorClosingSpeedTest() {
        assertThrows(IllegalArgumentException.class, () -> new ElevatorConfiguration(1, -1,1));
    }

    @Test
    public void constructorInvalidCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new ElevatorConfiguration(1, 1,-1));
    }

}