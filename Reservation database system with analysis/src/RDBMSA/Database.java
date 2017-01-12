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
    
    //Customer SQL methods
    static void booktable(String FN,String SN,String Date,String Time,String Phone, String Email,String Note,String Order, String Code){
        String selectstat = "insert into customer (FirstName,LastName,Date,Time,Phone,Email,AdditionalRequest,PreOrder,ConfirmCode) values(?,?,?,?,?,?,?,?,?)";
        open();
        
        try{
            connection.setAutoCommit(false);
            PreparedStatement prepStmt =  connection.prepareStatement(selectstat);
            prepStmt.setString(1,FN);
            prepStmt.setString(2,SN);
            prepStmt.setString(3,Date);
            prepStmt.setString(4,Time);
            prepStmt.setString(5,Phone);
            prepStmt.setString(6,Email);
            prepStmt.setString(7,Note);
            prepStmt.setString(8,Order);
            prepStmt.setString(9,Code);
  
            prepStmt.executeUpdate();
            connection.commit();

            ResultSet rs = statement.executeQuery("select * from customer");
            while(rs.next()){
                // read the result set
                //System.out.println("name = " + rs.getString("FirstName"));
                //System.out.println("id = " + rs.getInt("customerID"));
                //System.out.println("surname = " + rs.getString("LastName"));
                //System.out.println("email = " + rs.getString("email"));
                //System.out.println("password = " + rs.getString("password"));
                //System.out.println("date of birth = " + rs.getString("dateOfBirth"));
                //System.out.println("address = " + rs.getString("address1"));
                //System.out.println("city = " + rs.getString("city"));
                //System.out.println("phone number = " + rs.getString("phoneNumber"));       
            }
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        
        close();
    }
    
    //Account/manager/staff SQL methods
}
