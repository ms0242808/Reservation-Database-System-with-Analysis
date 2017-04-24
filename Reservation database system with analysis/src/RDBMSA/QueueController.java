/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RDBMSA;

import static RDBMSA.Database.countQueueD;
import static RDBMSA.Database.getMaxDiner;
import static RDBMSA.Database.queuetable;
import static RDBMSA.Database.removeQueue;
import static RDBMSA.Database.selectApprox;
import static RDBMSA.Database.selectQueueEnterT;
import static RDBMSA.Database.updateQueue;
import static RDBMSA.Database.updateTableT;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author jerry
 */
public class QueueController implements Initializable {
    @FXML
    private AnchorPane SceneP;
    @FXML
    private JFXTextField LName;
    @FXML
    private JFXTextField LPhone;
    @FXML
    private JFXTextField LDiner;
    @FXML
    private JFXListView QueueList;
    @FXML
    private JFXButton BJoin;
    @FXML
    private JFXTextField LQPhone;
    @FXML
    private JFXButton BCancel;
    @FXML
    private JFXListView AvaList;
    
    private final ObservableList QList = FXCollections.observableArrayList();
    private final ObservableList AList = FXCollections.observableArrayList();
    FxController fx = new FxController();
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date de = new Date();
    String today = dateFormat.format(de);
    int nH = LocalTime.now().getHour();
    int nM = LocalTime.now().getMinute();
    String period = null;
    String hr = null; // get closest endding hour
    String min = null; // get closest endding mintues
    int waitingT = 0; // count table_set rquired from queue
    int nearestEnd; // select closest endding table time
    int approxH = 0; // calculate approx hour
    int approxM = 0; // calculate approx mintue
    int approxT = 0; // calculate approx total time in mintues
    String nextSet = "1-2";//next table set
    String nowT = null;//now time into string
    int EnterH = 0;
    int EnterM = 0;
    String EnterTime;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        clearField();
        
        fx.textFV(LName, "Plaese enter your name.");
        fx.numberFV(LPhone, "Please enter your phone number.");
        fx.numberFV(LDiner, "Please enter number of diners.");
        fx.numberFV(LQPhone, "Enter the phone number queued up with.");
        
        UpdateFinishedTable();
        RefreshQueue();
        QueueTable();
        AvaTable();
    }

    public void FindPeriod(){
        nH = LocalTime.now().getHour();
        nM = LocalTime.now().getMinute();
        if(nH <= 16 && nH >= 10){
            period = "lunch";
        } else if(nH <= 23 && nH >= 17){
            period = "dinner";
        }
    }
    
    public void RefreshQueue(){
        FindPeriod();
        for(int x=1; x<=10; x++){
            int approxEnterT = selectQueueEnterT(x,today,period,"F");
            if(approxEnterT!=0){
                String approxEnterH = Integer.toString(approxEnterT).substring(0,2);
                String approxEnterM = Integer.toString(approxEnterT).substring(2,4);
                int QEnterH = Integer.parseInt(approxEnterH);
                int QEnterM = Integer.parseInt(approxEnterM);
                if(QEnterH <= nH && QEnterM <= nM){
                    updateQueue(x,today,period,"T");
                }
            }         
        }
    }
    
    public void UpdateFinishedTable(){
        FindPeriod();
        for(int n=1; n<=9; n++){
            String ns = Integer.toString(n)+"-"+Integer.toString(n+1);
            int approx = selectApprox(today,ns,period,"F");
            if(approx!=0){
                String estimatedH = Integer.toString(approx).substring(0,2);
                String estimatedM = Integer.toString(approx).substring(2,4);
                if(Integer.parseInt(estimatedH) <= nH && Integer.parseInt(estimatedM) <= nM){
                    updateTableT(today,period,approx,"T");
                }
            }
        }
    }
    
    public void FindApproxTime(String ns){
        hr = null; // get closest endding hour
        min = null; // get closest endding mintues
        int nearestEnd =0; // select closest endding table time
        approxH = 0; // calculate approx hour
        approxM = 0; // calculate approx mintue
        approxT = 0; // calculate approx total time in mintues
        nextSet = "1-2";//next table set
        nowT = null;//now time into string
        EnterTime = null;
        
        nearestEnd = selectApprox(today,ns, period, "F");
        if(nearestEnd!=0){
            hr = Integer.toString(nearestEnd).substring(0, 2);
            min = Integer.toString(nearestEnd).substring(2, 4);
            nowT = Integer.toString(nH)+Integer.toString(nM);
            approxH = (Integer.parseInt(hr) - nH)*60;
            approxM = Integer.parseInt(min) - nM;
            approxT = approxH + approxM;
            
            if(approxT<0){
                approxT = approxT * -1;
            }
            
            EnterH = (approxH*60) + nH;
            EnterM = approxM + nM;
            if(EnterM>=60){
                EnterH = EnterH + 1;
                EnterM = EnterM - 60;
            }else if(EnterM>=120){
                EnterH = EnterH + 2;
                EnterM = EnterM - 120;
            }
            
            EnterTime = Integer.toString(EnterH) + Integer.toString(EnterM);
        }else{
            nowT = Integer.toString(nH)+Integer.toString(nM);
            EnterTime = nowT; //dont need to wait "0"
        }
    }
    
    public void QueueTable(){
        QueueList.getItems().clear();
        String n = null;
        String d = null;
        String t = null;
        int count = 0;
        
        try{
            FindPeriod();
            String QueueQuery = "SELECT * from queue WHERE Date  ='" + today + "' AND Period = '" + period + "' AND Inrest = '" + "F" + "'";
            ResultSet rs2 = Database.RetSet(QueueQuery);
            while (rs2.next()){
                n = rs2.getString("Name");
                d = rs2.getString("Diners");
                count = count + 1;
                t = count + ". " + n + ", queuing a table for "+ d + " diners.";
                QueueList.getItems().add(t);
            }
        }
        catch (Exception e){
            //System.out.println(e);
        } 
        Database.close();
    }
    
    public void AvaTable(){
        AvaList.getItems().clear();
        String diner_set = null;
        int set1 = 1;
        int set2 = 2;
        int count1 = 0;
        int count2 = 0;
        waitingT = 0; // count table_set rquired from queue
        
        try{
            FindPeriod();
            
            String TsetQuery = "SELECT Diner from table_Info";
            ResultSet rs1 = Database.RetSet(TsetQuery);
            while (rs1.next()){
                diner_set = rs1.getString("Diner");
                count1 = countQueueD(set1, today, period, "F");
                count2 = countQueueD(set2, today, period, "F");
                waitingT = count1 + count2;
                
                FindApproxTime(nextSet);
                
                AvaList.getItems().add("Table for "+ diner_set + ", group of waiting: " + waitingT + ", Approx. waiting time is: " + approxT +" mintues.");
                set1 = set1 + 2;
                set2 = set2 + 2;
                nextSet = Integer.toString(set1)+"-"+Integer.toString(set2);
            }
        }catch (Exception e){
        
        }
        Database.close();
    }
    
    public void clearField(){
        LName.clear();
        LPhone.clear();
        LDiner.clear();
        LQPhone.clear();
    }
    
    @FXML
    private void BJoinClicked(MouseEvent event) {
        String n = LName.getText();
        String p = LPhone.getText();
        String d = LDiner.getText();
        String set = null;
        
        if(n.equals("") || p.equals("") || d.equals("")){
            fx.AlertWarningwindow(null, null, "Please enter all the fields.");
        }else{
            FindPeriod();        
            if ( (Integer.parseInt(d) & 1) == 0 ) { 
                for(int i=0; i< Integer.parseInt(getMaxDiner()); i++){
                    if(Integer.parseInt(d) == i+1 || Integer.parseInt(d) == i+2){
                        set = Integer.toString(i) + "-" + Integer.toString(i+1);
                    }
                }
            } else { 
                for(int i=0; i< Integer.parseInt(getMaxDiner()); i++){
                    if(Integer.parseInt(d) == i+1 || Integer.parseInt(d) == i+2){
                        set = Integer.toString(i+1) + "-" + Integer.toString(i+2);
                    }
                }
            }
            
            FindApproxTime(set);
            queuetable(n,p,d,today,period,"F",Integer.parseInt(EnterTime));
            clearField();
            UpdateFinishedTable();
            RefreshQueue();
            QueueTable();
            AvaTable();
        }
    }

    @FXML
    private void BCancelClicked(MouseEvent event) {
        String qp = LQPhone.getText();
        if(qp.equals("")){
            fx.AlertWarningwindow(null, null, "Please enter your phone number that signed for the queue.");
        }else{
            removeQueue(qp);
            clearField();
            UpdateFinishedTable();
            RefreshQueue();
            QueueTable();
            AvaTable();
            fx.AlertInforwindow(null, null, "Cancelled.");
        }
    }
}
