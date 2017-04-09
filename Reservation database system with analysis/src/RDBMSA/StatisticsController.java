/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RDBMSA;

import static RDBMSA.Database.avgDiner;
import static RDBMSA.Database.maxDiner;
import static RDBMSA.Database.minDiner;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author jerry
 */
public class StatisticsController implements Initializable {
    @FXML
    private AnchorPane SceneP;
    @FXML
    private AnchorPane GraphP;
    @FXML
    private JFXButton BArea;
    @FXML
    private JFXButton BTime;
    @FXML
    private Button BLine;
    @FXML
    private Button BBar;
    @FXML
    private Button BPie;
    @FXML
    private Button BDiner;
    @FXML
    private Button BMonth;
    @FXML
    private Label LMDiner;
    @FXML
    private Label LLDiner;
    @FXML
    private Label LADiner;
    @FXML
    private Label LMTime;
    @FXML
    private Label LLTime;
    @FXML
    private Label LATime;
    @FXML
    private Label LMMonth;
    @FXML
    private Label LLMonth;
    @FXML
    private Label LAMonth;
    @FXML
    private Button BBack;
    
    private final ObservableList<customerList> CList = FXCollections.observableArrayList();;
    int stage = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        BArea.setVisible(false);
        
        LMDiner.setText("Max. diners: " + Integer.toString(maxDiner()));
        LLDiner.setText("Min. diners: " + Integer.toString(minDiner()));
        LADiner.setText("Avg. diners: " + Integer.toString(avgDiner()));
        
        try{
            String CustomerQuery = "SELECT * from customer";
            ResultSet rs2 = Database.RetSet(CustomerQuery);
            while (rs2.next()){
                CList.add(new customerList(rs2.getInt("CustomerID"), //doesnt need all of it
                                           rs2.getString("Firstname"),
                                           rs2.getString("Lastname"),
                                           rs2.getInt("NumberOfDiner"),
                                           rs2.getString("Date"),
                                           rs2.getString("Time"),
                                           rs2.getString("Phone"),
                                           rs2.getString("Email"),
                                           rs2.getString("AdditionalRequest"),
                                           rs2.getString("PreOrder"),
                                           rs2.getString("ConfirmCode")));
            }
        } catch(Exception e){
            //System.out.println(e);
        } 
    }

    @FXML
    private void BDinerClicked(MouseEvent event) {
        stage = 1;
        BLine.setVisible(true);
        BBar.setVisible(true);
        BPie.setVisible(true);
        BArea.setVisible(false);
    }
    
    @FXML
    private void BMonthClicked(MouseEvent event) {
        stage = 2;
        BLine.setVisible(true);
        BBar.setVisible(true);
        BPie.setVisible(true);
        BArea.setVisible(false);
    }

    @FXML
    private void BTimeClicked(MouseEvent event) {
        stage = 3;
        BLine.setVisible(false);
        BBar.setVisible(false);
        BPie.setVisible(false);
        BArea.setVisible(true);
    }
    
    @FXML
    private void BLineClicked(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LineChart.fxml"));
            Parent root = loader.load();
            LineChartController con = loader.getController();
            GraphP.getChildren().setAll(root);
            if(stage == 1){
                con.setDinerLineData(CList);
            }else if(stage == 2){
                con.setMonthLineData(CList);
            }
        } catch (IOException ex) {
            Logger.getLogger(StatisticsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void BBarClicked(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BarChart.fxml"));
            Parent root = loader.load();
            BarChartController con = loader.getController();
            GraphP.getChildren().setAll(root);
            if(stage == 1){
                con.setDinerBarData(CList);
            }else if(stage == 2){
                con.setMonthBarData(CList);
            }
        } catch (IOException ex){
            Logger.getLogger(StatisticsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void BPieClicked(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PieChart.fxml"));
            Parent root = loader.load();
            PieChartController con = loader.getController();
            GraphP.getChildren().setAll(root);
            if(stage == 1){
                con.setDinerPieData(CList);
            }else if(stage == 2){
                con.setMonthPieData(CList);
            }
        } catch (IOException ex) {
            Logger.getLogger(StatisticsController.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }

    @FXML
    private void BAreaClicked(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AreaChart.fxml"));
            Parent root = loader.load();
            AreaChartController con = loader.getController();
            GraphP.getChildren().setAll(root);
            if(stage == 3){
                con.setTimeAreaData(CList);
            }
        } catch (IOException ex) {
            Logger.getLogger(StatisticsController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    @FXML
    private void BBackClicked(MouseEvent event) {
        RDBMSA.ManageController.sceneID = 0;
        try {
            Parent root1 = FXMLLoader.load(getClass().getResource("Manage.fxml"));
            SceneP.getChildren().setAll(root1);
        } catch (IOException ex) {
            Logger.getLogger(StatisticsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
