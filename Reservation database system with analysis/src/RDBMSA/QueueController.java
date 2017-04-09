/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RDBMSA;

import static RDBMSA.Database.queuetable;
import static RDBMSA.Database.removeQueue;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
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

    private final ObservableList QList = FXCollections.observableArrayList();
    FxController fx = new FxController();
    
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
        
        QueueTable();
    }

    public void QueueTable(){
        QueueList.getItems().clear();
        String n = null;
        String d = null;
        String t = null;
        int count = 0;
        try{
            String QueueQuery = "SELECT * from queue";
            ResultSet rs2 = Database.RetSet(QueueQuery);
            while (rs2.next()){
                n = rs2.getString("Name");
                d = rs2. getString("Diners");
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
        if(n.equals("") || p.equals("") || d.equals("")){
            fx.AlertWarningwindow(null, null, "Please enter all the fields.");
        }else{
            queuetable(n,p,d);
            clearField();
            QueueTable();
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
            QueueTable();
            fx.AlertInforwindow(null, null, "Cancelled.");
        }
    }
}
