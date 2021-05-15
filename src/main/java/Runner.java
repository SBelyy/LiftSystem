import domain.building.Building;
import domain.controller.SystemController;
import model.ElevatorConfiguration;

public class Runner {

    public static void main(String[] args) {
        ElevatorConfiguration configuration = new ElevatorConfiguration(1000,
                1000, 500);
        Building building = new Building(9, 1, configuration);
        SystemController controller = new SystemController(building, 11_000);

        controller.startSystem();
    }

}
