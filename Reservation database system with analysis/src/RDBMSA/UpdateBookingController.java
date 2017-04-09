/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RDBMSA;

import static RDBMSA.Database.updateBooking;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author jerry
 */
public class UpdateBookingController implements Initializable {
    @FXML
    private AnchorPane SceneP;
    @FXML
    private JFXTextField TFname;
    @FXML
    private JFXTextField TLname;
    @FXML
    private JFXTextField TNDiner;
    @FXML
    private JFXTextField TDate;
    @FXML
    private JFXTextField TTime;
    @FXML
    private JFXTextField TPhone;
    @FXML
    private JFXTextField TEmail;
    @FXML
    private JFXTextArea TSRequest;
    @FXML
    private JFXTextArea TPOrder;
    @FXML
    private JFXButton BCancel;
    @FXML
    private JFXButton BUpdate;

    int cID = RDBMSA.ManageController.customerID;
    FxController fx = new FxController();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        TFname.setText(RDBMSA.ManageController.fn);
        TLname.setText(RDBMSA.ManageController.ln);
        TNDiner.setText(Integer.toString(RDBMSA.ManageController.diner));
        TDate.setText(RDBMSA.ManageController.date);
        TTime.setText(RDBMSA.ManageController.ctime);
        TPhone.setText(RDBMSA.ManageController.pnumber);
        TEmail.setText(RDBMSA.ManageController.cemail);
        TSRequest.setText(RDBMSA.ManageController.srequest);
        TPOrder.setText(RDBMSA.ManageController.porder);
    }    

    public void loadScenePane(String SceneName){
        try {
            Parent root1 = FXMLLoader.load(getClass().getResource(SceneName));
            SceneP.getChildren().setAll(root1);
        } catch (IOException ex) {
            Logger.getLogger(UpdateBookingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void VFname(KeyEvent event) {
    }

    @FXML
    private void VLname(KeyEvent event) {
    }


    @FXML
    private void BCancelClicked(MouseEvent event) {
        loadScenePane("Manage.fxml");
    }

    @FXML
    private void BUpdateClicked(MouseEvent event) {
        String fname = TFname.getText();
        String lname = TLname.getText();
        int diner = Integer.parseInt(TNDiner.getText());
        String dt = TDate.getText();
        String time = TTime.getText();
        String ph = TPhone.getText();
        String em = TEmail.getText();
        String sr = TSRequest.getText();
        String po = TPOrder.getText();
        if(RDBMSA.ManageController.sceneID == 0){
            RDBMSA.ManageController.sceneID = 0;
        }else if(RDBMSA.ManageController.sceneID == 2){
            RDBMSA.ManageController.sceneID = 2;
        }
        updateBooking(cID,fname,lname,diner,dt,time,ph,em,sr,po);
        fx.AlertInforwindow(null, null, "Booking record updated.");
        loadScenePane("Manage.fxml");
    }

    @FXML
    private void validation(ActionEvent event) {
    }

    @FXML
    private void VPhone(KeyEvent event) {
    }

    @FXML
    private void VEmail(KeyEvent event) {
    }
    
}
