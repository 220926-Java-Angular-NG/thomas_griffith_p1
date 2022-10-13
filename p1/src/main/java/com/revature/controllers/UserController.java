package com.revature.controllers;

import com.revature.models.User;
import com.revature.services.UserService;
import io.javalin.http.Handler;

public class UserController {

    UserService service;

    public UserController(){
        service = new UserService();
    }

    public UserController(UserService service){
        this.service = service;
    }



    //////////////////////////////////////////////////////////////////
//    login user

    public Handler loginUser = context -> {


        //grab the object from the request body (postman)
        //send the incoming user to our service, so it can
        //reach out to our repo layer and execute the request
        User user = context.bodyAsClass(User.class); //change the json from postman to an object
        System.out.println(user);
        int id = service.createUser(user);

        if(id > 0){
//            valid number (represents the primary key)
            user.setId(id);
            context.json(user);
        }else{
            //something went wrong
            context.result("User not created").status(400);
        }

    };



    //////////////////////////////////////////////////////////////////
//    login user

    //////////////////////////////////////////////////////////////////
//    create user
    public Handler createNewUser = context -> {


        //grab the object from the request body (postman)
        //send the incoming user to our service, so it can
        //reach out to our repo layer and execute the request
        User user = context.bodyAsClass(User.class); //change the json from postman to an object
        System.out.println(user);
        int id = service.createUser(user);

        if(id > 0){
//            valid number (represents the primary key)
            user.setId(id);
            context.json(user);
        }else{
            //something went wrong
            context.result("User not created").status(400);
        }

    };

    //////////////////////////////////////////////////////////////////
//    read
// get all users here
    public Handler getAllUsers = context -> {
        context.json(service.getAllUsers());
    };

    //////////////////////////////////////////////////////////////////
//    update
// update
    public Handler updateUser = context -> {
        User user = context.bodyAsClass(User.class);
        user = service.updateUser(user);
        if (user != null) {
            context.json(user);
        } else {
            context.result("Could not update user.").status(400);
        }

    };

//////////////////////////////////////////////////////////////////
//    delete
    // delete

    public Handler deleteUser = context -> {
        User user = context.bodyAsClass(User.class);
        // if return true, then it was deleted (different that what she has)
        if (service.deleteUser(user)) {
            context.status(200);
        } else {
            context.status(400).result("could not delete user.");
        }
    };

//public Handler deleteUserById = context -> {
//
//    String param = context.pathParam("id");
//    // we are going to wrap this logic in a try catch
//    try {
//        // this is the id that we are getting from our URL
//        int id = Integer.parseInt(param);
//        User user = service.getUserById(id);
//        if (service.deleteUserById(user)) {
//            context.status(202);
//        } else {
//            context.result("User not found").status(400);
//        }
//    } catch(NumberFormatException nFMException) {
//        System.out.println(nFMException.getMessage());
//    }
//};



}
