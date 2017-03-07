/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RDBMSA;

import static RDBMSA.Database.updateAccount;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author jerry
 */
public class UpdateAccountController implements Initializable {
    @FXML
    private AnchorPane SceneP;
    @FXML
    private TextField TFname;
    @FXML
    private TextField TLname;
    @FXML
    private TextField TDob;
    @FXML
    private TextField TPnumber;
    @FXML
    private TextField TAddress;
    @FXML
    private TextField TUname;
    @FXML
    private PasswordField TPword;
    @FXML
    private RadioButton RStaff;
    @FXML
    private RadioButton RManager;
    @FXML
    private Button BCancel;
    @FXML
    private Button BCAccount;

    String role = RDBMSA.ManageController.staffr;
    public static int UAscene = 0;
    int sID = RDBMSA.ManageController.staffID;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        TFname.setText(RDBMSA.ManageController.stafffn);
        TLname.setText(RDBMSA.ManageController.staffln);
        TDob.setText(RDBMSA.ManageController.staffdob);
        TPnumber.setText(Integer.toString(RDBMSA.ManageController.staffphone));
        TAddress.setText(RDBMSA.ManageController.staffaddress);
        TUname.setText(RDBMSA.ManageController.staffun);
        TPword.setText(RDBMSA.ManageController.staffpw);
        
        if (role.equals("M")){
            RManager.selectedProperty().setValue(Boolean.TRUE);
            RStaff.selectedProperty().setValue(Boolean.FALSE);
        } else{
            RManager.selectedProperty().setValue(Boolean.FALSE);
            RStaff.selectedProperty().setValue(Boolean.TRUE);
        }
    }    

    public void loadScenePane(String SceneName){
        try {
            Parent root1 = FXMLLoader.load(getClass().getResource(SceneName));
            SceneP.getChildren().setAll(root1);
        } catch (IOException ex) {
            Logger.getLogger(UpdateAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void VFname(KeyEvent event) {
    }

    @FXML
    private void VLname(KeyEvent event) {
    }

    @FXML
    private void VDob(KeyEvent event) {
    }

    @FXML
    private void VPNumber(KeyEvent event) {
    }

    @FXML
    private void VAddress(KeyEvent event) {
    }

    @FXML
    private void VUName(KeyEvent event) {
    }

    @FXML
    private void VPWord(KeyEvent event) {
    }

    @FXML
    private void RStaffClicked(MouseEvent event) {
        RManager.selectedProperty().setValue(Boolean.FALSE);
        role = "S";
    }

    @FXML
    private void RManagerClicked(MouseEvent event) {
        RStaff.selectedProperty().setValue(Boolean.FALSE);
        role = "M";
    }

    @FXML
    private void BCancelClicked(MouseEvent event) {
        UAscene = 1;
        loadScenePane("Manage.fxml");
    }

    @FXML
    private void BUpdateClicked(MouseEvent event) {
        updateAccount(sID,TFname.getText(),TLname.getText(),TDob.getText(),TPnumber.getText(),TAddress.getText(),TUname.getText(),TPword.getText(),role);
        UAscene = 1;
        loadScenePane("Manage.fxml");
    }

    @FXML
    private void validation(ActionEvent event) {
    }
}
