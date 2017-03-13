/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RDBMSA;

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
    
    private final ObservableList<customerList> CList = FXCollections.observableArrayList();;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO        
        try{
            String CustomerQuery = "SELECT * from customer";
            ResultSet rs2 = Database.RetSet(CustomerQuery);
            while (rs2.next()){
                CList.add(new customerList(rs2.getInt("CustomerID"), //doesnt need all of it
                                           rs2.getString("Firstname"),
                                           rs2.getString("Lastname"),
                                           rs2.getString("NumberOfDiner"),
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
    }
    
    @FXML
    private void BMonthClicked(MouseEvent event) {
    }

    @FXML
    private void BLineClicked(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LineChart.fxml"));
            Parent root = loader.load();
            LineChartController con = loader.getController();
            GraphP.getChildren().setAll(root);
            con.setMonthLineData(CList);
            //con.setDinerLineData(CList);
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
            con.setMonthBarData(CList);
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
            con.setMonthPieData(CList);
        } catch (IOException ex) {
            Logger.getLogger(StatisticsController.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
}
