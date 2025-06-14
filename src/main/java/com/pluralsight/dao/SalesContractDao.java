package com.pluralsight.dao;

import com.pluralsight.models.SalesContract;
import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class SalesContractDao {
    private BasicDataSource dataSource;

    public SalesContractDao(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(SalesContract contract) {
        String query = """
            INSERT INTO SalesContracts (
                VIN, APR, SaleDate, TermMonths, SalesTaxAmount, RecordingFee, ProcessingFee,
                VehiclePrice, Finance
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);""";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, contract.getVehicleSold().split("\\|")[0]);
            preparedStatement.setDouble(2, contract.isFinance() ? contract.getApr() * 100 : 0);
            preparedStatement.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            preparedStatement.setObject(4, contract.isFinance() ? contract.getTermMonths() : null);
            preparedStatement.setDouble(5, contract.getSalesTaxAmount());
            preparedStatement.setDouble(6, contract.getRecordingFee());
            preparedStatement.setDouble(7, contract.getProcessingFee());
            preparedStatement.setDouble(8, contract.getVehiclePrice());
            preparedStatement.setBoolean(9, contract.isFinance());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving sales contract: " + e.getMessage(), e);
        }
    }
}