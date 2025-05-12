package com.pluralsight;

import java.util.List;
import java.util.Scanner;

public class UserInterface {

    //
    private Dealership dealership;
    private Scanner scanner;

    //
    public UserInterface() {
        this.scanner = new Scanner(System.in);
    }

    //
    public void display() {
        //
        init();
        //
        boolean accessing = true;
        while (accessing) {
            displayMenu();
            int command = scanner.nextInt();
            scanner.nextLine(); // consume newline
            //
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
                case 99-> {
                    System.out.println("Exiting the application. Goodbye!");
                    accessing = false;
                }
                default -> System.out.println("Invalid option! Please try again.");
            }
        }
    }

    //
    private void init() {
        DealershipFileManager fileManager = new DealershipFileManager();
        this.dealership = fileManager.getDealership();
        //
        if (dealership != null) {
            System.out.println("Welcome to " + dealership.getName());
        } else {
            System.out.println("Error loading dealership data.");
        }
    }

    //
    private void displayMenu() {
        System.out.println("\nASTEWAY'S AUTO DEALERSHIP MENU");
        System.out.println("========================================");
        System.out.println("(1) - Find vehicles within a price range");
        System.out.println("(2) - Find vehicles by make / model");
        System.out.println("(3) - Find vehicles by year range");
        System.out.println("(4) - Find vehicles by color");
        System.out.println("(5) - Find vehicles by mileage range");
        System.out.println("(6) - Find vehicles by type");
        System.out.println("(7) - List all vehicles");
        System.out.println("(8) - Add a vehicle");
        System.out.println("(9) - Remove a vehicle");
        System.out.println("(99) - Quit");
        System.out.print("\nPlease select a number from the choices above: ");
    }

    //
    private void displayVehicles(List<Vehicle> vehicles) {
        if (vehicles == null || vehicles.isEmpty()) {
            System.out.println("No vehicles found!");
            return;
        }
        //
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle);
        }
    }
    //
    private void processGetByPriceRequest() {
        System.out.print("Enter minimum price: ");
        double min = scanner.nextDouble();
        System.out.print("Enter maximum price: ");
        double max = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        displayVehicles(dealership.getVehiclesByPrice(min, max));
    }
    //
    private void processGetByMakeModelRequest() {
        System.out.print("Enter vehicle make: ");
        String make = scanner.nextLine();
        System.out.print("Enter vehicle model: ");
        String model = scanner.nextLine();
        displayVehicles(dealership.getVehiclesByMakeModel(make, model));
    }
    //
    private void processGetByYearRequest() {
        System.out.print("Enter minimum year: ");
        int min = scanner.nextInt();
        System.out.print("Enter maximum year: ");
        int max = scanner.nextInt();
        scanner.nextLine(); // consume newline
        displayVehicles(dealership.getVehiclesByYear(min, max));
    }
    //
    private void processGetByMileageRequest() {
        System.out.print("Enter minimum mileage: ");
        int min = scanner.nextInt();
        System.out.print("Enter maximum mileage: ");
        int max = scanner.nextInt();
        scanner.nextLine(); // consume newline
        displayVehicles(dealership.getVehiclesByMileage(min, max));
    }
    //
    private void processGetByColorRequest() {
        System.out.print("Enter vehicle color: ");
        String color = scanner.nextLine();
        displayVehicles(dealership.getVehiclesByColor(color));
    }
    //
    private void processGetByTypeRequest() {
        System.out.print("Enter vehicle type (car, truck, SUV, van): ");
        String type = scanner.nextLine();
        displayVehicles(dealership.getVehiclesByType(type));
    }
    //
    private void processAllVehiclesRequest() {
        List<Vehicle> allVehicles = dealership.getAllVehicles();
        displayVehicles(allVehicles);
    }
    //
    private void processAddVehicleRequest() {
        System.out.print("Enter the VIN: ");
        int vin = scanner.nextInt();
        System.out.print("Enter the year: ");
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter the make: ");
        String make = scanner.nextLine();
        System.out.print("Enter the model: ");
        String model = scanner.nextLine();
        System.out.print("Enter the type (car/truck/SUV/van): ");
        String type = scanner.nextLine();
        System.out.print("Enter the color: ");
        String color = scanner.nextLine();
        System.out.print("Enter the odometer reading: ");
        int odometer = scanner.nextInt();
        System.out.print("Enter the price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        Vehicle vehicle = new Vehicle(vin, year, make, model, type, color, odometer, price);
        dealership.addVehicle(vehicle);
        // Save updated dealership
        DealershipFileManager fileManager = new DealershipFileManager();
        fileManager.saveDealership(dealership);
        System.out.println("Vehicle added and inventory updated successfully!.");
    }
    //
    private void processRemoveVehicleRequest() {
        //
        System.out.println("Enter the VIN of the vehicle you'd like to remove: ");
        int vinChoice = scanner.nextInt();
        scanner.nextLine();
        //
        Vehicle correspondingVehicle = null;
        for (Vehicle v : dealership.getAllVehicles()) {
            if (v.getVin() == vinChoice) {
                correspondingVehicle = v;
                break;
            }
        }if (correspondingVehicle != null) {
            dealership.removeVehicle(correspondingVehicle);
            DealershipFileManager fileManager = new DealershipFileManager();
            fileManager.saveDealership(dealership);
            System.out.println("Vehicle removed and inventory updated successfully!");
        } else {
            System.out.println("No corresponding vehicle found with this VIN.");
        }
    }
}
