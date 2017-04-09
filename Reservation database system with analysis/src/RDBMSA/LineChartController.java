/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RDBMSA;

import static RDBMSA.Database.countRecords;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author jerry
 */
public class LineChartController implements Initializable {
    @FXML
    private LineChart<String, Integer> lineChart;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private CategoryAxis xAxis;

    private ObservableList<String> monthNames = FXCollections.observableArrayList();
    @FXML
    private Label LData;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        // Convert it to a list and add it to our ObservableList of months.
        monthNames.addAll(Arrays.asList(months));
    }    
    
    public void clearGraph(){
        lineChart.getData().clear();
    }
    
    public void setMonthLineData(List<customerList> CList) {
        // Count the number of people having their birthday in a specific month.
        clearGraph();
        lineChart.setTitle("Monthly booked");
        xAxis.setLabel("Month");   
        //yAxis.setLabel("Value");
        xAxis.setCategories(monthNames);
        int[] monthCounter = new int[12];
        int count = 0;
        for (customerList p : CList) {
            String str[] = p.getBdate().split("/");
            int month = Integer.parseInt(str[1]) - 1;
            count = 1 + monthCounter[month]++;
            //System.out.println(month + ": "+ count);
        }   
        
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Number of records");
        // Create a XYChart.Data object for each month. Add it to the series.        
        for (int i = 0; i < monthCounter.length; i++) {
            //System.out.println(monthNames.get(i) + ":" + monthCounter[i]);
            series.getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter[i]));
        }     
        lineChart.getData().add(series);
        
        for(final XYChart.Data<String, Integer> data : series.getData()){
            data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler(){
                @Override
                public void handle(Event event) {
                    String x;
                    x = "Month: " + data.getXValue() + ", Number of records: "+ String.valueOf(data.getYValue());
                    LData.setText(x);
                }
            });    
        }
    }
    
    public void setDinerLineData(List<customerList> CList) {
        // Count the number of people having their birthday in a specific month.
        clearGraph();
        lineChart.setTitle("Diner booked");
        xAxis.setLabel("Number of records");       
        //yAxis.setLabel("Value");
        
        int records = countRecords();
        int[] monthCounter = new int[12];
        int[] dinerCounter = new int[records];
        
        for (customerList p : CList) {
            String strd[] = Integer.toString(p.getNumberofdiner()).split("/");
            int diners = Integer.parseInt(strd[0]);
            
            String strm[] = p.getBdate().split("/");
            int month = Integer.parseInt(strm[1]) - 1;

            dinerCounter[month] += dinerCounter[diners] + diners;
           
            monthCounter[month]++;
            //System.out.println(month+": "+dinerCounter[month] );
        }   
        
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("2017");
        
        // Create a XYChart.Data object for each month. Add it to the series.        
        for (int i = 0; i < monthCounter.length; i++) {
            //System.out.println(monthNames.get(i) + ":" + dinerCounter[i]);
            series.getData().add(new XYChart.Data<>(monthNames.get(i), dinerCounter[i]));
        }
        lineChart.getData().add(series);

        for(final XYChart.Data<String, Integer> data : series.getData()){
            data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler(){
                @Override
                public void handle(Event event) {
                    String x;
                    x = "Month: " + data.getXValue() + ", Number of diners: "+ String.valueOf(data.getYValue());
                    LData.setText(x);
                }
            });    
        }
    }
}
