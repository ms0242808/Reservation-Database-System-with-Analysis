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
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

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
    private JFXButton BLine;
    @FXML
    private JFXButton BBar;
    @FXML
    private JFXButton BPie;
    @FXML
    private JFXButton BDiner;
    @FXML
    private JFXButton BMonth;
    @FXML
    private Label LMDiner;
    @FXML
    private Label LLDiner;
    @FXML
    private Label LADiner;
    @FXML
    private JFXButton BBack;
    
    private final ObservableList<customerList> CList = FXCollections.observableArrayList();;
    int stage = 0;
    int chartstage = 0;
    public static int yearlength = 0;
    public static int xy = 0;
    public static Date todays_date = new Date();
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy");
    public static int tyr;
    public static int months = 1;
    public static String cbdate = null;
    String[] dname = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    FxController fx = new FxController();
    
    @FXML
    private JFXButton BPyr;
    @FXML
    private JFXButton BCyr;
    @FXML
    private JFXButton BAyr;
    @FXML
    private JFXButton BPyr1;
    @FXML
    private JFXTextField TFBeforY;
    @FXML
    private JFXButton BBeforeG;
    @FXML
    private JFXTextField TFFromY;
    @FXML
    private JFXTextField TFToY;
    @FXML
    private JFXButton BFTG;
    @FXML
    private JFXComboBox<String> CBDates;
    @FXML
    private JFXButton BDateG;
    @FXML
    private HBox MonthBox;
    @FXML
    private ButtonBar CategoryBar;
    @FXML
    private ButtonBar ChartBar;
    @FXML
    private ButtonBar YearBar;
    @FXML
    private HBox SingleYear;
    @FXML
    private HBox MutilYear;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ChartBar.setDisable(true);
        YearBar.setDisable(true);
        SingleYear.setDisable(true);
        MutilYear.setDisable(true);
        BArea.setDisable(true);
        BBeforeG.setDisable(true);
        BFTG.setDisable(true);
        TFToY.setDisable(true);
        MonthBox.setDisable(true);
        CBDates.getItems().addAll(dname);
        
        tyr = Integer.parseInt(dateFormat.format(todays_date));
        BPyr1.setText(Integer.toString(tyr - 2));
        BPyr.setText(Integer.toString(tyr - 1));
        BCyr.setText(Integer.toString(tyr));
        BAyr.setText(Integer.toString(tyr - 2)+" - "+Integer.toString(tyr));
        
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

    public void lineC(){
        chartstage = 0;
        MutilYear.setDisable(false);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LineChart.fxml"));
            Parent root = loader.load();
            LineChartController con = loader.getController();
            GraphP.getChildren().setAll(root);
            if(stage == 1){
                con.setDinerLineData(CList);
            }else if(stage == 2){
                con.setMonthLineData(CList);
            }else if(stage == 4){
                con.setDatesLineData(CList);
            }
        } catch (IOException ex) {
            Logger.getLogger(StatisticsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void barC(){
        chartstage = 1;
        MutilYear.setDisable(false);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BarChart.fxml"));
            Parent root = loader.load();
            BarChartController con = loader.getController();
            GraphP.getChildren().setAll(root);
            if(stage == 1){
                con.setDinerBarData(CList);
            }else if(stage == 2){
                con.setMonthBarData(CList);
            }else if(stage == 4){
                con.setDatesBarData(CList);
            }
        } catch (IOException ex){
            Logger.getLogger(StatisticsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void pieC(){
        chartstage = 2;
        MutilYear.setDisable(true);
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
    
    public void areaC(){
        chartstage = 3;
        MutilYear.setDisable(false);
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
    
    public void loadC(){
        if(chartstage == 0){
            lineC();
        } else if(chartstage == 1){
            barC();
        } else if(chartstage == 2){
            pieC();
        }else if(chartstage == 3){
            areaC();
        }
    }
    
    @FXML
    private void BDinerClicked(MouseEvent event) {
        stage = 1;
        ChartBar.setDisable(false);
        YearBar.setDisable(false);
        SingleYear.setDisable(false);
        MutilYear.setDisable(false);
        BLine.setDisable(false);
        BBar.setDisable(false);
        BPie.setDisable(false);
        BArea.setDisable(true);
        MonthBox.setDisable(false);
        BDateG.setDisable(true);
        loadC();
    }
    
    @FXML
    private void BMonthClicked(MouseEvent event) {
        stage = 2;
        ChartBar.setDisable(false);
        YearBar.setDisable(false);
        SingleYear.setDisable(false);
        MutilYear.setDisable(false);
        BLine.setDisable(false);
        BBar.setDisable(false);
        BPie.setDisable(false);
        BArea.setDisable(true);
        MonthBox.setDisable(true);
        loadC();
    }

    @FXML
    private void BTimeClicked(MouseEvent event) {
        stage = 3;
        ChartBar.setDisable(false);
        YearBar.setDisable(false);
        SingleYear.setDisable(false);
        MutilYear.setDisable(false);
        BAyr.setDisable(false);
        BLine.setDisable(true);
        BBar.setDisable(true);
        BPie.setDisable(true);
        BArea.setDisable(false);
        MonthBox.setDisable(true);
        loadC();
    }
    
    @FXML
    private void BLineClicked(MouseEvent event) {
        BAyr.setVisible(true);
        lineC();
    }

    @FXML
    private void BBarClicked(MouseEvent event) {
        BAyr.setVisible(true);
        barC();
    }
    
    @FXML
    private void BPieClicked(MouseEvent event) {
        BAyr.setVisible(false);
        pieC();
    }

    @FXML
    private void BAreaClicked(MouseEvent event) {
        BAyr.setVisible(true);
        areaC();
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

    @FXML
    private void BPyr1Clicked(MouseEvent event) {
        xy = 2;
        yearlength = 2;
        tyr = Integer.parseInt(dateFormat.format(todays_date)) - 2;
        loadC();
    }
    
    @FXML
    private void BPyrClicked(MouseEvent event) {
        xy = 1;
        yearlength = 1;
        tyr = Integer.parseInt(dateFormat.format(todays_date)) - 1;
        loadC();
    }

    @FXML
    private void BCyrClicked(MouseEvent event) {
        xy =  0; // starts from
        yearlength = 0; // ends at
        tyr = Integer.parseInt(dateFormat.format(todays_date)); // year to store
        loadC();
    }

    @FXML
    private void BAyrClicked(MouseEvent event) {
        xy = 0;
        yearlength = 2;
        tyr = Integer.parseInt(dateFormat.format(todays_date));
        loadC();
    }    

    @FXML
    private void TFBeforYClicked(KeyEvent event) {
        BBeforeG.setDisable(true);
        fx.numberFV(TFBeforY, "e.g 2014");
        if(Boolean.TRUE.equals(TFBeforY.validate()) && TFBeforY.getLength() == 4){
            if(Integer.parseInt(TFBeforY.getText()) > Integer.parseInt(dateFormat.format(todays_date))){
                BBeforeG.setDisable(true);
            }else{
                BBeforeG.setDisable(false);
            }
        }else{
            BBeforeG.setDisable(true);
        }    
    }

    @FXML
    private void BBeforeGClicked(MouseEvent event) {
        String before = TFBeforY.getText();
        tyr = Integer.parseInt(before);
        xy = Integer.parseInt(dateFormat.format(todays_date)) - Integer.parseInt(before);
        yearlength = xy;
        loadC();
    }

    @FXML
    private void TFFromYClicked(KeyEvent event) {
        TFToY.setDisable(true);
        fx.numberFV(TFFromY, "e.g 2014");
        if(Boolean.TRUE.equals(TFFromY.validate()) && TFFromY.getLength() == 4){
            if(Integer.parseInt(TFFromY.getText()) > Integer.parseInt(dateFormat.format(todays_date))){
                TFToY.setDisable(true);
            }else{
                TFToY.setDisable(false);
            }
        }else{
            TFToY.setDisable(true);
        }
    }

    @FXML
    private void TFToYClicked(KeyEvent event) {
        BFTG.setDisable(true);
        fx.numberFV(TFToY, "<= current year.");
        if(Boolean.TRUE.equals(TFToY.validate()) && TFToY.getLength() == 4 && Integer.parseInt(TFToY.getText()) > Integer.parseInt(TFFromY.getText())){
            if(Integer.parseInt(TFToY.getText()) > Integer.parseInt(dateFormat.format(todays_date))){
                BFTG.setDisable(true);
            }else{
                BFTG.setDisable(false);
            }
        }else{
            BFTG.setDisable(true);
        }
    }

    @FXML
    private void BFTGClicked(MouseEvent event) {
        String startYr = TFFromY.getText();
        String endYr = TFToY.getText();
        tyr = Integer.parseInt(endYr);
        xy = Integer.parseInt(dateFormat.format(todays_date)) - Integer.parseInt(endYr);
        if(Integer.parseInt(endYr) == Integer.parseInt(dateFormat.format(todays_date))){
            yearlength = Integer.parseInt(endYr) - Integer.parseInt(startYr);
        }else {
            yearlength = Integer.parseInt(endYr) - Integer.parseInt(startYr) + 1;
        }
        loadC();
    }

    @FXML
    private void BDateGClicked(MouseEvent event) {
        cbdate = CBDates.getValue();
        for(int i = 0; i<=11; i++){
            if(cbdate.equals(dname[i])){
                months = 1 + i;
            }
        }
        stage = 4;
        loadC();
    }

    @FXML
    private void CBDatesClicked(ActionEvent event) {
        if(CBDates.getValue().isEmpty()){
            BDateG.setDisable(true);
        }else{
            BDateG.setDisable(false);
        }    
    }

    @FXML
    private void TFBeforYMouseClicked(MouseEvent event) {
        TFBeforY.setText(null);
    }

    @FXML
    private void TFFromYMouseClicked(MouseEvent event) {
        TFFromY.setText(null);
    }

    @FXML
    private void TFToYMouseClicked(MouseEvent event) {
        TFToY.setText(null);
    }
}
