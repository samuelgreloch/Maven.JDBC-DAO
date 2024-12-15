package models;

import daos.CarDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAOImpl implements CarDAO {
    private final String jdbcUrl = "jdbc:mysql://localhost:3306/jdbc_lab";
    private final String jdbcUser = "root";
    private final String jdbcPassword = "SX09890$";

    @Override
    public Car findById(int id) {
        String query = "SELECT * FROM car WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapToCar(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Car> findAll() {
        List<Car> cars = new ArrayList<>();
        String query = "SELECT * FROM car";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                cars.add(mapToCar(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    @Override
    public Car update(Car car) {
        String query = "UPDATE car SET make = ?, model = ?, year = ?, color = ?, vin = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, car.getMake());
            statement.setString(2, car.getModel());
            statement.setInt(3, car.getYear());
            statement.setString(4, car.getColor());
            statement.setString(5, car.getVin());
            statement.setInt(6, car.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return car;
    }

    @Override
    public Car create(Car car) {
        String query = "INSERT INTO car (make, model, year, color, vin) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, car.getMake());
            statement.setString(2, car.getModel());
            statement.setInt(3, car.getYear());
            statement.setString(4, car.getColor());
            statement.setString(5, car.getVin());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                car.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return car;
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM car WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Car mapToCar(ResultSet resultSet) throws SQLException {
        Car car = new Car();
        car.setId(resultSet.getInt("id"));
        car.setMake(resultSet.getString("make"));
        car.setModel(resultSet.getString("model"));
        car.setYear(resultSet.getInt("year"));
        car.setColor(resultSet.getString("color"));
        car.setVin(resultSet.getString("vin"));
        return car;
    }
}
