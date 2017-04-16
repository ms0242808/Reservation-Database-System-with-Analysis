/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RDBMSA;

import static RDBMSA.Database.getCID;
import static RDBMSA.Database.getDate;
import static RDBMSA.Database.getEmail;
import static RDBMSA.Database.phoneCheck;
import static RDBMSA.Database.getFirstname;
import static RDBMSA.Database.getMaxDiner;
import static RDBMSA.Database.getNumberofdiner;
import static RDBMSA.Database.getPhonenumber;
import static RDBMSA.Database.getSurname;
import static RDBMSA.Database.getTime;
import static RDBMSA.Database.removeBooked;
import static RDBMSA.Database.updateBooked;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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

    @FXML
    private void TPhoneClicked(MouseEvent event) {
        TPhone.setText(null);
    }

    @FXML
    private void BNextClciked(MouseEvent event) {
        //create another private viod to check and the customerID
        String phone = TPhone.getText();
        String reference = TCode.getText();
        try{
            int pn = 0;
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
                fx.AlertWarningwindow(null, null, "The phone number you inputed is wrong, please try again.");
                
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
        int cID = getCID(PNText.getText());
        String fn = FNText.getText();
        String sn = SNText.getText();
        String e = EText.getText();
        String p = PNText.getText();
        int diner = Integer.parseInt(NDCBox.getValue());
        LocalDate x = DPicker.getValue();
        String date = DPicker.getConverter().toString(x);
        String t = null;//TCBox.getValue();
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            String reference = TCode.getText();
            updateBooked(cID, fn, sn, 2, date, "11:00", e, reference);// update period, ava, table_time
            
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
        String period = null;
        if(TPicker.getValue() != null){
            String selectedDate = DPicker.getConverter().toString(DPicker.getValue());
            if(RDBMSA.FxController.today.equals(selectedDate)){
                if(timeH <= nH && timeM < nM){
                    fx.AlertInforwindow(null, null, "Did you selected time in past?");
                    BUpadate.setDisable(true);
                }
            }
            if(timeH <= 16 && timeH >= 10){
                period = "lunch";
            } else if(timeH <= 23 && timeH >= 17){
                period = "dinner";
            }
            BUpadate.setDisable(false);
        }
    }
}
