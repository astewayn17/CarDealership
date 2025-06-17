
package com.pluralsight.dao;

import com.pluralsight.models.Vehicle;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleDao {

    private BasicDataSource dataSource;

    public VehicleDao(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Vehicle> getVehiclesByPrice(double min, double max) {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = """
                SELECT *
                FROM Vehicles
                WHERE Price BETWEEN ? AND ? AND Sold = FALSE;""";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, min);
            preparedStatement.setDouble(2, max);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) { vehicles.add(mapVehicle(resultSet)); }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error when getting vehicles by price: " + e.getMessage(), e);
        }
        return vehicles;
    }

    public List<Vehicle> getVehiclesByMakeModel(String make, String model) {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = """
                SELECT *
                FROM Vehicles
                WHERE Make = ? AND Model = ? AND Sold = FALSE;""";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, make);
            preparedStatement.setString(2, model);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) { vehicles.add(mapVehicle(resultSet)); }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error when getting vehicles by make/model: " + e.getMessage(), e);
        }
        return vehicles;
    }

    public List<Vehicle> getVehiclesByYear(int minYear, int maxYear) {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = """
                SELECT *
                FROM Vehicles
                WHERE Year BETWEEN ? AND ? AND Sold = FALSE;""";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, minYear);
            preparedStatement.setInt(2, maxYear);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) { vehicles.add(mapVehicle(resultSet)); }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error when getting vehicles by year: " + e.getMessage(), e);
        }
        return vehicles;
    }

    public List<Vehicle> getVehiclesByColor(String color) {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = """
                SELECT *
                FROM Vehicles
                WHERE Color = ? AND Sold = FALSE;""";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, color);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) { vehicles.add(mapVehicle(resultSet)); }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error when getting vehicles by color: " + e.getMessage(), e);
        }
        return vehicles;
    }

    public List<Vehicle> getVehiclesByMileage(int min, int max) {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = """
                SELECT *
                FROM Vehicles
                WHERE Odometer BETWEEN ? AND ? AND Sold = FALSE;""";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, min);
            preparedStatement.setInt(2, max);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) { vehicles.add(mapVehicle(resultSet)); }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error when getting vehicles by mileage: " + e.getMessage(), e);
        }
        return vehicles;
    }

    public List<Vehicle> getVehiclesByType(String type) {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = """
                SELECT *
                FROM Vehicles
                WHERE VehicleType = ? AND Sold = FALSE;""";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, type);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) { vehicles.add(mapVehicle(resultSet)); }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error when getting vehicles by type: " + e.getMessage(), e);
        }
        return vehicles;
    }

    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = """
                SELECT *
                FROM Vehicles
                WHERE Sold = FALSE;""";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) { vehicles.add(mapVehicle(resultSet)); }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error when getting all vehicles: " + e.getMessage(), e);
        }
        return vehicles;
    }

    public Vehicle getVehicleByVin(String vin) {
        String query = """
                SELECT *
                FROM Vehicles
                WHERE VIN = ? AND Sold = FALSE;""";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, vin);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) return mapVehicle(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error when finding vehicle by VIN: " + e.getMessage(), e);
        }
        return null;
    }

    public void addVehicle(Vehicle vehicle) {
        String query = """
                INSERT INTO Vehicles (VIN, Year, Make, Model, VehicleType, Color, Odometer, Price, Sold)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, FALSE);""";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, vehicle.getVin());
            preparedStatement.setInt(2, vehicle.getYear());
            preparedStatement.setString(3, vehicle.getMake());
            preparedStatement.setString(4, vehicle.getModel());
            preparedStatement.setString(5, vehicle.getVehicleType());
            preparedStatement.setString(6, vehicle.getColor());
            preparedStatement.setInt(7, vehicle.getOdometer());
            preparedStatement.setDouble(8, vehicle.getPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error when adding a vehicle: " + e.getMessage(), e);
        }
    }

    public void removeVehicleByVin(String vin) {
        String query = """
                UPDATE Vehicles
                SET Sold = TRUE
                WHERE VIN = ?;""";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, vin);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error when marking vehicle as sold: " + e.getMessage(), e);
        }
    }

    public void deleteVehicleByVin(String vin) {
        String query = """
                DELETE FROM Vehicles
                WHERE VIN = ?;""";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, vin);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error when deleting vehicle: " + e.getMessage(), e);
        }
    }

    private Vehicle mapVehicle(ResultSet resultSet) throws SQLException {
        return new Vehicle(
                resultSet.getString("VIN"),
                resultSet.getInt("Year"),
                resultSet.getString("Make"),
                resultSet.getString("Model"),
                resultSet.getString("VehicleType"),
                resultSet.getString("Color"),
                resultSet.getInt("Odometer"),
                resultSet.getDouble("Price")
        );
    }
}