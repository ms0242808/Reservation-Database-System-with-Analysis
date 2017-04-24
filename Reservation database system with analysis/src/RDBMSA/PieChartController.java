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
        pieChartData.clear();
    }
    
    public void setMonthPieData(List<customerList> CList) {
        clearGraph();
       
        int[] monthCounter = new int[12];
        int tyr = RDBMSA.StatisticsController.tyr;

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
            
        for (int i = 0; i < monthCounter.length; i++) {
            pieChartData.addAll(new PieChart.Data(monthNames.get(i), monthCounter[i]));
            monthCounter[i] = 0;
        }
        
        pieChart.setData(pieChartData);
        pieChart.setTitle(tyr+" Diner booked");
        
        pieChartData.forEach(data ->
            data.nameProperty().bind(
                Bindings.concat(
                    data.getName(), " ", data.pieValueProperty().intValue()
                )
            )
        );
    }
    
    public void setDinerPieData(List<customerList> CList) {
        clearGraph();
        
        int records = countRecords();
        int[] monthCounter = new int[12];
        int[] dinerCounter = new int[records];
        int tyr = RDBMSA.StatisticsController.tyr;
        
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
  
        for (int i = 0; i < monthCounter.length; i++) {
            pieChartData.addAll(new PieChart.Data(monthNames.get(i), dinerCounter[i]));
            dinerCounter[i] = 0;
        }
        
        pieChart.setData(pieChartData);
        pieChart.setTitle(tyr+" Diner booked");
        
        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " ", data.pieValueProperty().intValue()
                        )
                )
        );
    }
}
