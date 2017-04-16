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
import javafx.stage.StageStyle;

/**
 *
 * @author Chieh-Yu Chou
 */
public class Generator extends Application {

    public static Stage viewStage;    
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        viewStage = primaryStage;
        Parent root_booking = FXMLLoader.load(getClass().getResource("Fx.fxml"));
        Scene Bookingscene = new Scene(root_booking);
        viewStage.setScene(Bookingscene);
        viewStage.setResizable(false);
        viewStage.initStyle(StageStyle.UNDECORATED);
        viewStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
