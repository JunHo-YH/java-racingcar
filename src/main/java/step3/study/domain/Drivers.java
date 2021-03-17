package step3.study.domain;

import step3.study.dto.RequestRacingDTO;
import step3.study.util.NumberGenerator;

import java.util.List;
import java.util.stream.Collectors;

public class Drivers {
    private final List<Driver> drivers;

    public Drivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public static List<Driver> of(RequestRacingDTO requestRacingDTO) {
        List<String> driverNames = requestRacingDTO.getDriverNames();

        return driverNames.stream()
                .map(driverName -> new Driver(driverName, new Car(new Position(0))))
                .collect(Collectors.toList());
    }

    public void moveCars(NumberGenerator numberGenerator) {
        for (Driver driver : drivers) {
            driver.moveCar(numberGenerator);
        }
    }

    public List<Driver> getDriverList() {
        return drivers;
    }

    public List<String> getWinnerNames() {
        int maxPosition = getMaxPosition();
        return this.drivers
                .stream()
                .filter(driver -> driver.position() == maxPosition)
                .map(Driver::name)
                .collect(Collectors.toList());
    }

    private int getMaxPosition() {
        return this.drivers
                .stream()
                .mapToInt(Driver::position)
                .max()
                .getAsInt();

    }
}
