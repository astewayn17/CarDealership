package com.pluralsight;

public abstract class Contract {

    // Declared class variables
    protected String date;
    protected String customerName;
    protected String customerEmail;
    protected String vehicleSold;
    protected double totalPrice;
    protected double monthlyPayment;

    // Constructor
    public Contract(String date, String customerName, String customerEmail, String vehicleSold) {
        this.date = date;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.vehicleSold = vehicleSold;
        this.totalPrice = 0;
        this.monthlyPayment = 0;
    }

    // Abstract methods
    public abstract double getTotalPrice();
    public abstract double getMonthlyPayment();

    // Standard getters and setters
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
    public String getVehicleSold() { return vehicleSold; }
    public void setVehicleSold(String vehicleSold) { this.vehicleSold = vehicleSold; }
}