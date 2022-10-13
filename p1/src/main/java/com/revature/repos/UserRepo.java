package com.revature.repos;

import com.revature.models.User;
import com.revature.utils.CRUDDaoInterface;
import com.revature.utils.ConnectionManager;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jws.soap.SOAPBinding;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepo implements CRUDDaoInterface<User> {

    public Connection conn;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepo.class);


    public UserRepo(){


        // Note: certain methods throw errors because there are different instances of what can go wrong
        // in order to handle those errors in a way that saves the application from crashing
        // we wrap those methods or blocks of code in a "try{}catch(){}"block
        try {

            // this is the code that can throw an error
//            conn = DriverManager.getConnection(url, username, password);
            conn = ConnectionManager.getConnection();
//            System.out.println(conn.toString());
            System.out.println(conn.getSchema());


        }catch(SQLException sqlException){

            LOGGER.error(sqlException.getMessage());

        }

    }




    //    /////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean changePos(User user) {

//        Change this for email, profile pic etc.
        try{

            String sql = "UPDATE users SET is_manager = ? WHERE id = ? OR username = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, user.getIsManager());
            pstmt.setInt(2,user.getId());
            pstmt.setString(3,user.getUsername());

            pstmt.executeUpdate();

            return true;
        }catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }


        return false;
    }

    @Override
    public int create(User user) {

//        Although it says create we are inserting into a table that is already created
//        however we are still creating a new row...


        try {
            String sql = "INSERT INTO users (id, username, pass_word, is_manager) VALUES (default, ?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1,user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setInt(3, user.getIsManager());


//            this executes the sql statement above
            pstmt.executeUpdate();

//            the gives us a result set of the row that was just created
            ResultSet rs = pstmt.getGeneratedKeys();

//            the cursor is right in front of the first (or only) element in our result set
//            calling rs.next() iterates us into the first row
            rs.next();

            return rs.getInt("id");

        }catch(SQLException sqlException){

            System.out.println(sqlException.getMessage());
        }
        return 0;
    }

    @Override
    public List<User> getAll() {

        List<User> users = new ArrayList<User>();

        try {

            String sql = "SELECT * FROM users";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("pass_word"));
                user.setIsManager(rs.getInt("is_manager"));
//                user.setPassword(rs.getString("pass_word"));

                users.add(user);

            }

            return users;

        }catch(SQLException sqlException){

            System.out.println(sqlException.getMessage());
        }

        return null;
    }

    @Override
    public User getById(int id) {

        try{
            String sql = "SELECT * FROM users WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,id);

            ResultSet rs = pstmt.executeQuery();

//            we are returning a user (by his id)
//            therefore we have to create a new instance of a user from the database

            User user = new User();

            while(rs.next()){
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("pass_word"));
                user.setIsManager(rs.getInt("is_manager"));
            }

            return user;


        }catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }




        return null;
    }

//    ////////////////////////////////////////////////////////////////////////////////


    @Override
    public User getLogin(String username, String password) {

        try{
            String sql = "SELECT * FROM users WHERE username = ? AND pass_word = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,username);
            pstmt.setString(2,password);

            ResultSet rs = pstmt.executeQuery();

//            we are returning a user (by his id)
//            therefore we have to create a new instance of a user from the database

            User user = new User();

            while(rs.next()){
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("pass_word"));
                user.setIsManager(rs.getInt("is_manager"));
            }
            System.out.println(user);
            return user;


        }catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }




        return null;
    }

//    @Override
//    public boolean checkUsername(String username) {
//        try{
//            String sql = "SELECT * FROM users WHERE username = ?";
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1,username);
//
//            ResultSet rs = pstmt.executeQuery();
//
////            we are returning a user (by his id)
////            therefore we have to create a new instance of a user from the database
//
//            User user = new User();
//
//            while(rs.next()){
//                user.setId(rs.getInt("id"));
//                user.setFirst_name(rs.getString("first_name"));
//                user.setLast_name(rs.getString("last_name"));
//                user.setEmail(rs.getString("email"));
//                user.setPassword(rs.getString("pass_word"));
//            }
//
//            return true;
//
//
//        }catch(SQLException sqlException){
//            System.out.println(sqlException.getMessage());
//
//        }
//
//
//
//
//        return false;
//    }

//    /////////////////////////////////////////////////////////////////////////////////

    @Override
    public User update(User user) {

//        Change this for email, profile pic etc.
        try{

            String sql = "UPDATE users SET email = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getEmail());
            pstmt.setInt(2,user.getId());

            pstmt.executeUpdate();


        }catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }


        return null;
    }

    @Override
    public boolean delete(User user) {

        try {

            String sql = "DELETE FROM users WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1,user.getId());

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();

            return true;

        }catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return false;
    }
}
