package com.revature.repos;

import com.revature.models.Ticket;
import com.revature.models.User;
import com.revature.utils.CRUDDaoInterface;
import com.revature.utils.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketRepo implements CRUDDaoInterface<Ticket> {



    public Connection conn;



    private static final Logger LOGGER = LoggerFactory.getLogger(TicketRepo.class);


    public TicketRepo(){


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
    public int create(Ticket ticket) {
//        Although it says create we are inserting into a table that is already created
//        however we are still creating a new row...


        try {
            String sql = ("INSERT INTO tickets (id, amount, description, creator_id, ticket_type) " +
                    "VALUES (default, ?, ?, ?, CAST(? AS TicketType))");
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setDouble(1,ticket.getAmount());
            pstmt.setString(2, ticket.getDescription());
            pstmt.setInt(3, ticket.getCreator_id());
//            Ticket.TYPE type = Ticket.TYPE.valueOf(ticket.getType().toString());
            pstmt.setString(4, ticket.getType().toString());


//            this executes the sql statement above
            pstmt.executeUpdate();

//            the gives us a result set of the row that was just created
            ResultSet rs = pstmt.getGeneratedKeys();

//            the cursor is right in front of the first (or only) element in our result set
//            calling rs.next() iterates us into the first row
            rs.next();

            return rs.getInt("id");

        }catch(SQLException sqlException) {

            System.out.println(sqlException.getMessage());
        }
        return 0;
    }

    @Override
    public boolean changePos(Ticket ticket) {
        return false;
    }

    @Override
    public List<Ticket> getAll() {
        List<Ticket> tickets = new ArrayList<Ticket>();

        try {

            String sql = "SELECT * FROM tickets WHERE status = 'pending' ORDER BY id ASC";


            PreparedStatement pstmt = conn.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt("id"));
                ticket.setAmount(rs.getDouble("amount"));
                ticket.setDescription(rs.getString("description"));
                ticket.setStatus(Ticket.STATUS.valueOf(rs.getString("status")));
                ticket.setType(Ticket.TYPE.valueOf(rs.getString("ticket_type")));
                ticket.setCreator_id(rs.getInt("creator_id"));


                tickets.add(ticket);

            }

            return tickets;

        }catch(SQLException sqlException){

            System.out.println(sqlException.getMessage());
        }

        return null;
    }

    @Override
    public List<Ticket> getTicketByType(int creator_id, Ticket.TYPE type) {

        List<Ticket> tickets = new ArrayList<Ticket>();

        try {

            String sql = "SELECT * FROM tickets where creator_id = ? AND ticket_type = CAST(? AS TicketType)";

//            check if type works now

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,creator_id);
            System.out.println(type.toString());
            pstmt.setString(2, type.toString());
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt("id"));
                ticket.setAmount(rs.getDouble("amount"));
                ticket.setDescription(rs.getString("description"));
                ticket.setStatus(Ticket.STATUS.valueOf(rs.getString("status")));
                ticket.setType(Ticket.TYPE.valueOf(rs.getString("ticket_type")));
                ticket.setCreator_id(rs.getInt("creator_id"));


                tickets.add(ticket);

            }

            return tickets;

        }catch(SQLException sqlException){

            System.out.println(sqlException.getMessage());
        }

        return null;
    }

    @Override
    public List<Ticket> getTicketByCreator_id(int creator_id) {

        List<Ticket> tickets = new ArrayList<Ticket>();

        try {

            String sql = "SELECT * FROM tickets where creator_id = ?";


            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,creator_id);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt("id"));
                ticket.setAmount(rs.getDouble("amount"));
                ticket.setDescription(rs.getString("description"));
                ticket.setStatus(Ticket.STATUS.valueOf(rs.getString("status")));
                ticket.setType(Ticket.TYPE.valueOf(rs.getString("ticket_type")));
                ticket.setCreator_id(rs.getInt("creator_id"));


                tickets.add(ticket);

            }

            return tickets;

        }catch(SQLException sqlException){

            System.out.println(sqlException.getMessage());
        }

        return null;
    }

    @Override
    public Ticket getById(int id) {
        return null;
    }

    @Override
    public Ticket getLogin(String username, String password) {
        return null;
    }

    @Override
    public Ticket update(Ticket ticket) {
        return null;
    }

    @Override
    public boolean updatetic(int id, Ticket.STATUS status) {
//        Change status for ticket
        try{

            String sql = "UPDATE tickets SET status = CAST(? AS status) WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, status.toString());
            pstmt.setInt(2,id);

            pstmt.executeUpdate();
            return true;

        }catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }


        return false;
    }

    @Override
    public boolean delete(Ticket ticket) {
        return false;
    }
}
