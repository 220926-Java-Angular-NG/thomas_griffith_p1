package com.revature.services;

import com.revature.models.Ticket;
import com.revature.models.User;
import com.revature.repos.TicketRepo;
import com.revature.repos.UserRepo;

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




}
