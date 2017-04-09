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
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

/**
 * FXML Controller class
 *
 * @author jerry
 */
public class PieChartController implements Initializable {
    @FXML
    private PieChart pieChart;

    private ObservableList<String> monthNames = FXCollections.observableArrayList();
    private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
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
        pieChartData.clear();
    }
    
    public void setMonthPieData(List<customerList> CList) {
        // Count the number of people having their birthday in a specific month.
        clearGraph();
        pieChart.setTitle("Monthly booked");
        int[] monthCounter = new int[12];
        int count = 0;
        for (customerList p : CList) {
            String str[] = p.getBdate().split("/");
            int month = Integer.parseInt(str[1]) - 1;
            count = 1 + monthCounter[month]++;
            //System.out.println(month + ": "+ count);
        }   

        // Create a XYChart.Data object for each month. Add it to the series.        
        for (int i = 0; i < monthCounter.length; i++) {
            //System.out.println(monthNames.get(i) + ":" + monthCounter[i]);
            pieChartData.addAll(new PieChart.Data(monthNames.get(i), monthCounter[i]));
        }
        pieChart.setData(pieChartData);
        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " ", data.pieValueProperty().intValue()
                        )
                )
        );
    }
    
    public void setDinerPieData(List<customerList> CList) {
        // Count the number of people having their birthday in a specific month.
        clearGraph();
        pieChart.setTitle("Diner booked");
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
        // Create a XYChart.Data object for each month. Add it to the series.    
        for (int i = 0; i < monthCounter.length; i++) {
            //System.out.println(monthNames.get(i) + ":" + dinerCounter[i]);
            pieChartData.addAll(new PieChart.Data(monthNames.get(i), dinerCounter[i]));
        }
        pieChart.setData(pieChartData);
        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " ", data.pieValueProperty().intValue()
                        )
                )
        );
    }
}
