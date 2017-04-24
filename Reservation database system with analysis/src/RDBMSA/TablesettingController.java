/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RDBMSA;

import static RDBMSA.Database.settingMTime;
import static RDBMSA.Database.settingTSet;
import static RDBMSA.Database.updateTableInfo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
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
public class TablesettingController implements Initializable {
    @FXML
    private AnchorPane SceneP;
    @FXML
    private AnchorPane DetailTableP;
    @FXML
    private JFXComboBox<String> CBtablediner;
    @FXML
    private JFXTextField TFtableset;
    @FXML
    private JFXTextField TFmaxtime;
    @FXML
    private JFXButton BTableUpdate;

    FxController fx = new FxController();
    @FXML
    private JFXButton BTableBack;
    private JFXButton BTdinerAdd;
    private JFXButton BTdinerEdit;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        BTableBack.setText(null);
        BTdinerAdd.setText(null);
        BTdinerEdit.setText(null);
        try{
            String TableQuery = "SELECT Diner from table_Info";
            ResultSet rs2 = Database.RetSet(TableQuery);
            while (rs2.next()){
                String td = rs2.getString("Diner");
                CBtablediner.getItems().add(td);
            }
        }catch (Exception e){
            //System.out.println(e);
        } 
        Database.close();
    }    

    public void loadScenePane(String SceneName){
        try {
            Parent root1 = FXMLLoader.load(getClass().getResource(SceneName));
            SceneP.getChildren().setAll(root1);
        } catch (IOException ex) {
            Logger.getLogger(TablesettingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void CBtabledinerClicked(ActionEvent event) {
        TFtableset.setText(Integer.toString(settingTSet(CBtablediner.getValue())));
        TFmaxtime.setText(settingMTime(CBtablediner.getValue()));
    }

    @FXML
    private void BTableUpdateClicked(MouseEvent event) {
        String tableDiner = CBtablediner.getValue();
        int tableSet = Integer.parseInt(TFtableset.getText());
        String tableMaxtime = TFmaxtime.getText();
        updateTableInfo(tableDiner, tableSet, tableMaxtime);
        fx.AlertInforwindow(null, null, "Table setting updated.");
        CBtablediner.setValue("");
        TFtableset.setText(null);
        TFmaxtime.setText(null);
    }

    @FXML
    private void BTableBackClicked(MouseEvent event) {
        loadScenePane("Manage.fxml");
    }
}
