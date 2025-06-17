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
            System.out.println("\nWELCOME TO " + dealership.getName().toUpperCase());
            System.out.println("============================================");
        } else {
            System.out.println("\nError loading dealership data.");
        }
    }

    private void displayMenu() {
        System.out.println("\n(1) ----- Find vehicles within a price range");
        System.out.println("(2) ----- Find vehicles by make/model");
        System.out.println("(3) ----- Find vehicles by year range");
        System.out.println("(4) ----- Find vehicles by color");
        System.out.println("(5) ----- Find vehicles by mileage range");
        System.out.println("(6) ----- Find vehicles by type");
        System.out.println("(7) ----- List ALL vehicles");
        System.out.println("(8) ----- Add a vehicle");
        System.out.println("(9) ----- Remove a vehicle");
        System.out.println("(10) ---- Sell or Lease a vehicle");
        System.out.println("(99) ---- Quit");
        System.out.print("\nPlease select a number from the choices above: ");
    }

    private void processGetByPriceRequest() {
        System.out.print("\nEnter minimum price: ");
        double minPrice = input.nextDouble();
        System.out.print("Enter maximum price: ");
        double maxPrice = input.nextDouble();
        input.nextLine();

        List<Vehicle> vehicles = vehicleDao.getVehiclesByPrice(minPrice, maxPrice);
        if (vehicles.isEmpty()) {
            System.out.println("\nNo vehicles found in that price range.");
        } else {
            System.out.println("\n      === Vehicles in Price Range $" + minPrice + " - $" + maxPrice + " ===");
            displayVehicles(vehicles);
        }
    }

    private void processGetByMakeModelRequest() {
        System.out.print("\nEnter vehicle make: ");
        String make = input.nextLine().trim();
        System.out.print("Enter vehicle model: ");
        String model = input.nextLine().trim();

        List<Vehicle> vehicles = vehicleDao.getVehiclesByMakeModel(make, model);
        if (vehicles.isEmpty()) {
            System.out.println("\nNo vehicles found for " + make + " " + model);
        } else {
            System.out.println("\n      === " + make + " " + model + " Vehicles ===");
            displayVehicles(vehicles);
        }
    }

    private void processGetByYearRequest() {
        System.out.print("\nEnter minimum year: ");
        int minYear = input.nextInt();
        System.out.print("Enter maximum year: ");
        int maxYear = input.nextInt();
        input.nextLine();

        List<Vehicle> vehicles = vehicleDao.getVehiclesByYear(minYear, maxYear);
        if (vehicles.isEmpty()) {
            System.out.println("\nNo vehicles found in that year range.");
        } else {
            System.out.println("\n      === Vehicles from " + minYear + " to " + maxYear + " ===");
            displayVehicles(vehicles);
        }
    }

    private void processGetByColorRequest() {
        System.out.print("\nEnter vehicle color: ");
        String color = input.nextLine().trim();

        List<Vehicle> vehicles = vehicleDao.getVehiclesByColor(color);
        if (vehicles.isEmpty()) {
            System.out.println("\nNo vehicles found in " + color + " color.");
        } else {
            System.out.println("\n      === " + color + " Vehicles ===");
            displayVehicles(vehicles);
        }
    }

    private void processGetByMileageRequest() {
        System.out.print("\nEnter minimum mileage: ");
        int minMileage = input.nextInt();
        System.out.print("Enter maximum mileage: ");
        int maxMileage = input.nextInt();
        input.nextLine();

        List<Vehicle> vehicles = vehicleDao.getVehiclesByMileage(minMileage, maxMileage);
        if (vehicles.isEmpty()) {
            System.out.println("\nNo vehicles found in that mileage range.");
        } else {
            System.out.println("\n      === Vehicles with " + minMileage + " - " + maxMileage + " miles ===");
            displayVehicles(vehicles);
        }
    }

    private void processGetByTypeRequest() {
        System.out.print("\nEnter vehicle type (Sedan/Truck/SUV/Coupe/Van): ");
        String type = input.nextLine().trim();

        List<Vehicle> vehicles = vehicleDao.getVehiclesByType(type);
        if (vehicles.isEmpty()) {
            System.out.println("\nNo vehicles found of type: " + type);
        } else {
            System.out.println("\n      === " + type + " Vehicles ===");
            displayVehicles(vehicles);
        }
    }

    private void processAllVehiclesRequest() {
        List<Vehicle> vehicles = vehicleDao.getAllVehicles();
        if (vehicles.isEmpty()) {
            System.out.println("\nNo vehicles currently in inventory.");
        } else {
            System.out.println("\n      === All Available Vehicles ===");
            displayVehicles(vehicles);
        }
    }

    private void processAddVehicleRequest() {
        System.out.println("\n      === Add New Vehicle ===");
        System.out.print("Enter VIN: ");
        String vin = input.nextLine().trim();
        System.out.print("Enter year: ");
        int year = input.nextInt();
        input.nextLine(); // consume newline
        System.out.print("Enter make: ");
        String make = input.nextLine().trim();
        System.out.print("Enter model: ");
        String model = input.nextLine().trim();
        System.out.print("Enter vehicle type (Sedan/Truck/SUV/Coupe/Van): ");
        String vehicleType = input.nextLine().trim();
        System.out.print("Enter color: ");
        String color = input.nextLine().trim();
        System.out.print("Enter odometer reading: ");
        int odometer = input.nextInt();
        System.out.print("Enter price: ");
        double price = input.nextDouble();
        input.nextLine();

        Vehicle newVehicle = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
        vehicleDao.addVehicle(newVehicle);
        System.out.println("\nVehicle added successfully!");
    }

    private void processRemoveVehicleRequest() {
        System.out.print("\nEnter the VIN of the vehicle to remove: ");
        String vin = input.nextLine().trim();

        Vehicle vehicle = vehicleDao.getVehicleByVin(vin);
        if (vehicle == null) {
            System.out.println("\nNo vehicle found with VIN: " + vin);
            return;
        }

        System.out.println("\nAre you sure you want to remove this vehicle?");
        System.out.println(vehicle);
        System.out.print("Enter (Y/N): ");
        String confirmation = input.nextLine().trim();

        if (confirmation.equalsIgnoreCase("Y")) {
            vehicleDao.deleteVehicleByVin(vin);
            System.out.println("\nVehicle removed successfully!");
        } else {
            System.out.println("\nRemoval cancelled.");
        }
    }

    private void processSellLeaseVehicleRequest() {
        List<Vehicle> availableVehicles = vehicleDao.getAllVehicles();
        if (availableVehicles.isEmpty()) {
            System.out.println("\nNo vehicles are available.");
            return;
        }

        System.out.println("\n      === Available Vehicles for Sale or Lease ===");
        displayVehicles(availableVehicles);
        System.out.print("\nPlease enter the VIN of the vehicle you would like to sell or lease out: ");
        String vinChoice = input.nextLine();

        Vehicle correspondingVehicle = null;
        for (Vehicle v : availableVehicles) {
            if (v.getVin().equals(vinChoice)) {
                correspondingVehicle = v;
                break;
            }
        }

        if (correspondingVehicle == null) {
            System.out.println("\nNo vehicle available with that VIN. Please try again.");
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
                System.out.println("\nThis vehicle is ineligible for a lease due to being over 3 years old.");
                return;
            }
            contract = new LeaseContract(formattedDateNow, customerName, customerEmail, vehicleInfo,
                    correspondingVehicle.getPrice());
            leaseContractDao.save((LeaseContract) contract);
        } else {
            System.out.println("\nInvalid option. Please try again.");
            return;
        }

        vehicleDao.removeVehicleByVin(correspondingVehicle.getVin());
        System.out.println("\nContract has been successfully confirmed and the vehicle has been marked as sold!");
    }

    private void displayVehicles(List<Vehicle> vehicles) {
        System.out.println("\n VIN                | Year | Make          | Model                | Type     | Color           | Mileage  | Price");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------");
        for (Vehicle vehicle : vehicles) {
            System.out.printf(" %-18s | %-4d | %-13s | %-20s | %-8s | %-15s | %-8d | $%,.2f%n",
                    vehicle.getVin(),
                    vehicle.getYear(),
                    vehicle.getMake(),
                    vehicle.getModel(),
                    vehicle.getVehicleType(),
                    vehicle.getColor(),
                    vehicle.getOdometer(),
                    vehicle.getPrice()
            );
        }
        System.out.println();
    }
}