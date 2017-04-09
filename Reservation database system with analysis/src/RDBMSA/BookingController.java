/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RDBMSA;

import static RDBMSA.Database.booktable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author jerry
 */
public class BookingController implements Initializable {
    @FXML
    private JFXProgressBar ProgBar;
    @FXML
    private AnchorPane CustomerP1;
    @FXML
    private JFXComboBox<String> NPeople = new JFXComboBox<>();
    @FXML
    private JFXDatePicker Date;
    @FXML
    private JFXTimePicker Time;
    @FXML
    private AnchorPane CustomerDP;
    @FXML
    private JFXTextField Ftextfield;
    @FXML
    private JFXTextField Stextfield;
    @FXML
    private JFXTextField Ptextfield;
    @FXML
    private JFXTextField Etextfield;
    @FXML
    private JFXButton BCnext1;
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
    private JFXButton BCBack;
    @FXML
    private JFXButton BCConfirm;
    @FXML
    private AnchorPane CustomerP3;
    @FXML
    private Label ConfirmLabel;
    @FXML
    private JFXButton BCSee;
    @FXML
    private AnchorPane SceneP;
    
    FxController fx = new FxController();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        SetCustomerVisibles();
        Time.setValue(LocalTime.now());
        NPeople.getItems().addAll("1","2","3","4","5","6","7","8","9","10");
        fx.valiadteDate(Date);
        fx.textFV(Ftextfield, "Enter your first name.");
        fx.textFV(Stextfield, "Enter your surname.");
        fx.numberFV(Ptextfield, "Numbers only.");
        fx.textFV(Etextfield, "Enter your email address.");
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
        Time.setValue(LocalTime.now());
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
        DDLabel.setText("Date: " + Date.getValue());
    }

    @FXML
    private void valiation(ActionEvent event) {
        String tfn = Ftextfield.getText();
        String tsn = Stextfield.getText();
        String tpn = Ptextfield.getText();
        String tem = Etextfield.getText();
        if(tfn.isEmpty() || tsn.isEmpty() || tpn.isEmpty() || tem.isEmpty()){
            fx.AlertWarningwindow(null, null, "Have you fill in all the fields?");
        }else{
            fx.valiateEmail(Etextfield);
        }
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
        int NP = Integer.parseInt(NPeople.getValue());
        //System.out.println(NP);
        //String Dt = ((TextField)Date.getEditor()).getText();
        LocalDate x = Date.getValue();
        String Dt = Date.getConverter().toString(x);
        LocalTime y = Time.getValue();
        String T = y.toString();
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
        
        //System.out.println(PT);
        booktable(FT,ST,NP,Dt,T,PT,ET,SR,"8",Concode);
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
