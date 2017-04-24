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
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author jerry
 */
public class AreaChartController implements Initializable {
    @FXML
    private AreaChart<Integer, Integer> areaChart;
    @FXML
    private Label LData;
    
    String[] timeCounter = new String[24];
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        String x = null;
        for(int i=0; i<timeCounter.length; i++){
            x = Integer.toString(i) + ":00 - " + Integer.toString(i) + ":59";
            timeCounter[i] = x;
        }
    }    
    
    public void clearGraph(){
        areaChart.getData().clear();
    }
    
    public void setTimeAreaData(List<customerList> CList) {
        clearGraph();    
        
        int records = countRecords();
        int[] dinerCounter = new int[records];
        String[] yearCounter = new String[50];
        int tyr = RDBMSA.StatisticsController.tyr;
        int ylength = RDBMSA.StatisticsController.yearlength;
        XYChart.Series<Integer, Integer>[] series = Stream.<XYChart.Series<Integer, Integer>>generate(XYChart.Series::new).limit(20).toArray(XYChart.Series[]::new);
        
        for(int y = RDBMSA.StatisticsController.xy; y <= ylength; y++){
            yearCounter[y] = Integer.toString(tyr);
                      
            try {
                String yearDinerQuery = "SELECT NumberOfDiner,Time from customer WHERE Year = '" + tyr + "' ";
                ResultSet rs2 = Database.RetSet(yearDinerQuery);
                while (rs2.next()){
                    String d[] = Integer.toString(rs2.getInt("NumberOfDiner")).split("/");
                    int din = Integer.parseInt(d[0]);

                    String t[] = rs2.getString("Time").split(":");
                    int tim = Integer.parseInt(t[0]);
                    dinerCounter[tim] += dinerCounter[din] + din;
                    
                }
            } catch (SQLException ex) {
                //System.out.println(ex);
            }
            
            series[y].setName(yearCounter[y]);
            
            for (int i = 0; i < timeCounter.length; i++) {       
                series[y].getData().add(new XYChart.Data(timeCounter[i], dinerCounter[i]));
                dinerCounter[i] = 0;
            }
        
            areaChart.getData().add(series[y]);
            
            for(final XYChart.Data<Integer, Integer> data : series[y].getData()){
                data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler(){
                    @Override
                    public void handle(Event event) {
                        String x = null;
                        x ="Time: " + data.getXValue() + ", Number of records: "+ String.valueOf(data.getYValue());
                        LData.setText(x);
                    }
                });    
            }           
            tyr = tyr - 1;
        }       
    }
}
