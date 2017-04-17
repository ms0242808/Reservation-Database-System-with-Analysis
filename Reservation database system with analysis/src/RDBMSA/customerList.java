/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RDBMSA;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author jerry
 */
public class customerList {
    private final SimpleIntegerProperty cID;
    private final SimpleStringProperty Fname;
    private final SimpleStringProperty Sname;
    private final SimpleIntegerProperty Nodiner;
    private final SimpleStringProperty Bdate;
    private final SimpleStringProperty Btime;
    private final SimpleStringProperty pnumber;
    private final SimpleStringProperty EMAIL;
    private final SimpleStringProperty SpecialR;
    private final SimpleStringProperty Period;
    private final SimpleStringProperty cCode;
    
    public customerList(int cID, String FN, String  SN, int NOD, String BD, String BT, String PN, String EM, String SR, String PD, String CC) {
        this.cID = new SimpleIntegerProperty(cID);
        this.Fname = new SimpleStringProperty(FN);
        this.Sname = new SimpleStringProperty(SN);
        this.Nodiner = new SimpleIntegerProperty(NOD);
        this.Bdate = new SimpleStringProperty(BD);        
        this.Btime = new SimpleStringProperty(BT);
        this.pnumber = new SimpleStringProperty(PN);
        this.EMAIL = new SimpleStringProperty(EM);
        this.SpecialR = new SimpleStringProperty(SR);
        this.Period = new SimpleStringProperty(PD);
        this.cCode = new SimpleStringProperty(CC);
    }

    public int getCustomerID() {
        return cID.get();
    }

    public void setCustomerID(int cID) {
        this.cID.set(cID);
    }
    
    public String getFirstName() {
        return Fname.get();
    }

    public void setFirstName(String Fname) {
        this.Fname.set(Fname);
    }

    public String getSurName() {
        return Sname.get();
    }

    public void setSurName(String Sname) {
        this.Sname.set(Sname);
    }
    
    public int getNumberofdiner() {
        return Nodiner.get();
    }

    public void setNumberofdiner(int Nodiner) {
        this.Nodiner.set(Nodiner);
    }
    
    public String getBdate() {
        return Bdate.get();
    }

    public void setBdate(String Bdate) {
        this.Bdate.set(Bdate);
    } 
    
    public String getBtime() {
        return Btime.get();
    }

    public void setBtime(String Btime) {
        this.Btime.set(Btime);
    } 
    
    public String getPnumber() {
        return pnumber.get();
    }

    public void setPnumber(String pnumber) {
        this.pnumber.set(pnumber);
    }   
    
    public String getEmail() {
        return EMAIL.get();
    }

    public void setEmail(String EMAIL) {
        this.EMAIL.set(EMAIL);
    }
    
    public String getSrequest() {
        return SpecialR.get();
    }

    public void setSrequest(String SpecialR) {
        this.SpecialR.set(SpecialR);
    }
    
    public String getPeriod() {
        return Period.get();
    }

    public void setPeriod(String Period) {
        this.Period.set(Period);
    }
    
    public String getCcode() {
        return cCode.get();
    }

    public void setCcode(String cCode) {
        this.cCode.set(cCode);
    }
}