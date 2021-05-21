package domain.building;

import domain.person.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FloorTest {

    private Floor floor;

    @BeforeEach
    void setUp(){
        floor = new Floor(0);
    }

    @Test
    void addPersonInQueueUpTest() {
        Person person = new Person(0, 1, 27, "1212");
        floor.addPersonInQueueUp(person);

        assertEquals(person, floor.getQueueUp().peek());
    }

    @Test
    void addPersonInQueueDownTest() {
        Person person = new Person(0, 1, 27, "1212");
        floor.addPersonInQueueDown(person);

        assertEquals(person, floor.getQueueDown().peek());
    }

    @Test
    void constructorTest(){
        assertThrows(IllegalArgumentException.class, () -> new Floor(-1));
    }

}