/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RDBMSA;

import static RDBMSA.Database.addAccount;
import static RDBMSA.Database.updateAccount;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
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
import javafx.scene.control.Label;
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
    private Label AccountTitle;
    @FXML
    private AnchorPane SceneP;
    @FXML
    private JFXTextField TFname;
    @FXML
    private JFXTextField TLname;
    @FXML
    private JFXTextField TDob;
    @FXML
    private JFXTextField TPnumber;
    @FXML
    private JFXTextField TAddress;
    @FXML
    private JFXTextField TUname;
    @FXML
    private JFXPasswordField TPword;
    @FXML
    private JFXRadioButton RStaff;
    @FXML
    private JFXRadioButton RManager;
    @FXML
    private JFXButton BCancel;
    @FXML
    private JFXButton BCAccount;

    String role = RDBMSA.ManageController.ro;
    int sID = RDBMSA.ManageController.staffID;
    int accStage = RDBMSA.ManageController.accountStage;
    FxController fx = new FxController();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if(accStage == 0){
            AccountTitle.setText("Create an account");
            BCAccount.setText("Create account");
        }else if(accStage == 1){
            TFname.setText(RDBMSA.ManageController.fn);
            TLname.setText(RDBMSA.ManageController.ln);
            TDob.setText(RDBMSA.ManageController.date);
            TPnumber.setText(RDBMSA.ManageController.pnumber);
            TAddress.setText(RDBMSA.ManageController.address);
            TUname.setText(RDBMSA.ManageController.un);
            TPword.setText(RDBMSA.ManageController.pw);

            if (role.equals("M")){
                RManager.selectedProperty().setValue(Boolean.TRUE);
                RStaff.selectedProperty().setValue(Boolean.FALSE);
            } else{
                RManager.selectedProperty().setValue(Boolean.FALSE);
                RStaff.selectedProperty().setValue(Boolean.TRUE);
            }
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
        fx.textFV(TFname, "Enter first name.");
    }

    @FXML
    private void VLname(KeyEvent event) {
        fx.textFV(TLname, "Enter suranme");
    }

    @FXML
    private void VDob(KeyEvent event) {
        fx.textFV(TDob, "Enter date of birth");
    }

    @FXML
    private void VPNumber(KeyEvent event) {
        fx.numberFV(TPnumber, "Number's only");
    }

    @FXML
    private void VAddress(KeyEvent event) {
        fx.textFV(TAddress, "Enter address");
    }

    @FXML
    private void VUName(KeyEvent event) {
        fx.textFV(TUname, "Enter username");
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
        RDBMSA.ManageController.sceneID = 1;
        loadScenePane("Manage.fxml");
    }

    @FXML
    private void BUpdateClicked(MouseEvent event) {
        if(accStage == 0){
            addAccount(TFname.getText(),TLname.getText(),TDob.getText(),TPnumber.getText(),TAddress.getText(),TUname.getText(),TPword.getText(),role);
            fx.AlertInforwindow(null, null, "Account created.");
        }else if(accStage == 1){
            updateAccount(sID,TFname.getText(),TLname.getText(),TDob.getText(),TPnumber.getText(),TAddress.getText(),TUname.getText(),TPword.getText(),role);
            fx.AlertInforwindow(null, null, "Account updated.");
        }        
        RDBMSA.ManageController.sceneID = 1;
        loadScenePane("Manage.fxml");
    }

    @FXML
    private void validation(ActionEvent event) {
    }
}
