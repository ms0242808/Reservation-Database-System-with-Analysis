/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RDBMSA;

import static RDBMSA.Database.updateBooking;
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
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    private TextField TFname;
    @FXML
    private TextField TLname;
    @FXML
    private TextField TNDiner;
    @FXML
    private TextField TDate;
    @FXML
    private TextField TTime;
    @FXML
    private TextField TPhone;
    @FXML
    private TextField TEmail;
    @FXML
    private TextArea TSRequest;
    @FXML
    private TextArea TPOrder;
    @FXML
    private Button BCancel;
    @FXML
    private Button BUpdate;

    int cID = RDBMSA.ManageController.customerID;
    FxController alertwindow = new FxController();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        TFname.setText(RDBMSA.ManageController.fn);
        TLname.setText(RDBMSA.ManageController.ln);
        TNDiner.setText(RDBMSA.ManageController.diner);
        TDate.setText(RDBMSA.ManageController.date);
        TTime.setText(RDBMSA.ManageController.ctime);
        TPhone.setText(Integer.toString(RDBMSA.ManageController.phone));
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
        RDBMSA.ManageController.sceneID = 0;
        loadScenePane("Manage.fxml");
    }

    @FXML
    private void BUpdateClicked(MouseEvent event) {
        updateBooking(cID,TFname.getText(),TLname.getText(),TNDiner.getText(),TDate.getText(),TTime.getText(),TPhone.getText(),TEmail.getText(),TSRequest.getText(),TPOrder.getText());
        RDBMSA.ManageController.sceneID = 0;
        alertwindow.AlertInforwindow(null, null, "Booking record updated.");
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
