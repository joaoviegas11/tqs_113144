package cars.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarManagerService {

    @Autowired
    private CarRepository carRepository;


    public Car save(Car car) {
        return carRepository.save(car);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Optional<Car> getCarDetails(Long carId) {
        return Optional.ofNullable(carRepository.findByCarId(carId));
    }

    public List<Car> findReplacementCar(Long carId) {
        Optional<Car> carOpt = carRepository.findById(carId);
        if (carOpt.isEmpty()) {
            return List.of();
        }

        Car originalCar = carOpt.get();
        List<Car> cars=carRepository.findBySegmentAndEngineType(originalCar.getSegment(), originalCar.getEngineType());
        cars.remove(originalCar);
        return cars;
    }
}