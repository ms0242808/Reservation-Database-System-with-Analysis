/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RDBMSA;

import static RDBMSA.Database.passwordCheck;
import static RDBMSA.Database.userCheck;
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author jerry
 */
public class LoginController implements Initializable {
    @FXML
    private AnchorPane SceneP;
    @FXML
    private TextField TUname;
    @FXML
    private TextField TPword;
    @FXML
    private Button BCancel;
    @FXML
    private Button BLogin;

    public static int LogId = 0;
    
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
    
    public void loadBookingPane(){
        try {
            Parent root2 = FXMLLoader.load(getClass().getResource("Booking.fxml"));
            SceneP.getChildren().setAll(root2);
        } catch (IOException ex) {
            Logger.getLogger(FxController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void BCancelClicked(MouseEvent event) {
        loadBookingPane();
    }

    @FXML
    private void BLoginClicked(MouseEvent event) {
        String user = TUname.getText();
        String pass = TPword.getText();
        try{
            int login = 0;
            int loginPass = 0;            
            login = userCheck(user);
            loginPass = passwordCheck(login, pass);          
            if (loginPass < 0){
                LogId = login;
                loadManagePane();
            }
            else{
                //check if its failed to log in, alert
                System.out.println("Try again.");
            }   
        } catch(NullPointerException e){
            System.out.println("Catched");
        }
    }  
}
