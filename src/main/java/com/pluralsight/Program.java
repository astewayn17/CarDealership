package com.pluralsight;

import com.pluralsight.dao.DealershipDao;
import com.pluralsight.dao.LeaseContractDao;
import com.pluralsight.dao.SalesContractDao;
import com.pluralsight.dao.VehicleDao;
import com.pluralsight.models.Dealership;
import org.apache.commons.dbcp2.BasicDataSource;

public class Program {

    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Application needs two arguments to run: java com.pluralsight.App <username> <password>");
            System.exit(1);
        }
        String username = "root";
        String password = "yearup";

        // Create the datasource
        BasicDataSource dataSource = new BasicDataSource();
        // Configure the datasource
        dataSource.setUrl("jdbc:mysql://localhost:3306/cardealershipdb");
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        // Create DAO (data access object) objects to interact with the database
        DealershipDao dataManagerDealership = new DealershipDao(dataSource);
        VehicleDao dataManagerVehicle = new VehicleDao(dataSource);
        SalesContractDao dataManagerSalesContract = new SalesContractDao(dataSource);
        LeaseContractDao dataManagerLeaseContract = new LeaseContractDao(dataSource);

        // Instantiate the ui object and call display() to begin user interaction
        UserInterface ui = new UserInterface();
        ui.display();
    }
}