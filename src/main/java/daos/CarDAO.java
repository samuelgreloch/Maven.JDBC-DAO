package daos;
import models.Car;

import java.util.List;

public interface CarDAO {
    Car findById(int id);
    List<Car> findAll();
    Car update(Car car);
    Car create(Car car);
    void delete(int id);
}
