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
        fx.textFV(TFname, "Enter first name.");
    }

    @FXML
    private void VLname(KeyEvent event) {
        fx.textFV(TLname, "Enter surname.");
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
        if(RDBMSA.ManageController.sceneID == 0){
            RDBMSA.ManageController.sceneID = 0;
        }else if(RDBMSA.ManageController.sceneID == 2){
            RDBMSA.ManageController.sceneID = 2;
        }
        String pd = "lunch";
        String tp[] = time.toString().split(":");
        int periodH = Integer.parseInt(tp[0]); // hr
        int periodM = Integer.parseInt(tp[1]); // min
        int aid = 0;
        if(periodH <= 16 && periodH >= 10){
            pd = "lunch";
        } else if(periodH <= 23 && periodH >= 17){
            pd = "dinner";
        }
        updateBooking(cID,fname,lname,diner,dt,time,ph,em,sr,pd);
        fx.AlertInforwindow(null, null, "Booking record updated.");
        loadScenePane("Manage.fxml");
    }

    @FXML
    private void validation(ActionEvent event) {
    }

    @FXML
    private void VPhone(KeyEvent event) {
        fx.numberFV(TPhone, "Number's only");
    }

    @FXML
    private void VEmail(KeyEvent event) {
        fx.textFV(TEmail, "Enter email");
    }
    
}
