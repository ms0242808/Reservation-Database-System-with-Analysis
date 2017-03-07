/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RDBMSA;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jerry
 */
public class Database {
    private static Connection connection = null;
    private static Statement statement;
    
    // load the sqlite-JDBC driver using the current class loader
    static{
        try{            
            Class.forName("org.sqlite.JDBC");
        } catch(ClassNotFoundException ex){
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public static void open(){
        try{
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:Restaurant.db"); 
            //src/Database/
            statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            //foreign key constraint
            statement.executeQuery( "PRAGMA foreign_keys=ON;");
        } catch(SQLException e){
            // if the error message is "out of memory", 
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }   
    }

    public static void close(){
        try{
            connection.close();
        } catch (Exception e){
            // this shouldn't happen
            System.out.println(e);
        }
    }
    
    //Method RetSet returns a ResultSet of the passed Query string, if query does not return result set, it Catches theSQLException adn prints it.
    public static ResultSet RetSet(String query) 
    {
        try
        {
        //    connection = DriverManager.getConnection("jdbc:sqlite:myDatabaseNew.db");
        //    statement = connection.createStatement();
        //    statement.setQueryTimeout(20);
            open();
            ResultSet rs = statement.executeQuery(query);
            System.out.println("Query successfully executed");
            return rs;
        } catch (SQLException e){
            System.err.println(e.getMessage());
        }
        close();
        return null;
    }
    
    //Customer SQL methods
    public static void booktable(String FN,String SN, String NDiner,String Date,String Time,String Phone, String Email,String Note,String Order, String Code){
        String selectstat = "insert into customer (FirstName,LastName,NumberOfDiner,Date,Time,Phone,Email,AdditionalRequest,PreOrder,ConfirmCode) values(?,?,?,?,?,?,?,?,?,?)";
        open();        
        try{
            connection.setAutoCommit(false);
            PreparedStatement prepStmt =  connection.prepareStatement(selectstat);
            prepStmt.setString(1,FN);
            prepStmt.setString(2,SN);
            prepStmt.setString(3,NDiner);
            prepStmt.setString(4,Date);
            prepStmt.setString(5,Time);
            prepStmt.setString(6,Phone);
            prepStmt.setString(7,Email);
            prepStmt.setString(8,Note);
            prepStmt.setString(9,Order);
            prepStmt.setString(10,Code);
            prepStmt.executeUpdate();
            connection.commit();
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }      
        close();
    }
    
    //EditBooking SQL methods
    public static int phoneCheck(String phoneText){
        String selectStatement = "select CustomerID from customer where Phone = ?";
        int userid = 0;
        open();
        try{
            PreparedStatement prepStmt =  connection.prepareStatement(selectStatement);
            prepStmt.setString(1,phoneText);
       
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                userid = rs.getInt("CustomerID");
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
            return -1;          
        }
        close();
        return userid;
    }
    
    public static String getFirstname(int cID){
        String firstname = null;
        try{
            open();            
            ResultSet rs = statement.executeQuery("select FirstName from customer where customerID = '" + cID + "' ");
            firstname = rs.getString("FirstName");
            //System.out.println("Query successfully executed");
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }
        close();
        return firstname;
    }
    
        public static String getSurname(int cID){
        String surname = null;
        try{
            open();            
            ResultSet rs = statement.executeQuery("select LastName from customer where customerID = '" + cID + "' ");
            surname = rs.getString("LastName");
            //System.out.println("Query successfully executed");
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }
        close();
        return surname;
    }
    
    public static String getEmail(int cID){
        String email = null;
        try{
            open();            
            ResultSet rs = statement.executeQuery("select Email from customer where customerID = '" + cID + "' ");
            email = rs.getString("Email");
            //System.out.println("Query successfully executed");
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }
        close();
        return email;
    }

    public static String getPhonenumber(int cID){
        String phone = null;
        try{
            open();            
            ResultSet rs = statement.executeQuery("select Phone from customer where customerID = '" + cID + "' ");
            phone = rs.getString("Phone");
            //System.out.println("Query successfully executed");
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }
        close();
        return phone;
    }

    public static String getNumberofdiner(int cID){
        String diner = null;
        try{
            open();            
            ResultSet rs = statement.executeQuery("select NumberOfDiner from customer where customerID = '" + cID + "' ");
            diner = rs.getString("NumberOfDiner");
            //System.out.println("Query successfully executed");
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }
        close();
        return diner;
    }

    public static String getDate(int cID){
        String date = null;
        try{
            open();            
            ResultSet rs = statement.executeQuery("select Date from customer where customerID = '" + cID + "' ");
            date = rs.getString("Date");
            //System.out.println("Query successfully executed");
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }
        close();
        return date;
    }

    public static String getTime(int cID){
        String time = null;
        try{
            open();            
            ResultSet rs = statement.executeQuery("select Time from customer where customerID = '" + cID + "' ");
            time = rs.getString("Time");
            //System.out.println("Query successfully executed");
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }
        close();
        return time;
    }    
    
    //Account/manager/staff SQL methods
    //Account login methods
    public static String getRole(int LogID){
        String role = null;
        try{
            open();
            ResultSet rs = statement.executeQuery("select Role from account where AccountID = '" + LogID + "'");
            role = rs.getString("Role");
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        close();
        return role;
    }
    
    public static int userCheck(String username){      
        String selectStatement = "select AccountID from account where UserName = ?";
        int userid = 0;
        open();
        try{
            PreparedStatement prepStmt =  connection.prepareStatement(selectStatement);
            prepStmt.setString(1,username);
       
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                userid = rs.getInt("AccountID");
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
            return -1;          
        }
        close();
        return userid;   
    }
    
    public static int passwordCheck(int id,String password){
        String selectStatement = "select PassWord from account where AccountID = ?";
        open();
        String actualPassword = null;
        try{
            PreparedStatement prepStmt =  connection.prepareStatement(selectStatement);
            prepStmt.setInt(1,id);
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                actualPassword = rs.getString("PassWord");
            }
            if(actualPassword.equals(password)){
                close();
                return -1;
            } else{
                close();       
                return 0;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
            close();
            return -1;          
        }
    }
    
    //customer records methods
    
    
    //staff records methods
    public static void addAccount(String FN,String SN, String DOB,String Phone, String Address,String Username,String Password, String Role){
        String selectstat = "insert into account (FirstName,LastName,DateOfBirth,Phone,Address,UserName,PassWord,Role) values(?,?,?,?,?,?,?,?)";
        open();     
        try{
            connection.setAutoCommit(false);
            PreparedStatement prepStmt =  connection.prepareStatement(selectstat);
            prepStmt.setString(1,FN);
            prepStmt.setString(2,SN);
            prepStmt.setString(3,DOB);
            prepStmt.setString(4,Phone);
            prepStmt.setString(5,Address);
            prepStmt.setString(6,Username);
            prepStmt.setString(7,Password);
            prepStmt.setString(8,Role);
            prepStmt.executeUpdate();
            connection.commit();
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }      
        close();
    }
    
    public static void updateAccount(int staffID, String fn, String sn, String dob, String phone, String address, String un, String pw, String role) {
        String selectstat = "UPDATE account set FirstName = ?, LastName = ?, DateOfBirth = ?, Phone = ?, Address = ?, UserName = ?, PassWord = ?, Role = ? where AccountID = "+ staffID;
        open();
        try{
            PreparedStatement ps = connection.prepareStatement(selectstat);
            ps.setString(1,fn);
            ps.setString(2,sn);
            ps.setString(3,dob);
            ps.setString(4,phone);
            ps.setString(5,address);
            ps.setString(6,un);
            ps.setString(7,pw);
            ps.setString(8,role);
            ps.executeUpdate();
            connection.commit();
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }      
        close();     
    }
    
    public static void removeAccount(int staffID){
        try{
            open();
            statement.executeUpdate("DELETE from account WHERE AccountID = '"+ staffID + "'");   
        } catch (SQLException e){
            System.out.println(e);
        }
        close();
    }
}