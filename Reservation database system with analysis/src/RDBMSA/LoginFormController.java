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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jerry
 */
public class LoginFormController implements Initializable {
    @FXML
    private TextField Uname;
    @FXML
    private TextField Pword;
    @FXML
    private Button Blogin;
    @FXML
    private Button Bcancel;
    private static Connection connection = null;
    private static Statement statement;
    
    public static Stage customerStage; 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void BLoginClicked(MouseEvent event) {
        try{
            int user = 0;
            int pass = 0;
            user = userCheck(Uname.getText());
            pass = passwordCheck(user, Pword.getText());
            if (pass == 0){
                throw new NullPointerException();
                //AccountMenu.setVisible(true);
                //CustomerTable();
                //System.out.println("Success!");
            }
            else{
                System.out.println("Try again.");
            }   
        } catch(NullPointerException e){
            System.out.println("Catched");
        }
    }

    @FXML
    private void BCancelClicked(MouseEvent event) throws IOException {
        Parent root2 = FXMLLoader.load(getClass().getResource("fx.fxml"));       
        customerStage = new Stage();
        javafx.geometry.Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        customerStage.setScene(new Scene(root2));
     
        customerStage.setHeight(Generator.viewStage.getHeight());
        customerStage.setWidth(Generator.viewStage.getWidth());
        
        customerStage.show();
        customerStage.setResizable(false);
        Generator.viewStage.close();
    }
    
}
