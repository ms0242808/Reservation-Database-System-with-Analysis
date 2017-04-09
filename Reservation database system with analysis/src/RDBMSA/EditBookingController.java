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
import static RDBMSA.Database.getNumberofdiner;
import static RDBMSA.Database.getPhonenumber;
import static RDBMSA.Database.getSurname;
import static RDBMSA.Database.getTime;
import static RDBMSA.Database.removeBooked;
import static RDBMSA.Database.updateBooked;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
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
    private ComboBox<String> NDCBox = new ComboBox<>();
    @FXML
    private DatePicker DPicker;
    @FXML
    private ComboBox<String> TCBox = new ComboBox<>();

    FxController alertwindow = new FxController();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void TPhoneClicked(MouseEvent event) {
        TPhone.setText(null);
    }

    @FXML
    private void BNextClciked(MouseEvent event) {
        //create another private viod to check and the customerID
        String phone = TPhone.getText();
        try{
            int pn = 0;
            pn = phoneCheck(phone);
            if(pn > 0){
                InputPane.setVisible(false);
                DetailPane.setVisible(true);
                ConfirmPane.setVisible(false);
                FNText.setText(getFirstname(pn));
                SNText.setText(getSurname(pn));
                EText.setText(getEmail(pn));
                PNText.setText(getPhonenumber(pn));
                NDCBox.valueProperty().setValue(Integer.toString(getNumberofdiner(pn)));
                DPicker.setPromptText(getDate(pn));
                TCBox.valueProperty().setValue(getTime(pn));
            }
            else {
                alertwindow.AlertWarningwindow(null, null, "The phone number you inputed is wrong, please try again.");
                
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
        String t = TCBox.getValue();
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            
            updateBooked(cID, fn, sn, 2, date, "11:00", p, e);// bug phone cannot update.
            InputPane.setVisible(false);
            DetailPane.setVisible(false);
            ConfirmPane.setVisible(true);
            UInfoText.setText("Booking information has been updated.");
        }
    }   
}
