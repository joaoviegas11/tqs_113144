package cars.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car,Long>{
    public Car findByCarId(Long carId);
    public List<Car> findAll();
    public List<Car> findBySegmentAndEngineType(String segment, String engineType);
}
