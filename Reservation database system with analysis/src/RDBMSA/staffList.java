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
public class staffList {
    private final SimpleIntegerProperty sID;
    private final SimpleStringProperty SFN;
    private final SimpleStringProperty SSN;
    private final SimpleStringProperty SDOB;
    private final SimpleIntegerProperty SPH;
    private final SimpleStringProperty SA;
    private final SimpleStringProperty SU;
    private final SimpleStringProperty SPW;
    private final SimpleStringProperty SROLE;
    
    public staffList(int ID, String FN, String  SN, String DOB, int PN, String ADD, String UN, String PW, String ROLE) {
        this.sID = new SimpleIntegerProperty(ID);
        this.SFN = new SimpleStringProperty(FN);
        this.SSN = new SimpleStringProperty(SN);
        this.SDOB = new SimpleStringProperty(DOB);
        this.SPH = new SimpleIntegerProperty(PN);
        this.SA = new SimpleStringProperty(ADD);
        this.SU = new SimpleStringProperty(UN);
        this.SPW = new SimpleStringProperty(PW);
        this.SROLE = new SimpleStringProperty(ROLE);
    }

    public int getStaffID() {
        return sID.get();
    }

    public void setStaffID(int sID) {
        this.sID.set(sID);
    }
    
    public String getFirstName() {
        return SFN.get();
    }

    public void setFirstName(String Fname) {
        this.SFN.set(Fname);
    }

    public String getSurName() {
        return SSN.get();
    }

    public void setSurName(String Sname) {
        this.SSN.set(Sname);
    }
    
    public String getDOB(){
        return SDOB.get();
    }

    public void setDOB(String BD) {
        this.SDOB.set(BD);
    } 
    
    public int getPnumber() {
        return SPH.get();
    }

    public void setPnumber(int pnumber) {
        this.SPH.set(pnumber);
    }   
    
    public String getSA(){
        return SA.get();
    }

    public void setSA(String ADD) {
        this.SA.set(ADD);
    } 
    
    public String getSU(){
        return SU.get();
    }

    public void setSU(String UN) {
        this.SU.set(UN);
    } 
    
    public String getSPW(){
        return SPW.get();
    }

    public void setSPW(String PW) {
        this.SPW.set(PW);
    } 

    public String getSROLE(){
        return SROLE.get();
    }

    public void setSROLE(String ROLE) {
        this.SROLE.set(ROLE);
    }
}
