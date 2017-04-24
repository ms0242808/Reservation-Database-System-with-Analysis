/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RDBMSA;

import static RDBMSA.Database.avaTLCheck;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
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
    private JFXButton BCustomer;
    @FXML
    private JFXButton BEdit;
    @FXML
    private JFXButton BQueue;
    @FXML
    private AnchorPane SceneP;
    @FXML
    private VBox LPmenu;
    @FXML
    private JFXHamburger ham;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXButton BLogin;
    @FXML
    private JFXButton BMinimize;
    @FXML
    private JFXButton BClose;
    @FXML
    private HBox TopFrame;
    @FXML
    private JFXTextField UserTF;
    @FXML
    private JFXButton BDatabase;
    
    public static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public static Date de = new Date();
    public static String today = dateFormat.format(de);
    private static double xOffset = 0;
    private static double yOffset = 0;
    public static String spane = "Booking.fxml";
    public static String nameUser = "Hello";
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        int userLogedID = RDBMSA.LoginController.LogId;
        
        if(userLogedID > 0){
            UserTF.setVisible(true);
            UserTF.setText(nameUser);
            BDatabase.setVisible(true);
            BLogin.setVisible(false);
            AddToolTip("View Booking records", BDatabase);
        }else{
            UserTF.setVisible(false);
            BDatabase.setVisible(false);
            BLogin.setVisible(true);
        }
        
        loadScenePane(spane);
        BLogin.setText(null);
        BMinimize.setText(null);
        BClose.setText(null);
        
        MenuAnimation();
        drawer.setSidePane(LPmenu);
        
        AddToolTip("Close", BClose);
        AddToolTip("Minimize", BMinimize);
        AddToolTip("Staff/Manager Login", BLogin);
        AddToolTip("Book a table", BCustomer);
        AddToolTip("Edit Booking", BEdit);
        AddToolTip("Online Self-Queue", BQueue);
        
        LPmenu.backgroundProperty().set(Background.EMPTY);
        LPmenu.setStyle("-fx-background-color: transparent;");
    }
    
    public void AddToolTip(String tipMesg, JFXButton butt){
        Tooltip addTip = new Tooltip();
        addTip.setText(tipMesg);
        butt.setTooltip(addTip);
    }
    
    private void MenuAnimation() {
        HamburgerBackArrowBasicTransition burger2 = new HamburgerBackArrowBasicTransition(ham);
        burger2.setRate(-1);
        
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

    public boolean valiateDateofBirth(JFXTextField dob){
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        df.setLenient(false);
        boolean valid = false;
        try {
            df.parse(dob.getText());
            valid = true;
        } catch (ParseException e) { 
            // valid will still be false
        }
        if (!valid) {
            AlertWarningwindow(null, null, "Please enter the date of birth in DD/MM/YYYY.");
            return false;
        }
        return false;
    }
    
    public boolean AddressFV(JFXTextField address){// not complete yet
        Pattern p = Pattern.compile("\\d+\\s+(([a-zA-Z])+|([a-zA-Z]+\\s+[a-zA-Z]+))\\s+[a-zA-Z]*");//[0-9]+([,.][0-9]{1,2})?
        Matcher m = p.matcher(address.getText());
        if(m.find() && m.group().equals(address.getText())){
            return true;
        }else{
            AlertWarningwindow(null, null, "Please enter the correct address.");
            return false;
        }
    }
    
    public boolean valiatePassword(JFXTextField password){//limit 6 digits, max 20 digits
        Pattern p = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})");//[a-zA-Z0-9]+[@#$%^&+=].{7,14}
        Matcher m = p.matcher(password.getText());
        if(m.find() && m.group().equals(password.getText())){
            return true;
        }else{
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
    private void BCClicked(ActionEvent event) {
        loadScenePane("Booking.fxml");
    }

    @FXML
    private void BEClicked(ActionEvent event) {
        loadScenePane("EditBooking.fxml");
    }

    @FXML
    private void BLoginOnAction(ActionEvent event) {
        try {
            Parent loginroot = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene loginScene = new Scene(loginroot);
            RDBMSA.Generator.viewStage.setScene(loginScene);
        } catch (IOException ex) {
            Logger.getLogger(FxController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void BQClicked(ActionEvent event) {
        int nH = LocalTime.now().getHour();
        String period = null;
        int ocCheck = 1;
        int i=0;
        if(nH <= 16 && nH >= 10){//
            period = "lunch";
        } else if(nH <= 23 && nH >= 17){
            period = "dinner";
        }
        while(i != 10){
            String odd = Integer.toString(i+1);
            String even = Integer.toString(i+2);
            String set = odd + "-" + even;
            i = i + 2;
            ocCheck= avaTLCheck(set,today,period);
        }
        if(ocCheck == -2){
            AlertInforwindow(null, null, "There are still some table left for current " + period + " section, book it quickly.");
            loadScenePane("Booking.fxml");
        }else{
            loadScenePane("Queue.fxml");
        }
    }

    @FXML
    private void BMinimizeAction(ActionEvent event) {
        RDBMSA.Generator.viewStage.setIconified(true);
    }

    @FXML
    private void BCloseAction(ActionEvent event) {
        RDBMSA.Generator.viewStage.close();
    }

    @FXML
    private void TopFrameMouseDragged(MouseEvent event) {
        RDBMSA.Generator.viewStage.setX(event.getScreenX() + xOffset);
        RDBMSA.Generator.viewStage.setY(event.getScreenY() + yOffset);
    }

    @FXML
    private void TopFrameMousePressed(MouseEvent event) {
        xOffset = RDBMSA.Generator.viewStage.getX() - event.getScreenX();
        yOffset = RDBMSA.Generator.viewStage.getY() - event.getScreenY();
    }

    @FXML
    private void BDatabaseAction(ActionEvent event) {
        loadScenePane("Manage.fxml");
    }
}