/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RDBMSA;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Chieh-Yu Chou
 */
public class Generator extends Application {

    public static Stage viewStage;    
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        viewStage = primaryStage;
        Parent root_friend = FXMLLoader.load(getClass().getResource("fx.fxml"));
        Scene friendListscene = new Scene(root_friend);
        viewStage.setScene(friendListscene);
        viewStage.setResizable(false);
        viewStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
