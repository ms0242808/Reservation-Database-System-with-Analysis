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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author jerry
 */
public class FxController implements Initializable {
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
        //Exitmenu.setOnAction(actionEvent -> Platform.exit());
        loadScenePane("Booking.fxml");
        //CustomerTab.setContentDisplay(ContentDisplay.TOP); set image above the text
    }    

    public void loadScenePane(String SceneName){
        try {
            Parent root1 = FXMLLoader.load(getClass().getResource(SceneName));
            SceneP.getChildren().setAll(root1);
        } catch (IOException ex) {
            Logger.getLogger(FxController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void AlertWarningwindow(String title, String header, String mesg){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(mesg);
        alert.showAndWait();
    }
    
    public void AlertInforwindow(String title, String header, String mesg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(mesg);
        alert.showAndWait();
    }
    
    @FXML
    private void BCTabClicked(ActionEvent event) {
        loadScenePane("Booking.fxml");
    }

    @FXML
    private void BETabClicked(ActionEvent event) {
        loadScenePane("EditBooking.fxml");
    }

    @FXML
    private void BMTabClicked(ActionEvent event) {
        loadScenePane("Login.fxml");
    }
}