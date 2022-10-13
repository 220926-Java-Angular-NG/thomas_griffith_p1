package com.revature.models;

import java.util.List;

public class Cache {


    public enum Level {NONE, EMPLOYEE, MANAGER}

    public Level level = Level.NONE;

    public String curUsername = null;
    public int curUserID = -1;

    public List<Ticket> ticketList;

}
