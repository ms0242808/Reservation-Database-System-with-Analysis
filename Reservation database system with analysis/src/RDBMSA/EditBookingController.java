/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RDBMSA;

import static RDBMSA.Database.avaTLCheck;
import static RDBMSA.Database.avaTableCheck;
import static RDBMSA.Database.bookTableT;
import static RDBMSA.Database.getDate;
import static RDBMSA.Database.getEmail;
import static RDBMSA.Database.phoneCheck;
import static RDBMSA.Database.getFirstname;
import static RDBMSA.Database.getMaxDiner;
import static RDBMSA.Database.getNumberofdiner;
import static RDBMSA.Database.getPeriod;
import static RDBMSA.Database.getPhonenumber;
import static RDBMSA.Database.getSurname;
import static RDBMSA.Database.getTime;
import static RDBMSA.Database.removeBooked;
import static RDBMSA.Database.removeTableT;
import static RDBMSA.Database.tableMaxtime;
import static RDBMSA.Database.tableSet;
import static RDBMSA.Database.updateAvail;
import static RDBMSA.Database.updateBooked;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author jerry
 */
public class EditBookingController implements Initializable {
    @FXML
    private AnchorPane SceneP;
    @FXML
    private JFXTextField TPhone;
    @FXML
    private JFXButton BNext;
    @FXML
    private JFXButton BCancel;
    @FXML
    private JFXButton BUpadate;
    @FXML
    private JFXTextField UInfoText;
    @FXML
    private AnchorPane InputPane;
    @FXML
    private AnchorPane DetailPane;
    @FXML
    private AnchorPane ConfirmPane;
    @FXML
    private JFXTextField FNText;
    @FXML
    private JFXTextField SNText;
    @FXML
    private JFXTextField EText;
    @FXML
    private JFXTextField PNText;
    @FXML
    private JFXComboBox<String> NDCBox;
    @FXML
    private JFXDatePicker DPicker;
    @FXML
    private JFXTimePicker TPicker;
    
    FxController fx = new FxController();
    String phone = null;
    String reference = null;
    int pn = 0;
    
    @FXML
    private JFXTextField TCode;
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        BUpadate.setDisable(true);
        
    }    

    public static String dinerString(int diner){
        String din =null;
        if ( (diner & 1) == 0 ) { 
            for(int i=0; i< Integer.parseInt(getMaxDiner()); i++){
                if(diner == i+1 || diner == i+2){
                    din = Integer.toString(i) + "-" + Integer.toString(i+1);
                }
            }
        } else { 
            for(int i=0; i< Integer.parseInt(getMaxDiner()); i++){
                if(diner == i+1 || diner == i+2){
                    din = Integer.toString(i+1) + "-" + Integer.toString(i+2);
                }
            }
        }
        return din;
    }
    
    @FXML
    private void TPhoneClicked(MouseEvent event) {
        TPhone.setText(null);
    }

    @FXML
    private void BNextClciked(MouseEvent event) {
        //create another private viod to check and the customerID
        try{
            phone = TPhone.getText();
            reference = TCode.getText();
            pn = phoneCheck(phone, reference);
            if(pn > 0){
                InputPane.setVisible(false);
                DetailPane.setVisible(true);
                ConfirmPane.setVisible(false);
                FNText.setText(getFirstname(pn));
                SNText.setText(getSurname(pn));
                EText.setText(getEmail(pn));
                PNText.setText(getPhonenumber(pn));
                for(int i=0; i< Integer.parseInt(getMaxDiner()); i++){
                    NDCBox.getItems().add(Integer.toString(i+1));
                }
                NDCBox.setValue(Integer.toString(getNumberofdiner(pn)));
                fx.valiadteDate(DPicker);
                LocalDate x = DPicker.getConverter().fromString(getDate(pn));
                DPicker.setValue(x);
                TPicker.setPromptText(getTime(pn));
            }
            else {
                fx.AlertWarningwindow(null, null, "The phone number or booking reference you entered is wrong, please try again.");
                
                InputPane.setVisible(true);
                DetailPane.setVisible(false);
                ConfirmPane.setVisible(false);
            }
        } catch(NullPointerException e){
            System.out.println("Catched");
        }
    }

    @FXML
    private void BcancelClicked(MouseEvent event) {
        //setMessageContent to not update
        //write cancel booking query
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Cancle Booking");
        alert.setContentText("Are you sure you want to cancle this booking?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            removeBooked(TPhone.getText());
            InputPane.setVisible(false);
            DetailPane.setVisible(false);
            ConfirmPane.setVisible(true);
            UInfoText.setText("Booking has been cancelled.");
        } else{
            // ... user chose CANCEL or closed the dialog
        }
    }

    @FXML
    private void BUpdateClicked(MouseEvent event) {
        // setMessageContent to update message
        //write update query
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Update booking information");
        alert.setContentText("Are you sure  you want to update booking information?");
        int cID = phoneCheck(phone, reference);
        String fn = FNText.getText();
        String sn = SNText.getText();
        String e = EText.getText();
        String p = PNText.getText();
        int diner = Integer.parseInt(NDCBox.getValue());
        String origDin = dinerString(getNumberofdiner(pn));
        String editedDin = dinerString(diner);
        LocalDate x = DPicker.getValue();
        String date = DPicker.getConverter().toString(x);
        String origDate = getDate(pn);
        LocalTime y = TPicker.getValue();
        String t = y.toString();
        String origPeriod = getPeriod(cID);
        String editedPD = null;
        int periodH;
        int periodM;
        String tp[] = y.toString().split(":");
        periodH = Integer.parseInt(tp[0]); // hr
        periodM = Integer.parseInt(tp[1]); // min
        if(periodH <= 16 && periodH >= 10){
            editedPD = "lunch";
        } else if(periodH <= 23 && periodH >= 17){
            editedPD = "dinner";
        }
        
        int orgAid = avaTableCheck(origDin, origDate, origPeriod);
        int orgAtl = avaTLCheck(origDin, origDate, origPeriod);
        int editAid = avaTableCheck(editedDin, date, editedPD);
        int editAtl = avaTLCheck(editedDin, date, editedPD);
        
        String maxT[] = tableMaxtime(dinerString(diner)).split(":");
        int maxhour = Integer.parseInt(maxT[0]);
        int maxmin = Integer.parseInt(maxT[1]);
        int endH = periodH + maxhour;
        int endM = periodM + maxmin;
        String em = null;      
        if(endM >= 60){
            endH = endH + 1;
            endM = endM - 60;
        }     
        if(endM < 10){
            em = "0" + Integer.toString(endM);
        }else if(endM >= 10){
            em = Integer.toString(endM);
        }
        String endT = Integer.toString(endH) + ":" + em;
        String approxET = Integer.toString(endH) + em;
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){           
            reference = TCode.getText();
            
            removeTableT(origDate, getTime(pn), origDin, origPeriod);
            updateBooked(cID, fn, sn, diner, date, t, e, reference);// update period, ava, table_time
            //if diner or date or period changed, update availability table
            orgAtl = orgAtl + 1;
            updateAvail(orgAid, orgAtl, origDin, origDate, origPeriod);
            if(editAtl > 0){
                editAtl = editAtl - 1;
                updateAvail(editAid, editAtl, editedDin, date, editedPD);
            }else{
                int ts = tableSet(editedDin) - 1;
                updateAvail(editAid,ts,editedDin,date,editedPD);
            }
            
            bookTableT(date, t, endT, editedPD, editedDin, Integer.parseInt(approxET),"F");
            InputPane.setVisible(false);
            DetailPane.setVisible(false);
            ConfirmPane.setVisible(true);
            UInfoText.setText("Booking information has been updated.");
        }
    }   

    @FXML
    private void TPickerAction(ActionEvent event) {
        int nH = LocalTime.now().getHour();
        int nM = LocalTime.now().getMinute();
        int timeH = TPicker.getValue().getHour();
        int timeM = TPicker.getValue().getMinute();
        if(TPicker.getValue() != null){
            String selectedDate = DPicker.getConverter().toString(DPicker.getValue());
            if(RDBMSA.FxController.today.equals(selectedDate)){
                if(timeH <= nH && timeM < nM){
                    fx.AlertInforwindow(null, null, "Did you selected time in past?");
                    BUpadate.setDisable(true);
                }
            }
            BUpadate.setDisable(false);
        }
    }
}
