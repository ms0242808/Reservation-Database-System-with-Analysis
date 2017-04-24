/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RDBMSA;

import static RDBMSA.Database.countRecords;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author jerry
 */
public class BarChartController implements Initializable {
    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private Label LData;
    
    private ObservableList<String> monthNames = FXCollections.observableArrayList();
    String[] dateCounter = new String[31];
       
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        
        // Convert it to a list and add it to our ObservableList of months.
        monthNames.addAll(Arrays.asList(months));
        
        for(int i=0; i<dateCounter.length; i++){
            dateCounter[i] = Integer.toString(i+1);
        }
    }    
    
    public void clearGraph(){
        barChart.getData().clear();
    }
    
    public void setMonthBarData(List<customerList> CList) {
        // Count the number of people having their birthday in a specific month.
        clearGraph();
        // Assign the month names as categories for the horizontal axis.
        barChart.setTitle("Monthly booked");
        xAxis.setCategories(monthNames); 
        
        int[] monthCounter = new int[12];
        int tyr = RDBMSA.StatisticsController.tyr;
        int ylength = RDBMSA.StatisticsController.yearlength;
        XYChart.Series<String, Integer>[] series = Stream.<XYChart.Series<String, Integer>>generate(XYChart.Series::new).limit(20).toArray(XYChart.Series[]::new);
        
        for(int y = RDBMSA.StatisticsController.xy; y <= ylength; y++){
            try {
                String yearDinerQuery = "SELECT Date from customer WHERE Year = '" + tyr + "' ";
                ResultSet rs2 = Database.RetSet(yearDinerQuery);
                while (rs2.next()){
                    String str[] = rs2.getString("Date").split("/");
                    int month = Integer.parseInt(str[1]) - 1;
                    int count = 1 + monthCounter[month]++;
                }
            } catch (SQLException ex) {
                    //System.out.println(ex);
            }

            series[y].setName(Integer.toString(tyr));
  
            for (int i = 0; i < monthCounter.length; i++) {
                series[y].getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter[i]));
                monthCounter[i] = 0;
            }  
            
            barChart.getData().add(series[y]);

            for(final XYChart.Data<String, Integer> data : series[y].getData()){
                String labelname = series[y].getName();
                data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler(){
                    @Override
                    public void handle(Event event) {
                        String x = null;
                        x = "Year: "+ labelname + ", Month: " + data.getXValue() + ", Number of records: "+ String.valueOf(data.getYValue());
                        LData.setText(x);
                    }
                });    
            }
            tyr = tyr - 1;
        }
    }
    
    public void setDinerBarData(List<customerList> CList) {
        clearGraph();
        barChart.setTitle("Number of Diner booked");
        xAxis.setLabel("Diner");   
        
        int records = countRecords();
        int[] monthCounter = new int[12];
        int[] dinerCounter = new int[records];
        int tyr = RDBMSA.StatisticsController.tyr;
        int ylength = RDBMSA.StatisticsController.yearlength;
        XYChart.Series<String, Integer>[] series = Stream.<XYChart.Series<String, Integer>>generate(XYChart.Series::new).limit(20).toArray(XYChart.Series[]::new);
        
        for(int y = RDBMSA.StatisticsController.xy; y <= ylength; y++){
            try {
                String yearDinerQuery = "SELECT NumberOfDiner,Date from customer WHERE Year = '" + tyr + "' ";
                ResultSet rs2 = Database.RetSet(yearDinerQuery);
                    while (rs2.next()){
                        String strd[] = Integer.toString(rs2.getInt("NumberOfDiner")).split("/");
                        int diners = Integer.parseInt(strd[0]);

                        String strm[] = rs2.getString("Date").split("/");
                        int month = Integer.parseInt(strm[1]) - 1;

                        dinerCounter[month] += dinerCounter[diners] + diners;

                        monthCounter[month]++;
                    }
            } catch (SQLException ex) {
                //System.out.println(ex);
            }   

            series[y].setName(Integer.toString(tyr));
                    
            for (int i = 0; i < monthCounter.length; i++) {
                series[y].getData().add(new XYChart.Data<>(monthNames.get(i), dinerCounter[i]));
                dinerCounter[i] = 0;
            }
            
            barChart.getData().add(series[y]);

            for(final XYChart.Data<String, Integer> data : series[y].getData()){
                String labelname = series[y].getName();
                data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler(){
                    @Override
                    public void handle(Event event) {
                        String x;
                        x = "Year: "+ labelname + ", Month: " + data.getXValue() + ", Number of diners: "+ String.valueOf(data.getYValue());
                        LData.setText(x);
                    }
                });    
            }
            tyr = tyr - 1;
        }
    }
    
    public void setDatesBarData(List<customerList> CList) {
        clearGraph();
        barChart.setTitle("Diner booked ("+ Integer.toString(RDBMSA.StatisticsController.tyr) + ", " + RDBMSA.StatisticsController.cbdate+")");
        xAxis.setLabel("Number of records");       
        
        int records = countRecords();
        int[] dinerCounter = new int[records];
        
        int tyr = RDBMSA.StatisticsController.tyr;
        int ylength = RDBMSA.StatisticsController.yearlength;
        XYChart.Series<String, Integer>[] series = Stream.<XYChart.Series<String, Integer>>generate(XYChart.Series::new).limit(20).toArray(XYChart.Series[]::new);
        
        for(int y = RDBMSA.StatisticsController.xy; y <= ylength; y++){
            try {
                String yearDinerQuery = "SELECT NumberOfDiner,Date from customer WHERE Year = '" + tyr + "' ";
                ResultSet rs2 = Database.RetSet(yearDinerQuery);
                    while (rs2.next()){
                        String strd[] = Integer.toString(rs2.getInt("NumberOfDiner")).split("/");
                        int diners = Integer.parseInt(strd[0]);

                        String strm[] = rs2.getString("Date").split("/");
                        int month = Integer.parseInt(strm[1]);
                        
                        String strdt[] = rs2.getString("Date").split("/");
                        int dates = Integer.parseInt(strdt[0]) - 1;
          
                        if(month==RDBMSA.StatisticsController.months){
                            dinerCounter[dates] += dinerCounter[diners] + diners;
                        }

                        //System.out.println(dates + ": " + dinerCounter[dates]);
                    }
            } catch (SQLException ex) {
                //System.out.println(ex);
            }   

            series[y].setName(Integer.toString(tyr));
                    
            for (int i = 0; i < dateCounter.length; i++) {
                series[y].getData().add(new XYChart.Data<>(dateCounter[i], dinerCounter[i]));
                dinerCounter[i] = 0;
            }
            
            barChart.getData().add(series[y]);

            for(final XYChart.Data<String, Integer> data : series[y].getData()){
                String labelname = RDBMSA.StatisticsController.cbdate;
                data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler(){
                    @Override
                    public void handle(Event event) {
                        String x;
                        x = "Month: "+ labelname + ", Date: " + data.getXValue() + ", Number of diners: "+ String.valueOf(data.getYValue());
                        LData.setText(x);
                    }
                });    
            }
            tyr = tyr - 1;
        }
    }
}
