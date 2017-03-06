/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RDBMSA;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author jerry
 */
public class AddAccountController implements Initializable {
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
    private TextField TPword;
    @FXML
    private RadioButton RStaff;
    @FXML
    private RadioButton RManager;
    @FXML
    private Button BCancel;
    @FXML
    private Button BCAccount;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void loadManagePane(){
        try {
            Parent root1 = FXMLLoader.load(getClass().getResource("Manage.fxml"));
            SceneP.getChildren().setAll(root1);
        } catch (IOException ex) {
            Logger.getLogger(FxController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    @FXML
    private void RStaffClicked(MouseEvent event) {
        RManager.selectedProperty().setValue(Boolean.FALSE);
    }

    @FXML
    private void RManagerClicked(MouseEvent event) {
        RStaff.selectedProperty().setValue(Boolean.FALSE);
    }

    @FXML
    private void BCancelClicked(MouseEvent event) {
        loadManagePane();
    }

    @FXML
    private void BCreateClicked(MouseEvent event) {
    }
    
}
