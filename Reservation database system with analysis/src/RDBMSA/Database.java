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
    public static String getMaxDiner(){
        String selectStatement = "SELECT Diner FROM table_Info";
        String maxD = null;
        open();
        try{
            PreparedStatement prepStmt =  connection.prepareStatement(selectStatement);
            //prepStmt.setString(1,diner);
            //prepStmt.setString(1,dt);
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                String mDiner[] = rs.getString("Diner").split("-");
                maxD = mDiner[1];
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
            return maxD;          
        }
        close();
        return maxD;   
    }
    
    public static int avaTLCheck(String diner, String dt, String period){
        String selectStatement = "select Tableleft from availability WHERE Diner = '" + diner + "' AND Date = '" + dt + "' AND Period = '" + period + "'";
        int tl = -2;
        open();
        try{
            PreparedStatement prepStmt =  connection.prepareStatement(selectStatement);
            
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                tl = rs.getInt("Tableleft");
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
            return -1;          
        }
        close();
        return tl;
    }
    
    public static void booktable(String FN,String SN, int NDiner,String Date,String Time,String Phone, String Email,String Note,String Order, String Code, String Yr){
        String selectstat = "insert into customer (FirstName,LastName,NumberOfDiner,Date,Time,Phone,Email,AdditionalRequest,PreOrder,ConfirmCode,Year) values(?,?,?,?,?,?,?,?,?,?,?)";
        open();        
        try{
            connection.setAutoCommit(false);
            PreparedStatement prepStmt =  connection.prepareStatement(selectstat);
            prepStmt.setString(1,FN);
            prepStmt.setString(2,SN);
            prepStmt.setInt(3,NDiner);
            prepStmt.setString(4,Date);
            prepStmt.setString(5,Time);
            prepStmt.setString(6,Phone);
            prepStmt.setString(7,Email);
            prepStmt.setString(8,Note);
            prepStmt.setString(9,Order);
            prepStmt.setString(10,Code);
            prepStmt.setString(11,Yr);
            prepStmt.executeUpdate();
            connection.commit();
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }      
        close();
    }
    
    public static int avaTableCheck(String diner, String dt, String period){
        String selectStatement = "select AvailabilityID from availability WHERE Diner = '" + diner + "' AND Date = '" + dt + "' AND Period = '" + period + "'";
        int aid = 0;
        open();
        try{
            PreparedStatement prepStmt =  connection.prepareStatement(selectStatement);
            //prepStmt.setString(1,diner);
            //prepStmt.setString(1,dt);
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                aid = rs.getInt("AvailabilityID");
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
            return -1;          
        }
        close();
        return aid;   
    }
    
    public static int tableSet(String diner){
        String selectStatement = "select Tableset from table_Info WHERE Diner = '" + diner +  "'";
        int ts = 0;
        open();
        try{
            PreparedStatement prepStmt =  connection.prepareStatement(selectStatement);
            //prepStmt.setString(1,diner);
            //prepStmt.setString(1,dt);
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                ts = rs.getInt("Tableset");
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
            return -1;          
        }
        close();
        return ts;
    }
    
    public static void updateAvail(int aid, int tl, String din, String dt, String period){
        String stat1 = "insert into availability (Tableleft, Diner, Date, Period) values (?,?,?,?)";
        String stat2 = "UPDATE availability set Tableleft = ?, Diner = ?, Date = ?, Period = ? WHERE AvailabilityID = '" + aid + "'";
        String selectstat = null;
        
        if(aid == 0){
            selectstat = stat1;
        }else if(aid > 0){
            selectstat = stat2;
        }
        
        open();
        try{
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(selectstat);
            ps.setInt(1,tl);
            ps.setString(2,din);
            ps.setString(3,dt);
            ps.setString(4,period);
            ps.executeUpdate();
            connection.commit();
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }      
        close();
    }
    
    public static String tableMaxtime(String diner){
        String selectStatement = "select Maxtime from table_Info WHERE Diner = '" + diner +  "'";
        String maxt = null;
        try{
            open();            
            ResultSet rs = statement.executeQuery(selectStatement);
            maxt = rs.getString("Maxtime");
            //System.out.println("Query successfully executed");
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }
        close();
        return maxt;
    }
    
    public static void bookTableT(String date, String st, String et, String pd, String ds, int approx, String done){
        String selectstat = "insert into table_time (Date, StartTime, EndTime, Period, Diner, Approx, Finished) values (?,?,?,?,?,?,?)";
        
        open();
        try{
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(selectstat);
            ps.setString(1,date);
            ps.setString(2,st);
            ps.setString(3,et);
            ps.setString(4,pd);
            ps.setString(5,ds);
            ps.setInt(6,approx);
            ps.setString(7,done);
            ps.executeUpdate();
            connection.commit();
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }      
        close();
    }
    
    //EditBooking SQL methods
    public static int phoneCheck(String phoneText, String code){
        String selectStatement = "SELECT CustomerID FROM customer WHERE Phone = '" + phoneText + "' AND ConfirmCode = '" + code + "'";
        int userid = 0;
        open();
        try{
            PreparedStatement prepStmt =  connection.prepareStatement(selectStatement);
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
    
    public static int getCID(String Phone){
        int cID = 0;
        try{
            open();            
            ResultSet rs = statement.executeQuery("select CustomerID from customer where Phone = '" + Phone + "' ");
            cID = rs.getInt("CustomerID");
            //System.out.println("Query successfully executed");
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }
        close();
        return cID;
    }
    
    public static String getFirstname(int cID){
        String firstname = null;
        try{
            open();            
            ResultSet rs = statement.executeQuery("select FirstName from customer WHERE CustomerID = '" + cID + "' ");
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
            ResultSet rs = statement.executeQuery("select LastName from customer WHERE CustomerID = '" + cID + "' ");
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
            ResultSet rs = statement.executeQuery("select Email from customer where CustomerID = '" + cID + "' ");
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
            ResultSet rs = statement.executeQuery("select Phone from customer where CustomerID = '" + cID + "' ");
            phone = rs.getString("Phone");
            //System.out.println("Query successfully executed");
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }
        close();
        return phone;
    }

    public static int getNumberofdiner(int cID){
        int diner = 0;
        try{
            open();            
            ResultSet rs = statement.executeQuery("select NumberOfDiner from customer where CustomerID = '" + cID + "' ");
            diner = rs.getInt("NumberOfDiner");
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
            ResultSet rs = statement.executeQuery("select Date from customer where CustomerID = '" + cID + "' ");
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
            ResultSet rs = statement.executeQuery("select Time from customer WHERE CustomerID = '" + cID + "' ");
            time = rs.getString("Time");
            //System.out.println("Query successfully executed");
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }
        close();
        return time;
    }
    
    public static void updateBooked(int cID, String Fname, String Sname, int Diners, String Date, String Time, String Email, String code) {
        String selectstat = "UPDATE customer set FirstName = ?, LastName = ?, NumberOfDiner = ?,Date = ?, Time = ?, Email = ? WHERE CustomerID = '" + cID + "' AND ConfirmCode = '" + code + "'";
        open();
        try{
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(selectstat);
            ps.setString(1,Fname);
            ps.setString(2,Sname);
            ps.setInt(3,Diners);
            ps.setString(4,Date);
            ps.setString(5,Time);
            ps.setString(6,Email);
            ps.executeUpdate();
            connection.commit();
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }      
        close();     
    }
    
    public static void removeBooked(String Phone){
        try{
            open();
            statement.executeUpdate("DELETE from customer WHERE Phone = '"+ Phone + "'");   
        } catch (SQLException e){
            System.out.println(e);
        }
        close();
    }
    
    //Online self-queue system SQL methods
    public static void queuetable(String Name, String Phone, String Diners, String Dt,String Period, String TF, int EnterT){
        String selectstat = "insert into queue (Name, Phone, Diners, Date, Period, Inrest, ApproEtime) values(?,?,?,?,?,?,?)";
        open();        
        try{
            connection.setAutoCommit(false);
            PreparedStatement prepStmt =  connection.prepareStatement(selectstat);
            prepStmt.setString(1,Name);
            prepStmt.setString(2,Phone);
            prepStmt.setString(3,Diners);
            prepStmt.setString(4,Dt);
            prepStmt.setString(5,Period);
            prepStmt.setString(6,TF);
            prepStmt.setInt(7,EnterT);
            prepStmt.executeUpdate();
            connection.commit();
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }      
        close();
    }
    
    public static int countQueueD(int dinerset1, String dt, String period, String inrest) {
        String selectStatement = "SELECT COUNT(*) FROM queue WHERE Diners = '"+ dinerset1 + "' AND Date = '" + dt + "' AND Period = '" + period + "' AND Inrest = '" + inrest + "'";
        int count = 0;
        open();
        try{
            ResultSet rs = statement.executeQuery(selectStatement);
            while (rs.next()) {
                count = rs.getInt(1);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return 0;  
        }
        close();
        return count;
    }
    
    public static int selectQueueEnterT(int diner, String dt, String period, String inrest){
        String selectStatement = "SELECT ApproEtime FROM queue WHERE Diners = '"+ diner + "' AND Date = '" + dt + "' AND Period = '" + period + "' AND Inrest = '" + inrest + "'";
        int enterT = 0;
        open();
        try{
            ResultSet rs = statement.executeQuery(selectStatement);
            while (rs.next()) {
                enterT = rs.getInt(1);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return 0;  
        }
        close();
        return enterT;
    }
    
    public static void updateQueue(int diner, String dt, String period, String inrest) {
        String selectstat = "UPDATE queue set Inrest = ? WHERE Diners = '"+ diner + "' AND Date = '" + dt + "' AND Period = '" + period + "'";
        open();
        try{
            PreparedStatement ps = connection.prepareStatement(selectstat);
            ps.setString(1,inrest);
            ps.executeUpdate();
            connection.commit();
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }      
        close();     
    }
    
    public static int selectApprox(String dt, String diner, String period, String done){
        String selectStatement = "SELECT MIN(Approx) FROM table_time WHERE Date = '"+ dt + "' AND Diner = '" + diner + "' AND Period = '" + period +  "' AND Finished = '" + done + "'";
        int approx = 0;
        open();
        try{
            ResultSet rs = statement.executeQuery(selectStatement);
            while (rs.next()) {
                approx = rs.getInt(1);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return 0;  
        }
        close();
        return approx;
    }
    
    public static void removeQueue(String Phone){
        try{
            open();
            statement.executeUpdate("DELETE from queue WHERE Phone = '"+ Phone + "'");   
        } catch (SQLException e){
            System.out.println(e);
        }
        close();
    }
    
    public static void updateTableT(String dt, String period, int approx, String done) {
        String selectstat = "UPDATE table_time set Finished = ? WHERE Date = '"+ dt + "' AND Period = '" + period + "' AND Approx = '" + approx + "'";
        open();
        try{
            PreparedStatement ps = connection.prepareStatement(selectstat);
            ps.setString(1,done);
            ps.executeUpdate();
            connection.commit();
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }      
        close();     
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
    public static void updateBooking(int customerID, String fn, String sn, int diner, String date, String ctime, String phone, String cemail, String request, String porder) {
        String selectstat = "UPDATE customer set FirstName = ?, LastName = ?, NumberOfDiner = ?,Date = ?, Time = ?, Phone = ?, Email = ?, AdditionalRequest = ?, PreOrder = ? where CustomerID = "+ customerID;
        open();
        try{
            PreparedStatement ps = connection.prepareStatement(selectstat);
            ps.setString(1,fn);
            ps.setString(2,sn);
            ps.setInt(3,diner);
            ps.setString(4,date);
            ps.setString(5,ctime);
            ps.setString(6,phone);
            ps.setString(7,cemail);
            ps.setString(8,request);
            ps.setString(9,porder);
            ps.executeUpdate();
            connection.commit();
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }      
        close();     
    }
    
    public static int countRecords() {
        String selectStatement = "SELECT COUNT(*) FROM customer";
        int count = 0;
        open();
        try{
            ResultSet rs = statement.executeQuery(selectStatement);
            while (rs.next()) {
                count = rs.getInt(1)*2;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return 0;  
        }
        close();
        return count;
    }
    
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
    
    //statistics, analysis methods
    public static int maxDiner(){
        String selectStatement = "SELECT MAX(NumberOfDiner) from customer";
        int maxd = 0;
        open();
        try{
            ResultSet rs = statement.executeQuery(selectStatement);
            while (rs.next()) {
                maxd = rs.getInt(1);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return 0;  
        }
        close();
        return maxd;
    }
    
    public static int minDiner(){
        String selectStatement = "SELECT MIN(NumberOfDiner) from customer";
        int mind = 0;
        open();
        try{
            ResultSet rs = statement.executeQuery(selectStatement);
            while (rs.next()) {
                mind = rs.getInt(1);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return 0;  
        }
        close();
        return mind;
    }
    
    public static int avgDiner(){
        String selectStatement = "SELECT AVG(NumberOfDiner) from customer";
        int avgd = 0;
        open();
        try{
            ResultSet rs = statement.executeQuery(selectStatement);
            while (rs.next()) {
                avgd = rs.getInt(1);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return 0;  
        }
        close();
        return avgd;
    }
    
    //table setting methods
    public static int settingTSet(String diner){
        String selectStatement = "SELECT Tableset from table_Info WHERE Diner = '"+ diner +"'";
        int Tset = 0;
        open();
        try{
            ResultSet rs = statement.executeQuery(selectStatement);
            while (rs.next()) {
                Tset = rs.getInt(1);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return 0;  
        }
        close();
        return Tset;
    }
    
    public static String settingMTime(String diner){
        String selectStatement = "SELECT Maxtime from table_Info WHERE Diner = '"+ diner +"'";
        String maxTime = null;
        open();
        try{
            ResultSet rs = statement.executeQuery(selectStatement);
            while(rs.next()){
                maxTime = rs.getString("Maxtime");
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        close();
        return maxTime;
    }
    
    public static void updateTableInfo(String diner, int tset, String maxtime){
        String selectstat = "UPDATE table_Info set Diner = ?, Tableset = ?, Maxtime = ? WHERE Diner = '"+ diner +"'";
        open();        
        try{
            connection.setAutoCommit(false);
            PreparedStatement prepStmt =  connection.prepareStatement(selectstat);
            prepStmt.setString(1,diner);
            prepStmt.setInt(2,tset);
            prepStmt.setString(3,maxtime);
            prepStmt.executeUpdate();
            connection.commit();
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }      
        close();
    }
}