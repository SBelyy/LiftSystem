package domain.building;

import domain.building.elevator.ElevatorConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingTest {

    private ElevatorConfiguration configuration;

    @BeforeEach
    void setUp(){
        configuration = new ElevatorConfiguration(1, 1, 1);
    }

    @Test
    public void constructorInvalidNumberOfFloorTest() {
        int invalidNumberOfFloor = -1;
        int numberOfElevators = 1;

        assertThrows(IllegalArgumentException.class, () -> new Building(invalidNumberOfFloor, numberOfElevators, configuration));
    }

    @Test
    public void constructorInvalidNumberOfElevatorTest() {
        int numberOfFloor = 5;
        int invalidNumberOfElevators = -1;

        assertThrows(IllegalArgumentException.class, () -> new Building(numberOfFloor, invalidNumberOfElevators, configuration));
    }

    @Test
    public void constructorInvalidConfigurationTest() {
        int numberOfFloor = 1;
        int numberOfElevators = 1;

        assertThrows(NullPointerException.class, () -> new Building(numberOfFloor, numberOfElevators, null));
    }

    @Test
    public void constructorTest() {
        int numberOfFloor = 1;
        int numberOfElevators = 1;

        Building building = new Building(numberOfFloor, numberOfElevators, configuration);

        assertEquals(numberOfFloor, building.getFloors().size());
        assertEquals(numberOfElevators, building.getElevators().size());
    }

}