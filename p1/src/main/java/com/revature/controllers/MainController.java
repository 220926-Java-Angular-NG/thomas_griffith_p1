package com.revature.controllers;

import com.revature.models.Cache;
import com.revature.models.Ticket;
import com.revature.models.User;
import com.revature.services.TicketService;
import com.revature.services.UserService;
import io.javalin.http.Handler;

import java.util.List;


public class MainController {

    Cache caches = new Cache();


    UserService userService = new UserService();

    TicketService ticketService = new TicketService();



    //////////////////////////////////////////////////////////////////
//    change pos user
    public Handler changePos = context -> {
        if(caches.level == Cache.Level.MANAGER){
            User in = context.bodyAsClass(User.class);
            System.out.println(in.getUsername());
            boolean user = userService.changePos(in);
            if (user){
                context.result("User has been updated");
            }else{
                context.result("User has not been updated");
            }

        }

        System.out.println("You do not have the authorization to change user employment state");

    };

    //////////////////////////////////////////////////////////////////
//    delete user only manager
    public Handler checkStatus = context -> {


        context.result("User info:\n" + "Username: " + caches.curUsername + "\n"
                        + "Clearance Level: " + caches.level);


    };



    //////////////////////////////////////////////////////////////////
//    delete user only manager
    public Handler delUser = context -> {
        if(caches.level == Cache.Level.MANAGER){
            User in = context.bodyAsClass(User.class);
            System.out.println(in.getUsername() + "   " + in.getId());
            boolean del = userService.deleteUser(in);
            if (del){
                context.result("User has been deleted");
            }else{
                context.result("User has not been deleted");
            }

        }

        System.out.println("You do not have the authorization to delete users");

    };

    //////////////////////////////////////////////////////////////////
//    login
    public Handler loginGet = context -> {

        context.result("The options are: login (post)\n or logout (delete)\n ");
    };


    //////////////////////////////////////////////////////////////////
//    login user
    public Handler login = context -> {

        User in = context.bodyAsClass(User.class);
        System.out.println(in.getUsername() + "   " + in.getPassword());
        User user = userService.getUserByUsername(in.getUsername(), in.getPassword());

        caches.curUserID = user.getId();
        caches.curUsername = user.getUsername();
        if(caches.curUsername == null){
            context.result("incorrect username or password").status(400);
            return;
        }

        if (user.getIsManager() == 1) {
            caches.level = Cache.Level.MANAGER;
        } else{
            caches.level = Cache.Level.EMPLOYEE;
        }

        context.result(caches.curUsername + " is logged in as an " + caches.level);
        System.out.println(caches.curUsername + " is logged in as an " + caches.level);

    };


    //////////////////////////////////////////////////////////////////
//    login out user
    public Handler loginout = context -> {
        caches.level = Cache.Level.NONE;
        caches.curUserID = -1;
        caches.curUsername = "";

        context.result("You have been logged out");


    };


    //////////////////////////////////////////////////////////////////
//    register home page
    public Handler registerHome = context -> {

        context.result("The options are: register a user (post)\n or register info (get)\n ");
    };

    //////////////////////////////////////////////////////////////////
//    register user
    public Handler register = context -> {
        if (caches.level == Cache.Level.NONE){



        User in = context.bodyAsClass(User.class);

        int test = userService.createUser(in);

        if (test == 0){
            context.result("This is already taken username\n Please try again");
        }
        User user = userService.getUserByUsername(in.getUsername(), in.getPassword());

        caches.curUserID = user.getId();
        caches.curUsername = user.getUsername();

        if (user.getIsManager() == 1) {
            caches.level = Cache.Level.MANAGER;
        } else{
            caches.level = Cache.Level.EMPLOYEE;
        }
        context.result("User is created and you are logged in");
        System.out.println(caches);

        }else{
            context.result("Already logged in\n log out at /login if new user");
        }

    };






    //////////////////////////////////////////////////////////////////
//    Create a ticket
    public Handler createTicket = context -> {
        if (caches.level == Cache.Level.EMPLOYEE) {

            Ticket in = context.bodyAsClass(Ticket.class);
            System.out.println(in.getAmount());
            if (in.getAmount() > 1 && in.getDescription().length() > 1){
                int id = ticketService.createTicket(in);
                if (id == 0) {
                    context.result("does not work");
                } else {
                    context.result("Ticket Created");
                }
            }else {
                context.result("You need to add amount and description");
            }
        }else{
            context.result("You need to be an employee to create ticket");
        }

    };


    //////////////////////////////////////////////////////////////////
//   Employee views his past tickets
    public Handler allPrevTicket = context -> {
        if (caches.level == Cache.Level.EMPLOYEE){

//            User in = context.bodyAsClass(User.class);

            List<Ticket> tickets = ticketService.getTicketByCreator_id(caches.curUserID);

            context.result("Ticket list made");
            String lists = "";
            for(Ticket tic: tickets){
                lists+=tic.toString()+"\n";
            }
            context.result(lists);

        }else{
            context.result("You need to be an employee to create ticket");
        }

    };


    //////////////////////////////////////////////////////////////////
//   Employee views his past tickets by type
    public Handler prevTicketType = context -> {
        if (caches.level == Cache.Level.EMPLOYEE){

            Ticket in = context.bodyAsClass(Ticket.class);
            System.out.println(caches.curUserID + " " + in.getType().toString());
            List<Ticket> tickets = ticketService.getTicketByType(caches.curUserID, in.getType());

            String lists = "";
            for(Ticket tic: tickets){
                lists+=tic.toString()+"\n";
            }
            context.result(lists);


        }else{
            context.result("You need to be an employee to create ticket");
        }

    };


    //////////////////////////////////////////////////////////////////
//   Manager views all tickets processing
    public Handler managerTickets = context -> {
        if (caches.level == Cache.Level.MANAGER){

//            User in = context.bodyAsClass(User.class);

            List<Ticket> tickets = ticketService.getAll();

            context.result("Ticket list made");
            String lists = "";
            for(Ticket tic: tickets){
                lists+=tic.toString()+"\n";
            }
            context.result(lists);
            caches.ticketList = tickets;

        }else{
            context.result("You need to be an Manager to see all processing tickets");
        }


    };


    //////////////////////////////////////////////////////////////////
//   Manager updates ticket
    public Handler managerUpdateTickets = context -> {
        if (caches.level == Cache.Level.MANAGER){

//            User in = context.bodyAsClass(User.class);
            Ticket in = context.bodyAsClass(Ticket.class);
            boolean update = ticketService.updatetic(in.getId(), in.status);

            if(update){
                List<Ticket> tickets = ticketService.getAll();
                String lists = "";
                for(Ticket tic: tickets){
                    lists+=tic.toString()+"\n";
                }
                context.result("Updated\nNew queue below \n" + lists);
                caches.ticketList = tickets;
            }else {
                context.result("not updated, please enter id and status");
            }

        }else{
            context.result("You need to be a Manager to update ticket");
        }


    };



}
