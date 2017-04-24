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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

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
    @FXML
    private HBox TopFrame;
    @FXML
    private JFXButton BBack;
    @FXML
    private JFXButton BMinimize;
    @FXML
    private JFXButton BClose;
    
    public static int LogId = 0;
    FxController alertwindow = new FxController();
    AnimationGen animationGen = new AnimationGen();
    private static double xOffset = 0;
    private static double yOffset = 0;
 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void loadScenePane(String SceneName){
        try {
            RDBMSA.FxController.spane = SceneName;
            Parent root1 = FXMLLoader.load(getClass().getResource("Fx.fxml"));
            Scene pScene = new Scene(root1);
            RDBMSA.Generator.viewStage.setScene(pScene);
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
                RDBMSA.FxController.nameUser = "Welcome back! " + user;
                LogId = login;
                loadScenePane("Manage.fxml");
                animationGen.FadeAnimationOn(SceneP, 2000, 0f, 1.0f, null);
            }
        } catch(NullPointerException e){ 
            alertwindow.AlertWarningwindow(null, null, "Please enter correct username and password.");
        }
        
    }  

    @FXML
    private void BBackOnAction(ActionEvent event) {
        RDBMSA.FxController.spane = "Booking.fxml";
        try {
            Parent Bookingroot = FXMLLoader.load(getClass().getResource("Fx.fxml"));
            
            Scene bookingScene = new Scene(Bookingroot);
            RDBMSA.Generator.viewStage.setScene(bookingScene);
        } catch (IOException ex) {
            Logger.getLogger(FxController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void BMinimizeAction(ActionEvent event) {
        RDBMSA.Generator.viewStage.setIconified(true);
    }

    @FXML
    private void BCloseAction(ActionEvent event) {
        RDBMSA.Generator.viewStage.close();
    }

    @FXML
    private void TopFrameMouseDragged(MouseEvent event) {
        RDBMSA.Generator.viewStage.setX(event.getScreenX() + xOffset);
        RDBMSA.Generator.viewStage.setY(event.getScreenY() + yOffset);
    }

    @FXML
    private void TopFrameMousePressed(MouseEvent event) {
        xOffset = RDBMSA.Generator.viewStage.getX() - event.getScreenX();
        yOffset = RDBMSA.Generator.viewStage.getY() - event.getScreenY();
    }
}
