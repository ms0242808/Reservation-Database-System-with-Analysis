/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RDBMSA;

import static RDBMSA.Database.countRecords;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
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
        //areaChart.setTitle("Time - Diners");
        String x = null;
        for(int i=0; i<timeCounter.length; i++){
            x = Integer.toString(i) + ":00 - " + Integer.toString(i) + ":59";
            timeCounter[i] = x;
            //System.out.println(timeCounter[i]);
        }
    }    
    
    public void clearGraph(){
        areaChart.getData().clear();
    }
    
    public void setTimeAreaData(List<customerList> CList) {
        clearGraph();      
        int records = countRecords();
        int[] dinerCounter = new int[records];
        for (customerList p : CList) {
            //String stry[] = p.getBdate().split("/");
            //int yr = Integer.parseInt(stry[2]);
            //System.out.println(yr);

            String strd[] = Integer.toString(p.getNumberofdiner()).split("/");
            int diners = Integer.parseInt(strd[0]);
            
            String str[] = p.getBtime().split(":");
            int t = Integer.parseInt(str[0]);
            
            dinerCounter[t] += dinerCounter[diners] + diners;
            //System.out.println(t+ ": "+dinerCounter[t]);
        } 
        
        //XYChart.Series<Integer, Integer>[] series = Stream.<XYChart.Series<String, Number>>generate(XYChart.Series::new).limit(2).toArray(XYChart.Series[]::new);
        XYChart.Series<Integer, Integer> series = new XYChart.Series<>();
        series.setName("Number of records");
        for (int i = 0; i < timeCounter.length; i++) {       
            series.getData().add(new XYChart.Data(timeCounter[i], dinerCounter[i]));
        }
        
        areaChart.getData().add(series);
        
        for(final XYChart.Data<Integer, Integer> data : series.getData()){
            data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler(){
                @Override
                public void handle(Event event) {
                    String x;
                    x = "Time: " + data.getXValue() + ", Number of records: "+ String.valueOf(data.getYValue());
                    LData.setText(x);
                }
            });    
        }
    }
}
