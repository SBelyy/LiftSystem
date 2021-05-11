package domain.Controller;

import domain.building.Building;
import domain.building.Floor;
import domain.person.PeopleSpawner;
import lombok.Getter;

import java.util.LinkedList;
import java.util.Queue;

@Getter
public class SystemController {

    private final Queue<Floor> queueUp = new LinkedList<>();
    private final Queue<Floor> queueDown = new LinkedList<>();

    private final Building building;
    private final PeopleSpawner spawner;

    public SystemController(Building building, int generationRateInMillis) {
        this.building = building;

        spawner = new PeopleSpawner(this, generationRateInMillis, building.getFloors());
    }

    public void addFloorInQueueUp(Floor floor) {
        if (!queueUp.contains(floor)) {
            queueUp.offer(floor);
        }
    }

    public void addFloorInQueueDown(Floor floor) {
        if (!queueDown.contains(floor)) {
            queueDown.offer(floor);
        }
    }
}
