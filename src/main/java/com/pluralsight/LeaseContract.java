package com.pluralsight;

public class LeaseContract extends Contract {

    // Declared class variables
    private double expectedEndingValue;
    private double leaseFee;
    private double vehiclePrice;
    // These are final since they won't change
    private final int termMonths;
    private final double apr;

    // Constructor
    public LeaseContract(String date, String customerName, String customerEmail, String vehicleSold, double vehiclePrice) {
        super(date, customerName, customerEmail, vehicleSold);
        this.expectedEndingValue = vehiclePrice * .5;
        this.leaseFee = vehiclePrice * .07;
        this.vehiclePrice = vehiclePrice;
        this.termMonths = 36;
        this.apr = .04;
    }

    // Abstract method overridden from the super class
    @Override
    public double getTotalPrice() {
        return (this.vehiclePrice - this.expectedEndingValue) + this.leaseFee;
    }

    // Abstract method overridden from the super class
    @Override
    public double getMonthlyPayment() {
        //
        double monthlyRate = apr / 12;
        double totalPrice = getTotalPrice();
        // Using the standard amortization formula for calculating fixed monthly payments
        // P * (r * (1 + r)^n) / ((1 + r)^n - 1)
        // P = Principal (loan amount, which is the total price)
        // r = Monthly interest rate
        // n = Total number of months
        return (totalPrice * (monthlyRate * Math.pow(1 + monthlyRate, termMonths))) /
                (Math.pow(1 + monthlyRate, termMonths) - 1);
    }

    // Getter for getting the vehicle price
    public double getVehiclePrice() { return vehiclePrice; }
    // Setter that will update two other variables based on the vehicle price
    public void setVehiclePrice(double vehiclePrice) {
        this.vehiclePrice = vehiclePrice;
        this.expectedEndingValue = vehiclePrice * .5;
        this.leaseFee = vehiclePrice * .07;
    }
    // Standard getters and setters
    public double getExpectedEndingValue() { return expectedEndingValue; }
    public void setExpectedEndingValue(double expectedEndingValue) { this.expectedEndingValue = expectedEndingValue; }
    public double getLeaseFee() { return leaseFee; }
    public void setLeaseFee(double leaseFee) { this.leaseFee = leaseFee; }
    public void setMonthlyPayment(double monthlyPayment) { this.monthlyPayment = monthlyPayment; }
    // Added getters to use these when displaying the sales contract to the user
    public int getTermMonths() { return termMonths; }
    public double getApr() { return apr; }
}