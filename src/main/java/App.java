import daos.CarDAO;
import models.Car;
import models.CarDAOImpl;

public class App {
    public static void main(String[] args) {
        CarDAO carDAO = new CarDAOImpl();

        // Create
        Car newCar = new Car();
        newCar.setMake("BMW");
        newCar.setModel("X5");
        newCar.setYear(2022);
        newCar.setColor("Black");
        newCar.setVin("WBAXXXXXXXXXXXXXX");
        carDAO.create(newCar);

        // Read All
        System.out.println("All Cars:");
        carDAO.findAll().forEach(System.out::println);

        // Update
        newCar.setColor("White");
        carDAO.update(newCar);

        // Find by ID
        System.out.println("Car with ID 1: " + carDAO.findById(1));

        // Delete
        carDAO.delete(newCar.getId());
    }
}
