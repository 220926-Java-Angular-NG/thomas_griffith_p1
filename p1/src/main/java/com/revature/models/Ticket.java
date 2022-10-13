package com.revature.models;

public class Ticket {

    private int id;
    private double amount;
    private String description;

    private int creator_id;
//    private String status;

    public enum STATUS {pending, approved, denied}

    public Ticket.STATUS status = STATUS.pending;


    public enum TYPE {TRAVEL, LODGING, FOOD, OTHER}

    private Ticket.TYPE type = TYPE.OTHER;

    public Ticket() {
    }

    public Ticket(double amount, String description, int creator_id) {
        this.amount = amount;
        this.description = description;
        this.creator_id = creator_id;
    }

    public Ticket(double amount, String description, STATUS status, TYPE type, int creator_id) {
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.type = type;
        this.creator_id = creator_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public int getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(int creator_id) {
        this.creator_id = creator_id;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", creator_id=" + creator_id +
                ", status=" + status +
                ", type=" + type +
                '}';
    }
}
