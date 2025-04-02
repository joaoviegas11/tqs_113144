package cars.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CarController {

    @Autowired
    private final CarManagerService carManagerService;

    public CarController(CarManagerService carManagerService) {
        this.carManagerService = carManagerService;
    }


    @PostMapping(path = "/car")
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        Car savedCar = carManagerService.save(car);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCar);
    }

    @GetMapping(path = "/cars")
    public List<Car> getAllCars() {
        return carManagerService.getAllCars();
    }

    @GetMapping(path = "/car/{carId}")
    public ResponseEntity<Car> getCarById(@PathVariable Long carId) {
        Optional<Car> car = carManagerService.getCarDetails(carId);
        return car.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping(path = "/car/{carId}/replacement")
    public ResponseEntity<List<Car>> findReplacementCar(@PathVariable Long carId) {
        List<Car> replacementCars = carManagerService.findReplacementCar(carId);
        if (replacementCars.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(replacementCars);
    }
}
