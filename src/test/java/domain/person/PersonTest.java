package domain.person;

import domain.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void directionTest() {
        Person personUp = new Person(1, 2, 20, "as");

        assertEquals(Direction.MOVE_UP, personUp.getDirection());

        Person personDown = new Person(2, 1, 20, "as");

        assertEquals(Direction.MOVE_DOWN, personDown.getDirection());
    }

    @Test
    void constructorInvalidCurrentFloorTest() {
        assertThrows(IllegalArgumentException.class,
                () -> new Person(-1, 1, 1, "1"));
    }

    @Test
    void constructorInvalidDesiredFloorTest() {
        assertThrows(IllegalArgumentException.class,
                () -> new Person(1, -1, 1, "1"));
    }

    @Test
    void constructorInvalidWeightTest() {
        assertThrows(IllegalArgumentException.class,
                () -> new Person(1, 1, -1, "1"));
    }

}