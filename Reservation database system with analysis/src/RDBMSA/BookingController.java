/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RDBMSA;

import static RDBMSA.Database.booktable;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author jerry
 */
public class BookingController implements Initializable {
    @FXML
    private ProgressBar ProgBar;
    @FXML
    private AnchorPane CustomerP1;
    @FXML
    private ImageView Crowd;
    @FXML
    private ComboBox<String> NPeople = new ComboBox<>();
    @FXML
    private DatePicker Date;
    @FXML
    private ImageView Clock;
    @FXML
    private ComboBox<String> Time = new ComboBox<>();
    @FXML
    private AnchorPane CustomerDP;
    @FXML
    private Label FLabel;
    @FXML
    private TextField Ftextfield;
    @FXML
    private Label SLabel;
    @FXML
    private TextField Stextfield;
    @FXML
    private Label PLabel;
    @FXML
    private TextField Ptextfield;
    @FXML
    private Label ELabel;
    @FXML
    private TextField Etextfield;
    @FXML
    private Button BCnext1;
    @FXML
    private AnchorPane CustomerP2;
    @FXML
    private Label DLabel;
    @FXML
    private ImageView Detail;
    @FXML
    private Label DNLabel;
    @FXML
    private Label DNPLabel;
    @FXML
    private Label DELabel;
    @FXML
    private Label DPLabel;
    @FXML
    private Label DTLabel;
    @FXML
    private Label DDLabel;
    @FXML
    private Label SRLabel;
    @FXML
    private ImageView Request;
    @FXML
    private TextArea SRtextarea;
    @FXML
    private Button BCBack;
    @FXML
    private Button BCConfirm;
    @FXML
    private AnchorPane CustomerP3;
    @FXML
    private Label ConfirmLabel;
    @FXML
    private Button BCSee;
    @FXML
    private AnchorPane SceneP;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        SetCustomerVisibles();
        
        NPeople.getItems().addAll("1","2","3","4","5","6","7","8","9","10");
        Time.getItems().addAll("12.00","12.30","13.00");
        valiadteDate();
    }    

    public void SetCustomerVisibles(){
        SceneP.setVisible(true);
        //updateProgBar(-1);
        ProgBar.progressProperty().unbind();
        ProgBar.setProgress(0);
        ClearFields();
        SetDisables();
        //ManagerP.setVisible(false);
        //AccountMenu.setVisible(false);
        CustomerP1.setVisible(true);
        CustomerP2.setVisible(false);
        CustomerP3.setVisible(false);
        //GeneralMenu.setText("Customer");
    }

    public void ClearFields(){
        NPeople.setValue(null);
        Date.setValue(null);
        Time.setValue(null);
        Ftextfield.setText(null);
        Stextfield.setText(null);
        Ptextfield.setText(null);
        Etextfield.setText(null);
        SRtextarea.setText(null);
    }
    
    public void SetDisables(){
        Date.setDisable(true);
        Time.setDisable(true);
        CustomerDP.setDisable(true);
        BCnext1.setDisable(true);
    }
    
    public void updateProgBar(int bar){
        Task copyWorker;
        
        copyWorker = new Task() {
            @Override
            protected Object call() throws Exception {                  
                updateProgress(bar + 1, 10);
                return true;
            }
        };
        ProgBar.progressProperty().unbind();        
        ProgBar.progressProperty().bind(copyWorker.progressProperty());
        copyWorker.messageProperty().addListener(new ChangeListener<String>() {
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        System.out.println(newValue);
                    }
                });
        new Thread(copyWorker).start();
    }
    
    public int getRandomNumber(){
        String CHAR_LIST ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        int randomInt = 0;
        Random randomGenerator = new Random();
        randomInt = randomGenerator.nextInt(CHAR_LIST.length());
        if (randomInt - 1 == -1) {
            return randomInt;
        } else {
            return randomInt - 1;
        }
    }
    
    private boolean valiateFirstname(){
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(Ftextfield.getText());
        if(m.find() && m.group().equals(Ftextfield.getText())){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Please enter valid first name");
            alert.showAndWait();
            return false;
        }
    }
    
    private boolean valiateSurname(){
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(Stextfield.getText());
        if(m.find() && m.group().equals(Stextfield.getText())){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Please enter valid surname");
            alert.showAndWait();
            return false;
        }
    }
    
    private boolean valiatePhone(){
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(Ptextfield.getText());
        if(m.find() && m.group().equals(Ptextfield.getText())){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Please enter valid phone number");
            alert.showAndWait();
            return false;
        }
    }
    
    private boolean valiateEmail(){
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(Etextfield.getText());
        if(m.find() && m.group().equals(Etextfield.getText())){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Please enter valid Email");
            alert.showAndWait();
            return false;
        }
    }
    
    public void valiadteDate(){
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
        Date.setDayCellFactory(dayCellFactory);
    }
    
    @FXML
    private void SelectedNP(ActionEvent event) {
        //String npvalue = NPeople.getValue();
        //System.out.println(npvalue);
        if(NPeople.getValue() != null){
            Date.setDisable(false);
            updateProgBar(0);
        }
    }

    @FXML
    private void SelectedDate(ActionEvent event) {
        //System.out.println("1");
        if(Date.getValue() != null){
            //System.out.println(Date.getValue());
            Time.setDisable(false);
            updateProgBar(1);
        }
    }

    @FXML
    private void SelectedTime(ActionEvent event) {
        if(Time.getValue() != null){
            CustomerDP.setDisable(false);
            updateProgBar(2);
        }
    }

    @FXML
    private void EditedFname(KeyEvent event) {
        if(Ftextfield.getText() != null){
            Stextfield.setDisable(false);
            updateProgBar(3);
        }   
    }

    @FXML
    private void EditedSname(KeyEvent event) {
        if(Stextfield.getText() != null){
            Ptextfield.setDisable(false);
            updateProgBar(4);
        }
    }

    @FXML
    private void EditedP(KeyEvent event) {
        if(Ptextfield.getText() != null){
            Etextfield.setDisable(false);
            updateProgBar(5);
        }
    }

    @FXML
    private void EditedE(KeyEvent event) {
        if(Etextfield.getText() != null){
            BCnext1.setDisable(false);
            updateProgBar(6);
        }
    }

    @FXML
    private void BCNextClicked(MouseEvent event) {
        CustomerP1.setVisible(false);
        CustomerP2.setVisible(true);
        CustomerP3.setVisible(false);

        updateProgBar(7);
        
        DNLabel.setText("Your name: " + Ftextfield.getText() + " "+ Stextfield.getText());
        DNPLabel.setText("Number of diners: " + NPeople.getValue());
        DELabel.setText("Email: " + Etextfield.getText());
        DPLabel.setText("Contact number: " + Ptextfield.getText());
        DTLabel.setText("Time: " + Time.getValue());
        DDLabel.setText("Date: " + ((TextField)Date.getEditor()).getText());        
    }

    @FXML
    private void valiation(ActionEvent event) {
        valiateFirstname();//button valiation
        valiateSurname();
        valiatePhone();
        valiateEmail();
    }

    @FXML
    private void BCBackClicked(MouseEvent event) {
        CustomerP1.setVisible(true);
        CustomerP2.setVisible(false);
        CustomerP3.setVisible(false);
        updateProgBar(6);
    }

    @FXML
    private void BCConfirmClicked(MouseEvent event) {
        CustomerP1.setVisible(false);
        CustomerP2.setVisible(false);
        CustomerP3.setVisible(true);
        String NP = NPeople.getValue();
        String Dt = ((TextField)Date.getEditor()).getText();
        String T = Time.getValue();
        String FT = Ftextfield.getText();
        String ST = Stextfield.getText();
        String PT = Ptextfield.getText();
        String ET = Etextfield.getText();
        String SR = SRtextarea.getText();
        //preorder food
        
        String CHAR_LIST ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        int random = 10;
        StringBuilder randStr = new StringBuilder();
        for(int i=0; i<random; i++){
            int number = getRandomNumber();
            char ch = CHAR_LIST.charAt(number);
            randStr.append(ch);
        }
        String Concode = randStr.toString();
        
        //System.out.println(Dt);
        booktable(FT,ST,Dt,T,PT,ET,SR,"8",Concode);
        ConfirmLabel.setText("Confrimation code: "+Concode);
        updateProgBar(10);
        BCSee.setText("See you at " + Dt + " , " + T);
    }

    @FXML
    private void BCseeClicked(MouseEvent event) {
        SetCustomerVisibles();
        //ClearFields();
        //SetDisables();
    }    
}
