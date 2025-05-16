package com.pluralsight;

public class LeaseContract extends Contract {

    // Declared class variables
    private double expectedEndingValue;
    private double leaseFee;
    private double monthlyPayment;

    // Constructor
    public LeaseContract(String date, String customerName, String vehicleSold) {
        super(date, customerName, vehicleSold);
        this.expectedEndingValue = 0;
        this.leaseFee = 1.07;
        this.monthlyPayment = 0;
    }

    // Abstract method overridden from the super class
    @Override
    public double getTotalPrice() {
        return 0;
    }

    // Abstract method overridden from the super class
    @Override
    public double getMonthlyPayment() {
        return 0;
    }

    // Standard getters and setters
    public double getExpectedEndingValue() { return expectedEndingValue; }
    public void setExpectedEndingValue(double expectedEndingValue) { this.expectedEndingValue = expectedEndingValue; }
    public double getLeaseFee() { return leaseFee; }
    public void setLeaseFee(double leaseFee) { this.leaseFee = leaseFee; }
    public void setMonthlyPayment(double monthlyPayment) { this.monthlyPayment = monthlyPayment; }

    //

}
