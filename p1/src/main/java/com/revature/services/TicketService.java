package com.revature.services;

import com.revature.models.Ticket;
import com.revature.models.User;
import com.revature.repos.TicketRepo;
import com.revature.repos.UserRepo;

import java.util.List;

public class TicketService {

    private TicketRepo ticketRepo;

    public TicketService() {
        ticketRepo = new TicketRepo();
    }

    public TicketService(TicketRepo ticketRepo) {
        this.ticketRepo = ticketRepo;
    }



    //    create
    public int createTicket(Ticket ticket){

        return ticketRepo.create(ticket);
    }

    public List<Ticket> getTicketByType(int creator_id, Ticket.TYPE type){

        return ticketRepo.getTicketByType(creator_id, type);
    }

    public List<Ticket> getTicketByCreator_id(int creator_id){

        return ticketRepo.getTicketByCreator_id(creator_id);
    }

    public List<Ticket> getAll(){

        return ticketRepo.getAll();
    }

    public boolean updatetic(int id, Ticket.STATUS status){

        return ticketRepo.updatetic(id, status);
    }



}
