package com.revature.utils;

import com.revature.models.Ticket;

import java.util.List;

public interface CRUDDaoInterface <T> {
    //DOA - Data Access Object
    // a pattern that provides an abstract interface to
    // some type of database or other persistence mechanism

    // we are returning an int because we are returning the primary key of this newly added row
    int create(T t);

//    change pos
    boolean changePos(T t);

    //arrayList of whatever type I query
    List<T> getAll();

    //arrayList of whatever type I query
    List<T> getTicketByType(int creator_id, Ticket.TYPE type);

    List<T> getTicketByCreator_id(int creator_id);

    T getById(int id);

    T getLogin(String username, String password);

//    T checkUsername(String username);

    T update(T t);
    boolean updatetic(int id, Ticket.STATUS status);

    boolean delete(T t);
}
