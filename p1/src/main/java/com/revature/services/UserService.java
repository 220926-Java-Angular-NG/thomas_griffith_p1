package com.revature.services;

import com.revature.models.User;
import com.revature.repos.UserRepo;
import com.revature.utils.CRUDDaoInterface;

import java.util.ArrayList;
import java.util.List;

public class UserService {

//    CRUDDaoInterface<User> userRepo = new UserRepo();

    private UserRepo userRepo;

    //    we are creating a new instance of our UserRepo
    public UserService(){
        userRepo = new UserRepo();
    }

    //    here we are passing in an existing UserRepo
    public UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    //    create
    public int createUser(User user){

        return userRepo.create(user);
    }

    //    read all
    public List<User> getAllUsers(){
        return userRepo.getAll();
    }

    //    readById
    public User getUserById(int id){
        return userRepo.getById(id);
    }

//    readByUsername
    public User getUserByUsername(String username, String password){ return userRepo.getLogin(username, password);}

//    check for username
//    public boolean checkUsername(String username){return userRepo.checkUsername(username);}

    //    update
    public User updateUser(User user){
        return userRepo.update(user);
    }

    //    delete
    public boolean deleteUser(User user){
        return userRepo.delete(user);
    }

    public boolean changePos(User user) {return userRepo.changePos(user);}

}
