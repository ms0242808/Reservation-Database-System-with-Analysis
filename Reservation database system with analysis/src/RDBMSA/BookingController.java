/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RDBMSA;

import static RDBMSA.Database.avaTLCheck;
import static RDBMSA.Database.avaTableCheck;
import static RDBMSA.Database.booktable;
import static RDBMSA.Database.tableMaxtime;
import static RDBMSA.Database.tableSet;
import static RDBMSA.Database.bookAvail;
import static RDBMSA.Database.bookTableT;
import static RDBMSA.Database.getMaxDiner;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Properties;
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
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.MimeMessage;

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
    private JFXComboBox<String> NPeople ;
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
    String pd = null;
    String approxET = null;
    String endT = null;
    int periodH;
    int periodM;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        SetCustomerVisibles();
        Time.setValue(LocalTime.now());
        
        for(int i=0; i< Integer.parseInt(getMaxDiner()); i++){
            NPeople.getItems().add(Integer.toString(i+1));
        }
        NPeople.setValue("1");
        
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
        NPeople.setValue("1");
        Date.setValue(null);
        Time.setValue(LocalTime.now());
        Ftextfield.setText(null);
        Stextfield.setText(null);
        Ptextfield.setText(null);
        Etextfield.setText(null);
        SRtextarea.setText("N/A");
    }
    
    public void SetDisables(){
        NPeople.setDisable(true);
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
    
    public void sendConfirmEmail(String mesg, String to){
        String host="smtp.gmail.com";
        String from = "jrjonlinefood@gmail.com";
        String password = "Foodcourt123";
        
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");	
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        
        Session session = Session.getDefaultInstance(props,new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Restaurant Booking Confirmation Email");
            message.setText(mesg);
            Transport.send(message);
        } catch (MessagingException e){         
            throw new RuntimeException(e);
        }
    }
    
    @FXML
    private void SelectedNP(ActionEvent event) {
        //String npvalue = NPeople.getValue();
        //System.out.println(npvalue);
        if(NPeople.getValue() != null){
            Time.setDisable(false);
            updateProgBar(1);
        }
    }

    @FXML
    private void SelectedDate(ActionEvent event) {
        //System.out.println("1");
        if(Date.getValue() != null){
            //System.out.println(Date.getValue());
            NPeople.setDisable(false);
            updateProgBar(0);
        }
    }

    @FXML
    private void SelectedTime(ActionEvent event) {
        String period = null;
        int nH = LocalTime.now().getHour();
        int nM = LocalTime.now().getMinute();
        int timeH = Time.getValue().getHour();
        int timeM = Time.getValue().getMinute();
        if(Time.getValue() != null){
            String selectedDate = Date.getConverter().toString(Date.getValue());
            if(RDBMSA.FxController.today.equals(selectedDate)){
                if(timeH <= nH && timeM < nM){
                    fx.AlertInforwindow(null, null, "Did you selected time in past?");
                    CustomerDP.setDisable(true);
                }
            }

            if(timeH <= 16 && timeH >= 10){
                period = "lunch";
            } else if(timeH <= 23 && timeH >= 17){
                period = "dinner";
            }
            int np = Integer.parseInt(NPeople.getValue());
            String numberP = null;
            if ( (np & 1) == 0 ) { 
                for(int i=0; i< Integer.parseInt(getMaxDiner()); i++){
                    if(np == i+1 || np == i+2){
                        numberP = Integer.toString(i) + "-" + Integer.toString(i+1);
                    }
                }
            } else { 
                for(int i=0; i< Integer.parseInt(getMaxDiner()); i++){
                    if(np == i+1 || np == i+2){
                        numberP = Integer.toString(i+1) + "-" + Integer.toString(i+2);
                    }
                }
            }

            int avaCheck = avaTLCheck(numberP,selectedDate,period);
            if(avaCheck > 0 || avaCheck == -2){
                CustomerDP.setDisable(false);
                updateProgBar(2);
            }else if(avaCheck == 0){
                fx.AlertInforwindow(null, null, period +" period is fully booked, please select another time, date or diners");
                CustomerDP.setDisable(true);
            }
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
        String din = NPeople.getValue();
        //System.out.println(NP);
        //String Dt = ((TextField)Date.getEditor()).getText();
        LocalDate x = Date.getValue();
        String Dt = Date.getConverter().toString(x);        
        String yrb = Integer.toString(x.getYear());
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
        //update customer booking
        String tp[] = y.toString().split(":");
        periodH = Integer.parseInt(tp[0]); // hr
        periodM = Integer.parseInt(tp[1]); // min
        int aid = 0;
        if(periodH <= 16 && periodH >= 10){
            pd = "lunch";
            aid = avaTableCheck(din, Dt, pd);
        } else if(periodH <= 23 && periodH >= 17){
            pd = "dinner";
            aid = avaTableCheck(din, Dt, pd);
        }
        
        booktable(FT,ST,NP,Dt,T,PT,ET,SR,pd,Concode,yrb);
        
        //update availability.      
        if ( (NP & 1) == 0 ) { 
            for(int i=0; i< Integer.parseInt(getMaxDiner()); i++){
                if(NP == i+1 || NP == i+2){
                    din = Integer.toString(i) + "-" + Integer.toString(i+1);
                }
            }
        } else { 
            for(int i=0; i< Integer.parseInt(getMaxDiner()); i++){
                if(NP == i+1 || NP == i+2){
                    din = Integer.toString(i+1) + "-" + Integer.toString(i+2);
                }
            }
        }
        
        int atl = avaTLCheck(din, Dt, pd);
        if(atl > 0){
            atl = atl - 1;
            bookAvail(aid, atl, din, Dt, pd);
        }else{
            int ts = tableSet(din) - 1;
            bookAvail(aid, ts, din, Dt, pd);
        }
        
        //update table_time
        String maxT[] = tableMaxtime(din).split(":");
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
        endT = Integer.toString(endH) + ":" + em;
        approxET = Integer.toString(endH) + em;
        bookTableT(Dt, T, endT, pd, din, Integer.parseInt(approxET),"F");
        
        //send confirmation email
        String newLine = System.getProperty("line.separator");
        String message = "Hello " + FT + " " + ST + "," + newLine +
                "Here is your booking confirmation deatils: " + newLine +
                "Booking reference: " + Concode + newLine + 
                "Number of diners booked: " + NP + newLine + 
                "Time: " + Dt + newLine + 
                "Date: " + T + newLine + 
                "Additional requests: " + SR + newLine +
                "We are looking forward to see you soon!";
        sendConfirmEmail(message, ET);
        
        // next page
        ConfirmLabel.setText("Confrimation code: "+Concode);
        updateProgBar(10);
        BCSee.setText("See you at " + Dt + " , " + T);
    }
    
    @FXML
    private void BCseeClicked(MouseEvent event) {
        SetCustomerVisibles();
        ClearFields();
        SetDisables();
    }    
}
