/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RDBMSA;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author jerry
 */
public class FxController implements Initializable {
    @FXML
    private JFXButton CustomerTab;
    @FXML
    private JFXButton EditTab;
    @FXML
    private JFXButton QueueTab;
    @FXML
    private JFXButton ManagerTab;
    @FXML
    private AnchorPane SceneP;
    @FXML
    private VBox LPmenu;
    @FXML
    private JFXHamburger ham;
    @FXML
    private JFXDrawer drawer;
   
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Exitmenu.setOnAction(actionEvent -> Platform.exit());
        loadScenePane("Booking.fxml");
        MenuAnimation();
        drawer.setSidePane(LPmenu);
        LPmenu.setStyle("-fx-background-color: transparent;");
        //CustomerTab.setContentDisplay(ContentDisplay.TOP); set image above the text
    }
    
    private void MenuAnimation() {
        HamburgerBackArrowBasicTransition burger2 = new HamburgerBackArrowBasicTransition(ham);
        burger2.setRate(-1);
        
        //set 2 images rather than button, add transparent, change general pane size
        ham.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            burger2.setRate(burger2.getRate()*-1);
            burger2.play();
            if(drawer.isShown()){
                drawer.close();
            }else{
                drawer.open();
            }
        });
    }
    
    public void loadScenePane(String SceneName){
        try {
            Parent root1 = FXMLLoader.load(getClass().getResource(SceneName));
            SceneP.getChildren().setAll(root1);
        } catch (IOException ex) {
            Logger.getLogger(FxController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void AlertWarningwindow(String title, String header, String mesg){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(mesg);
        alert.showAndWait();
    }
    
    public void AlertInforwindow(String title, String header, String mesg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(mesg);
        alert.showAndWait();
    }
    
    public void setRedEffect(JFXTextField tf){
        //DropShadow red = new DropShadow(); // change effect, no shadow
        //red.setColor(javafx.scene.paint.Color.RED);
        ////tf.setEffect(red);
        //tf.setStyle("-fx-text-fill: red");
    }
    
    public void setGreenEffect(JFXTextField tf){
        //DropShadow green = new DropShadow();
        //green.setColor(javafx.scene.paint.Color.GREEN);
        //tf.setEffect(green);
        //tf.setStyle("-fx-text-fill: green");
    }  
    
    public void textFV(JFXTextField tf, String mesg){
        RequiredFieldValidator rf = new RequiredFieldValidator();
        tf.getValidators().add(rf);
        rf.setMessage(mesg);
        
        tf.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    tf.validate();
                }
            }
        });
    }
    
    public void numberFV(JFXTextField nf, String mesg){
        NumberValidator rf = new NumberValidator();
        nf.getValidators().add(rf);
        rf.setMessage(mesg);
        nf.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    nf.validate();
                }
            }
        });
    }

    /**
    public boolean valiatePhone(JFXTextField tf){
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(tf.getText());
        if(m.find() && m.group().equals(tf.getText())){
            return true;
        }else{
            AlertWarningwindow(null, null, "Please enter valid phone number");
            return false;
        }
    }
    **/
    public boolean valiateDateofBirth(JFXTextField dob){
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        df.setLenient(false);
        boolean valid = false;
        try {
            df.parse(dob.getText());
            valid = true;
            //setGreenEffect(TDob);
        } catch (ParseException e) { 
            // valid will still be false
        }
        if (!valid) {
            //setRedEffect(TDob);
            AlertWarningwindow(null, null, "Please enter the date of birth in DD/MM/YYYY.");
            return false;
        }
        return false;
    }
    
    public boolean AddressFV(JFXTextField address){// not complete yet
        Pattern p = Pattern.compile("\\d+\\s+(([a-zA-Z])+|([a-zA-Z]+\\s+[a-zA-Z]+))\\s+[a-zA-Z]*");//[0-9]+([,.][0-9]{1,2})?
        Matcher m = p.matcher(address.getText());
        if(m.find() && m.group().equals(address.getText())){
            //setGreenEffect(TAddress);
            return true;
        }else{
            //setRedEffect(TAddress);
            AlertWarningwindow(null, null, "Please enter the correct address.");
            return false;
        }
    }
    
    public boolean valiatePassword(JFXTextField password){//limit 6 digits, max 20 digits, need to test
        Pattern p = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})");//[a-zA-Z0-9]+[@#$%^&+=].{7,14}
        Matcher m = p.matcher(password.getText());
        if(m.find() && m.group().equals(password.getText())){
            //setGreenEffect(TPword);
            return true;
        }else{
            //setRedEffect(TPword);
            AlertWarningwindow(null, null, "Please enter valid Password.");
            return false;
        }
    } 
    
    public boolean valiateEmail(JFXTextField tf){
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(tf.getText());
        if(m.find() && m.group().equals(tf.getText())){
            return true;
        }else{
            AlertWarningwindow(null, null, "Please enter valid Email.");
            return false;
        }
    }
    
    public void valiadteDate(JFXDatePicker date){
        Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>(){
            public DateCell call(final DatePicker datePicker){
                return new DateCell(){
                    public void updateItem(LocalDate item, boolean empty){
                        super.updateItem(item, empty);
                        DayOfWeek day = DayOfWeek.from(item);
                        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY){
                            this.setTextFill(Color.BLUE);
                        }
                        if (item.isBefore(LocalDate.now())){
                            this.setDisable(true);
                        }
                    }
                };
            }
        };            
        date.setDayCellFactory(dayCellFactory);
    }
    
    @FXML
    private void BCTabClicked(ActionEvent event) {
        loadScenePane("Booking.fxml");
    }

    @FXML
    private void BETabClicked(ActionEvent event) {
        loadScenePane("EditBooking.fxml");
    }

    @FXML
    private void BQTabClicked(ActionEvent event) {
        loadScenePane("Queue.fxml");
    }
    
    @FXML
    private void BMTabClicked(ActionEvent event) {
        loadScenePane("Login.fxml");
    }
}