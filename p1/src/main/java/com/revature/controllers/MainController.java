package com.revature.controllers;

import com.revature.models.Cache;
import com.revature.models.User;
import com.revature.services.UserService;
import io.javalin.http.Handler;


public class MainController {

    Cache caches = new Cache();

    UserController userController = new UserController();

    UserService userService = new UserService();


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

        System.out.println(caches);

        }else{
            context.result("Already logged in\n log out at /login if new user");
        }

//        User in = context.bodyAsClass(User.class);
//        System.out.println(in.getUsername() + "   " + in.getPassword());
//        User user = userService.getUserByUsername(in.getUsername(), in.getPassword());
//
//        caches.curUserID = user.getId();
//        caches.curUsername = user.getUsername();
//        if(caches.curUsername == null){
//            context.result("incorrect username or password").status(400);
//            return;
//        }
//
//        if (user.getIsManager() == 1) {
//            caches.level = Cache.Level.MANAGER;
//        } else{
//            caches.level = Cache.Level.EMPLOYEE;
//        }
//
//
//        System.out.println(caches.curUsername + " is logged in as an " + caches.level);


    };

}
