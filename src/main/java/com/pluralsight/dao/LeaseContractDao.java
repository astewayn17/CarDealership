package com.pluralsight.dao;

import com.pluralsight.models.LeaseContract;
import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class LeaseContractDao {
    private BasicDataSource dataSource;

    public LeaseContractDao(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(LeaseContract contract) {
        String query = """
            INSERT INTO LeaseContracts (
                VIN, APR, LeaseDate, TermMonths, ExpectedEndingValue, LeaseFee, VehiclePrice
            ) VALUES (?, ?, ?, ?, ?, ?, ?);""";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, contract.getVehicleSold().split("\\|")[0]);
            preparedStatement.setDouble(2, contract.getApr() * 100);
            preparedStatement.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            preparedStatement.setInt(4, contract.getTermMonths());
            preparedStatement.setDouble(5, contract.getExpectedEndingValue());
            preparedStatement.setDouble(6, contract.getLeaseFee());
            preparedStatement.setDouble(7, contract.getVehiclePrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving lease contract: " + e.getMessage(), e);
        }
    }
}