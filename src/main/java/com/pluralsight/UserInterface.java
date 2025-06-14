package com.pluralsight;

import com.pluralsight.dao.LeaseContractDao;
import com.pluralsight.dao.SalesContractDao;
import com.pluralsight.dao.VehicleDao;
import com.pluralsight.models.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private Dealership dealership;
    private Scanner input;
    private VehicleDao vehicleDao;
    private SalesContractDao salesContractDao;
    private LeaseContractDao leaseContractDao;

    public UserInterface(Dealership dealership, VehicleDao vehicleDao,
                         SalesContractDao salesContractDao, LeaseContractDao leaseContractDao) {
        this.input = new Scanner(System.in);
        this.dealership = dealership;
        this.vehicleDao = vehicleDao;
        this.salesContractDao = salesContractDao;
        this.leaseContractDao = leaseContractDao;
    }

    // Method that shows the main UI and processes the preliminary choice input by the user
    public void display() {
        // Calls init method
        init();
        // Loops based on the options selected from the menu
        boolean accessing = true;
        while (accessing) {
            displayMenu();
            int command = input.nextInt();
            // Consume next line since the input previous is an int or double
            input.nextLine();
            switch (command) {
                case 1 -> processGetByPriceRequest();
                case 2 -> processGetByMakeModelRequest();
                case 3 -> processGetByYearRequest();
                case 4 -> processGetByColorRequest();
                case 5 -> processGetByMileageRequest();
                case 6 -> processGetByTypeRequest();
                case 7 -> processAllVehiclesRequest();
                case 8 -> processAddVehicleRequest();
                case 9 -> processRemoveVehicleRequest();
                case 10 -> processSellLeaseVehicleRequest();
                case 99-> {
                    System.out.println("\nExiting the application. Goodbye!");
                    accessing = false;
                }
                default -> System.out.println("\nInvalid option! Please try again.");
            }
        }
    }

    private void init() {
        if (dealership != null) {
            System.out.println("\n        WELCOME TO " + dealership.getName().toUpperCase());
            System.out.println("============================================");
        } else {
            System.out.println("\nError loading dealership data.");
        }
    }

    private void displayMenu() {
        System.out.println("(1) ----- Find vehicles within a price range");
        System.out.println("(2) ----- Find vehicles by make/model");
        System.out.println("(3) ----- Find vehicles by year range");
        System.out.println("(4) ----- Find vehicles by color");
        System.out.println("(5) ----- Find vehicles by mileage range");
        System.out.println("(6) ----- Find vehicles by type (car/truck/SUV/van)");
        System.out.println("(7) ----- List ALL vehicles");
        System.out.println("(8) ----- Add a vehicle");
        System.out.println("(9) ----- Remove a vehicle");
        System.out.println("(10) ---- Sell or Lease a vehicle");
        System.out.println("(99) ---- Quit");
        System.out.print("\nPlease select a number from the choices above: ");
    }

    private void processSellLeaseVehicleRequest() {
        List<Vehicle> availableVehicles = vehicleDao.getVehiclesByPrice(0, Double.MAX_VALUE); // All unsold vehicles
        if (availableVehicles.isEmpty()) {
            System.out.println("No vehicles are available.");
            return;
        }

        System.out.println("\n      === Available Vehicles for Sale or Lease ===");
        displayVehicles(availableVehicles);
        System.out.print("Please enter the VIN of the vehicle you would like to sell or lease out: ");
        String vinChoice = input.nextLine();

        Vehicle correspondingVehicle = null;
        for (Vehicle v : availableVehicles) {
            if (v.getVin().equals(vinChoice)) {
                correspondingVehicle = v;
                break;
            }
        }

        if (correspondingVehicle == null) {
            System.out.println("\nNo vehicle available with that VIN. Please try again.\n");
            return;
        }

        System.out.print("Enter the customer's name: ");
        String customerName = input.nextLine().trim();
        System.out.print("Enter the customer's email address: ");
        String customerEmail = input.nextLine().trim();
        System.out.print("Will this be a (S) Sale or a (L) Lease? Enter (S/L): ");
        String contractType = input.nextLine().trim();

        LocalDate dateNow = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDateNow = dateNow.format(formatter);

        String vehicleInfo = correspondingVehicle.getVin() + "|" +
                correspondingVehicle.getYear() + "|" +
                correspondingVehicle.getMake() + "|" +
                correspondingVehicle.getModel() + "|" +
                correspondingVehicle.getVehicleType() + "|" +
                correspondingVehicle.getColor() + "|" +
                correspondingVehicle.getOdometer();

        Contract contract;
        if (contractType.equalsIgnoreCase("s")) {
            System.out.print("Would the customer like to finance this vehicle? Enter (Y/N): ");
            boolean willFinance = input.nextLine().trim().equalsIgnoreCase("y");
            contract = new SalesContract(formattedDateNow, customerName, customerEmail, vehicleInfo,
                    correspondingVehicle.getPrice(), willFinance);
            salesContractDao.save((SalesContract) contract);
        } else if (contractType.equalsIgnoreCase("l")) {
            if ((dateNow.getYear() - correspondingVehicle.getYear()) > 3) {
                System.out.println("\nThis vehicle is ineligible for a lease due to being over 3 years old.\n");
                return;
            }
            contract = new LeaseContract(formattedDateNow, customerName, customerEmail, vehicleInfo,
                    correspondingVehicle.getPrice());
            leaseContractDao.save((LeaseContract) contract);
        } else {
            System.out.println("\nInvalid option. Please try again.\n");
            return;
        }

        vehicleDao.removeVehicleByVin(correspondingVehicle.getVin());
        System.out.println("\nContract has been successfully confirmed and the vehicle has been removed from the inventory!\n");
    }

    private void displayVehicles(List<Vehicle> vehicles) {
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle);
        }
    }
}