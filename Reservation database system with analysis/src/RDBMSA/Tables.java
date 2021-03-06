/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * using this Tables.java to create database tables.
 */
package RDBMSA;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author jerry
 */
public class Tables {
    private static Connection connection = null;
    private static Statement statement;
    public static void main(String[] args) {
        // load the sqlite-JDBC driver using the current class loader
        try{
            Class.forName("org.sqlite.JDBC");
        }catch(ClassNotFoundException ex){
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        try{
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:Restaurant.db");
            statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
      
            // create tables: customer, account, queue, table_time
            statement.executeUpdate("drop table if exists customer");
            statement.executeUpdate("create table customer (CustomerID INTEGER primary key NOT NULL, FirstName TEXT ,LastName TEXT, NumberOfDiner INTEGER, Date TEXT, Time TEXT, Phone TEXT, Email TEXT, AdditionalRequest TEXT, PreOrder TEXT, ConfirmCode TEXT, Year INTEGER)");
      
            statement.executeUpdate("drop table if exists account");
            statement.executeUpdate("create table account (AccountID INTEGER primary key NOT NULL, FirstName TEXT, LastName TEXT, DateOfBirth TEXT, Phone TEXT, Address TEXT, UserName TEXT, PassWord TEXT, Role TEXT)");
        
            statement.executeUpdate("drop table if exists queue");
            statement.executeUpdate("create table queue (QueueID INTEGER primary key NOT NULL, Name TEXT, Phone TEXT, Diners TEXT, Date TEXT, Period TEXT, Inrest TEXT, ApproEtime INTEGER)");
            
            statement.executeUpdate("drop table if exists table_Info");
            statement.executeUpdate("create table table_Info (TableInfoID INTEGER primary key NOT NULL, Diner TEXT, Tableset INTEGER, Maxtime TEXT)");
            
            statement.executeUpdate("drop table if exists availability");
            statement.executeUpdate("create table availability (AvailabilityID INTEGER primary key NOT NULL, Tableleft INTEGER, Diner TEXT, Date TEXT, Period TEXT)");
            
            statement.executeUpdate("drop table if exists table_time");
            statement.executeUpdate("create table table_time (TableTimeID INTEGER primary key NOT NULL, Date TEXT, StartTime TEXT, EndTime TEXT, Period TEXT, Diner TEXT, Approx INTEGER, Finished TEXT)");
                       
            //close connection
            close();
        }catch(SQLException e){
            // if the error message is "out of memory", 
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } 
    }
   
    public static void close(){
        try {
            connection.close();
        } catch(Exception e){
            // this shouldn't happen
            System.out.println(e);
        }
    }  
    
}