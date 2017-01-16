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
    private final SimpleStringProperty Bdate;
    private final SimpleStringProperty Btime;
    private final SimpleIntegerProperty pnumber;
    private final SimpleStringProperty EMAIL;
    private final SimpleStringProperty SpecialR;
    private final SimpleStringProperty pOrder;
    private final SimpleStringProperty cCode;

    
    public customerList(int cID, String FN, String  SN, String BD, String BT, int PN, String EM, String SR, String PO, String CC) {
        this.cID = new SimpleIntegerProperty(cID);
        this.Fname = new SimpleStringProperty(FN);
        this.Sname = new SimpleStringProperty(SN);
        this.Bdate = new SimpleStringProperty(BD);        
        this.Btime = new SimpleStringProperty(BT);
        this.pnumber = new SimpleIntegerProperty(PN);
        this.EMAIL = new SimpleStringProperty(EM);
        this.SpecialR = new SimpleStringProperty(SR);
        this.pOrder = new SimpleStringProperty(PO);
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
    
    public int getPnumber() {
        return pnumber.get();
    }

    public void setPnumber(int pnumber) {
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
    
    public String getPorder() {
        return pOrder.get();
    }

    public void setPorder(String pOrder) {
        this.pOrder.set(pOrder);
    }
    
    public String getCcode() {
        return cCode.get();
    }

    public void setCcode(String cCode) {
        this.cCode.set(cCode);
    }
}
