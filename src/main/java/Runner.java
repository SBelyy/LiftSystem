import domain.building.Building;
import domain.SystemController;
import lombok.SneakyThrows;
import domain.building.elevator.ElevatorConfiguration;
import storage.InMemorySystemStorage;
import storage.SystemStorage;

import java.util.concurrent.TimeUnit;

public class Runner {

    @SneakyThrows
    public static void main(String[] args) {
        ElevatorConfiguration configuration = new ElevatorConfiguration(100,
                100, 500);
        Building building = new Building(3, 1, configuration);
        SystemStorage storage = new InMemorySystemStorage();
        SystemController controller = new SystemController(building, storage, 200);

        controller.startSystem();

        TimeUnit.SECONDS.sleep(20);

        controller.stopSystem();

        TimeUnit.SECONDS.sleep(2);

        System.out.println(storage.loadAllInitialFloorsForElevator(building.getElevators().get(0)));
        System.out.println(storage.loadAllFinalFloorsForElevator(building.getElevators().get(0)));
        System.out.println(storage.loadTotalNumberOfPeopleTransportedByElevator(building.getElevators().get(0)));
    }

}
