package com.pluralsight;

import com.pluralsight.dao.LeaseContractDao;
import com.pluralsight.dao.SalesContractDao;
import com.pluralsight.dao.VehicleDao;
import com.pluralsight.models.Dealership;
import org.apache.commons.dbcp2.BasicDataSource;

public class Program {
    public static void main(String[] args) {

        // Set DB credentials
        String username = "root";
        String password = "yearup";

        // Configure database connection
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/cardealershipdb");
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        // Create DAO objects
        VehicleDao vehicleDao = new VehicleDao(dataSource);
        SalesContractDao salesContractDao = new SalesContractDao(dataSource);
        LeaseContractDao leaseContractDao = new LeaseContractDao(dataSource);

        // Create dealership instance (could be fetched from DB in future if needed)
        Dealership dealership = new Dealership("Asteway Auto Center", "123 Main St", "555-123-4567");

        // Start UI
        UserInterface ui = new UserInterface(dealership, vehicleDao, salesContractDao, leaseContractDao);
        ui.display();
    }
}