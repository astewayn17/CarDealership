package com.pluralsight;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class UserInterface {

    // Declaring variables for storing dealership info and vehicle inventory, saving contracts to the CSV and scanner
    private Dealership dealership;
    private ContractFileManager contractFileManager;
    private Scanner input;

    // Constructor that initializes the Scanner for user input and ContractFileManager for handling contracts
    public UserInterface() {
        this.input = new Scanner(System.in);
        this.contractFileManager = new ContractFileManager();
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

    // Loads dealership data from CSV and puts it into dealership variable then it shows the first line
    private void init() {
        DealershipFileManager fileManager = new DealershipFileManager();
        this.dealership = fileManager.getDealership();
        if (dealership != null) {
            System.out.println("\n        WELCOME TO " + dealership.getName().toUpperCase());
            System.out.println("============================================");
        } else {
            System.out.println("\nError loading dealership data.");
        }
    }

    // Shows the menu
    private void displayMenu() {
        System.out.println("(1) ----- Find vehicles within a price range");
        System.out.println("(2) ----- Find vehicles by make / model");
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

    // Helper method used by the 'process...' methods to iterate through the list of vehicles and display them
    private void displayVehicles(List<Vehicle> vehicles) {
        if (vehicles == null || vehicles.isEmpty()) {
            System.out.println("\nNo vehicles found!\n");
            return;
        }
        System.out.println();
        System.out.println("VIN | Year | Make | Model | Type | Color | Odometer | Price\n");
        // This actually calls the toString behind the scenes implicitly from the vehicle class
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle);
        }
        System.out.println();
    }
    // User inputs the min and max parameters that are then put into the getVehiclesByPrice method in
    // the dealership class that then goes into the displayVehicles method to show them to the user
    private void processGetByPriceRequest() {
        System.out.print("Enter the minimum price: ");
        double min = input.nextDouble();
        System.out.print("Enter the maximum price: ");
        double max = input.nextDouble();
        input.nextLine();
        displayVehicles(dealership.getVehiclesByPrice(min, max));
    }
    // Similar to line 88, 89
    private void processGetByMakeModelRequest() {
        System.out.print("Enter the vehicle make: ");
        String make = input.nextLine().trim();
        System.out.print("Enter the vehicle model: ");
        String model = input.nextLine().trim();
        displayVehicles(dealership.getVehiclesByMakeModel(make, model));
    }
    // Similar to line 88, 89
    private void processGetByYearRequest() {
        System.out.print("Enter the minimum year: ");
        int min = input.nextInt();
        System.out.print("Enter the maximum year: ");
        int max = input.nextInt();
        input.nextLine();
        displayVehicles(dealership.getVehiclesByYear(min, max));
    }
    // Similar to line 88, 89
    private void processGetByMileageRequest() {
        System.out.print("Enter the minimum mileage: ");
        int min = input.nextInt();
        System.out.print("Enter the maximum mileage: ");
        int max = input.nextInt();
        input.nextLine();
        displayVehicles(dealership.getVehiclesByMileage(min, max));
    }
    // Similar to line 88, 89
    private void processGetByColorRequest() {
        System.out.print("Enter the vehicle color: ");
        String color = input.nextLine().trim();
        displayVehicles(dealership.getVehiclesByColor(color));
    }
    // Similar to line 88, 89
    private void processGetByTypeRequest() {
        System.out.print("Enter the vehicle type (car, truck, SUV, van): ");
        String type = input.nextLine().trim();
        displayVehicles(dealership.getVehiclesByType(type));
    }
    // Just acquires all vehicles and uses displayVehicles to show them
    private void processAllVehiclesRequest() {
        List<Vehicle> allVehicles = dealership.getAllVehicles();
        displayVehicles(allVehicles);
    }
    // User inputs info for the vehicle they want to add. Those are then used to instantiate
    // a new vehicle object which is then added to the array list and then saved to the CSV.
    private void processAddVehicleRequest() {
        System.out.print("Enter the VIN: ");
        int vin = input.nextInt();
        System.out.print("Enter the year: ");
        int year = input.nextInt();
        input.nextLine();
        System.out.print("Enter the make: ");
        String make = input.nextLine().trim();
        System.out.print("Enter the model: ");
        String model = input.nextLine().trim();
        System.out.print("Enter the type (car/truck/SUV/van): ");
        String type = input.nextLine().trim();
        System.out.print("Enter the color: ");
        String color = input.nextLine().trim();
        System.out.print("Enter the odometer reading: ");
        int odometer = input.nextInt();
        System.out.print("Enter the price: ");
        double price = input.nextDouble();
        input.nextLine();
        Vehicle vehicle = new Vehicle(vin, year, make, model, type, color, odometer, price);
        dealership.addVehicle(vehicle);
        // Save updated dealership
        DealershipFileManager fileManager = new DealershipFileManager();
        fileManager.saveDealership(dealership);
        System.out.println("\nVehicle added and inventory updated successfully!\n");
    }
    // Handles vehicle removal by prompting the user for a VIN, locating the corresponding Vehicle object,
    // removing it from the dealership's inventory list, and saving the updated list back to the file
    private void processRemoveVehicleRequest() {
        System.out.print("Enter the VIN of the vehicle you'd like to remove: ");
        int vinChoice = input.nextInt();
        input.nextLine();
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
            System.out.println("\nVehicle removed and inventory updated successfully!\n");
        } else {
            System.out.println("\nNo corresponding vehicle found with this VIN.\n");

        }
    }
    // Method for handling sales and leasing
    // Gets vehicle inventory, collects customer info, creates appropriate contract
    private void processSellLeaseVehicleRequest() {
        // Makes an array list that stores the vehicles that are available from the dealership
        List<Vehicle> availableVehicles = dealership.getAllVehicles();
        // If there are none available, the sell/lease option will take the user back
        if (availableVehicles.isEmpty()) {
            System.out.println("No vehicles are available.");
            return;
        }
        // Header and showing available vehicles to choose for sale/lease and requests input for the VIN
        System.out.println("\n      === Available Vehicles for Sale or Lease ===");
        displayVehicles(availableVehicles);
        System.out.print("Please enter the VIN of the vehicle you would like to sell or lease out: ");
        int vinChoice = input.nextInt();
        input.nextLine();
        // Loops though the available vehicles for that specific VIN to match it as the correspondingVehicle
        Vehicle correspondingVehicle = null;
        for (Vehicle v : availableVehicles) {
            if (v.getVin() == vinChoice) {
                correspondingVehicle = v;
                break;
            }
        }
        // If after it loops and is still not found, it will display this error message ad go back to the menu
        if (correspondingVehicle == null) {
            System.out.println("\nNo vehicle available with that VIN. Please try again.\n");
            return;
        }
        // Requests user input about the customer
        System.out.print("Enter the customer's name: ");
        String customerName = input.nextLine().trim();
        System.out.print("Enter the customer's email address: ");
        String customerEmail = input.nextLine().trim();
        System.out.print("Will this be a (S) Sale or a (L) Lease? Enter (S/L): ");
        String contractType = input.nextLine().trim();
        // Using the LocalDate method to obtain the real time now (for the year later on)
        LocalDate dateNow = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDateNow = dateNow.format(formatter);
        // Stores the basic vehicle info of the selected one into vehicleInfo
        String vehicleInfo = correspondingVehicle.getVin() + "|" +
                correspondingVehicle.getYear() + "|" +
                correspondingVehicle.getMake() + "|" +
                correspondingVehicle.getModel() + "|" +
                correspondingVehicle.getVehicleType() + "|" +
                correspondingVehicle.getColor() + "|" +
                correspondingVehicle.getOdometer();
        // Initialize contract to be used outside the conditionals which are going based off sale or lease
        Contract contract;
        if (contractType.equalsIgnoreCase("s")) {
            System.out.print("Would the customer like to finance this vehicle? Enter (Y/N): ");
            boolean willFinance = input.nextLine().trim().equalsIgnoreCase("y");
            // Instantiating a new contract object with the information collected about the customer and vehicle
            contract = new SalesContract(formattedDateNow, customerName, customerEmail, vehicleInfo,
                    correspondingVehicle.getPrice(), willFinance);
            // This will display the info about the contract after down casting to the sales contract class
            SalesContract sale = (SalesContract) contract;
            System.out.print("\n *** Sales Contract Details ***");
            System.out.printf("\nCustomer Name: ---- %s", contract.getCustomerName());
            System.out.printf("\nCustomer Email: --- %s", contract.getCustomerEmail());
            System.out.printf("\nVehicle: ---------- %d %s %s", correspondingVehicle.getYear(),
                    correspondingVehicle.getMake(),
                    correspondingVehicle.getModel());
            System.out.printf("\nPrice: ------------ $%.2f", sale.getVehiclePrice());
            System.out.printf("\nSales Tax: -------- $%.2f", sale.getSalesTaxAmount());
            System.out.printf("\nRecording Fee: ---- $%.2f", sale.getRecordingFee());
            System.out.printf("\nProcessing Fee: --- $%.2f", sale.getProcessingFee());
            System.out.printf("\nTotal Price: ------ $%.2f", sale.getTotalPrice());
            System.out.printf("\nFinanced: --------- %s", sale.isFinance() ? "Yes" : "No");
            if (sale.isFinance()) {
                System.out.printf("\nMonthly Payment: -- $%.2f", sale.getMonthlyPayment());
                System.out.printf("\nTerm Length: ------ %d Months", sale.getTermMonths());
                System.out.printf("\nAPR: -------------- %.2f%%", sale.getApr() * 100);
            }
        }
        // This will then begin the contract conditional for leases
        else if (contractType.equals("l")) {
            if ((dateNow.getYear() - correspondingVehicle.getYear()) > 3) {
                System.out.println("\nThis vehicle is ineligible for a lease due to being over 3 years old.\n");
                return;
            }
            // Instantiating a new contract object with the information collected about the customer and vehicle
            contract = new LeaseContract(formattedDateNow, customerName, customerEmail, vehicleInfo,
                    correspondingVehicle.getPrice());
            // This will display the info about the contract after down casting to the lease contract class
            LeaseContract lease = (LeaseContract) contract;
            System.out.print("\n *** Lease Contract Details ***");
            System.out.printf("\nCustomer Name: ------- %s", contract.getCustomerName());
            System.out.printf("\nCustomer Email: ------ %s", contract.getCustomerEmail());
            System.out.printf("\nVehicle: ------------- %d %s %s", correspondingVehicle.getYear(),
                    correspondingVehicle.getMake(),
                    correspondingVehicle.getModel());
            System.out.printf("\nPrice: --------------- $%.2f", lease.getVehiclePrice());
            System.out.printf("\nExpected End Value: -- $%.2f", lease.getExpectedEndingValue());
            System.out.printf("\nLease Fee: ----------- $%.2f", lease.getLeaseFee());
            System.out.printf("\nTotal Lease Price: --- $%.2f", lease.getTotalPrice());
            System.out.printf("\nMonthly Payment: ----- $%.2f", lease.getMonthlyPayment());
            System.out.printf("\nTerm Length: --------- %d Months", lease.getTermMonths());
            System.out.printf("\nAPR: ----------------- %.2f%%", lease.getApr() * 100);
        } else {
            System.out.println("\nInvalid option. Please try again.\n");
            return;
        }
        // Double-checking after the contract info is displayed whether the user wants to confirm or cancel
        System.out.print("\n\nWould you like to confirm and save this contract? Enter (Y/N): ");
        String contractConfirmation = input.nextLine().trim();
        if (contractConfirmation.equalsIgnoreCase("y")) {
            // Save the contract to the CSV
            contractFileManager.saveContract(contract);
            // Using the removeVehicle method to remove the corresponding vehicle from the dealership inventory
            dealership.removeVehicle(correspondingVehicle);
            // Create a new instance of DealershipFileManager to handle file operations. This class is responsible for
            // writing dealership data to the inventory.csv. Then it will use the saveDealership method to use the
            // instance to update and save the new inventory.csv
            DealershipFileManager fileManager = new DealershipFileManager();
            fileManager.saveDealership(dealership);
            System.out.println("\nContract has been successfully confirmed and the vehicle has been removed from the inventory!\n");
        } else {
            System.out.println("\nContract has been canceled. Back to menu.\n");
        }
    }
}