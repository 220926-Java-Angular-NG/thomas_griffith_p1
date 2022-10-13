package com.revature.models;

public class Ticket {

    private double amount;
    private String description;
    private String status;

    private String type;

    public Ticket() {
    }


    public Ticket(double amount, String description, String status) {
        this.amount = amount;
        this.description = description;
        this.status = status;
    }


//    optional
    public Ticket(double amount, String description, String status, String type) {
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.type = type;
    }
}
