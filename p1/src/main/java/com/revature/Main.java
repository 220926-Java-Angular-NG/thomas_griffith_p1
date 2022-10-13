package com.revature;
import com.revature.controllers.UserController;
import com.revature.controllers.MainController;
import com.revature.models.User;
import com.revature.services.UserService;
import io.javalin.Javalin;


public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        Javalin app = Javalin.create().start(8080);

        UserService userService = new UserService();
        UserController userController = new UserController(userService);
        MainController mainController = new MainController();




        User user1 = new User("tomgriff","password", 1);

        app.get("/", context -> {
            context.result("HomePage to management.com project-1\n"
                    + "go to /users  to manage users\n"
                    + "go to /tickets to manage tickets\n"
                    + "go to /login  to login to your account\n"
                    + "go to /register into a new account");
        });

//        app.post("/user", userController.createNewUser);

//       Loggin info, Log in, log out methods [check]
        app.get("/login", mainController.loginGet);
        app.post("/login", mainController.login);
        app.delete("/login", mainController.loginout);


//        Register info, Register new User [Check]
        app.get("/register", mainController.registerHome);
        app.post("/register", mainController.register);



//        app.get("/users",userController.getAllUsers);


    }
}