/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RDBMSA;

import static RDBMSA.Database.passwordCheck;
import static RDBMSA.Database.userCheck;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
    private JFXTextField TUname;
    @FXML
    private JFXPasswordField TPword;
    @FXML
    private JFXButton BCancel;
    @FXML
    private JFXButton BLogin;
    
    public static int LogId = 0;
    FxController alertwindow = new FxController();
    AnimationGen animationGen = new AnimationGen();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void loadScenePane(String SceneName){
        try {
            Parent root1 = FXMLLoader.load(getClass().getResource(SceneName));
            SceneP.getChildren().setAll(root1);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void BCancelClicked(MouseEvent event) {
        loadScenePane("Booking.fxml");
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
            if (loginPass == -1){
                LogId = login;
                loadScenePane("Manage.fxml");
                animationGen.FadeAnimationOn(SceneP, 2000, 0f, 1.0f, null);
            }
        } catch(NullPointerException e){ 
            alertwindow.AlertWarningwindow(null, null, "Please enter correct username and password.");
            //effect on textfiled 
        }
    }  
}
