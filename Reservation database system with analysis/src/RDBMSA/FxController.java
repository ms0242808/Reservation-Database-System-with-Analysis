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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author jerry
 */
public class FxController implements Initializable {
    @FXML
    private AnchorPane MenuP;
    @FXML
    private MenuItem Customermenu;
    @FXML
    private MenuItem Managermenu;
    @FXML
    private MenuItem Exitmenu;
    @FXML
    private Menu GeneralMenu;
    @FXML
    private Menu AccountMenu;
    @FXML
    private MenuItem CRMenu;
    @FXML
    private MenuItem SDMenu;
    @FXML
    private Button CustomerTab;
    @FXML
    private Button EditTab;
    @FXML
    private Button ManagerTab;
    @FXML
    private AnchorPane GeneralP;
    @FXML
    private AnchorPane SceneP;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Exitmenu.setOnAction(actionEvent -> Platform.exit());
        loadCustomerPane();  
    }    

    public void loadCustomerPane(){
        try {
            Parent root1 = FXMLLoader.load(getClass().getResource("Booking.fxml"));
            SceneP.getChildren().setAll(root1);
        } catch (IOException ex) {
            Logger.getLogger(FxController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadEditBookedPane(){
        try {
            Parent root2 = FXMLLoader.load(getClass().getResource("EditBooking.fxml"));
            SceneP.getChildren().setAll(root2);
        } catch (IOException ex) {
            Logger.getLogger(FxController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public void loadManagePane(){
        try {
            Parent root3 = FXMLLoader.load(getClass().getResource("Manage.fxml"));
            SceneP.getChildren().setAll(root3);
        } catch (IOException ex) {
            Logger.getLogger(FxController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void CustomerMClicked(ActionEvent event) {       
        GeneralMenu.setText("Customer");
        loadCustomerPane();
    }

    @FXML
    private void ManagerMClicked(ActionEvent event) throws IOException {      
        GeneralMenu.setText("Manager/Staff");
        loadManagePane();
    }
    
    @FXML
    private void CRMenuClicked(ActionEvent event) {
        //DetailCustomerP.setVisible(true);
        //DetailAccountP.setVisible(false);
        //CustomerTable();
    }

    @FXML
    private void SDMenuClicked(ActionEvent event) {
        //DetailCustomerP.setVisible(false);
        //DetailAccountP.setVisible(true);
        //StaffListTable();
    }

    @FXML
    private void BCTabClicked(ActionEvent event) {
        loadCustomerPane();
    }

    @FXML
    private void BETabClicked(ActionEvent event) {
        loadEditBookedPane();
    }

    @FXML
    private void BMTabClicked(ActionEvent event) {
        loadManagePane();
    }
}