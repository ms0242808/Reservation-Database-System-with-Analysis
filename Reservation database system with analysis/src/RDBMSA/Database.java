/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
}
