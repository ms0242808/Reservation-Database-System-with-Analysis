/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RDBMSA;

import static RDBMSA.Database.addAccount;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author jerry
 */
public class AddAccountController implements Initializable {
    @FXML
    private AnchorPane SceneP;
    @FXML
    private TextField TFname;
    @FXML
    private TextField TLname;
    @FXML
    private TextField TDob;
    @FXML
    private TextField TPnumber;
    @FXML
    private TextField TAddress;
    @FXML
    private TextField TUname;
    @FXML
    private TextField TPword;
    @FXML
    private RadioButton RStaff;
    @FXML
    private RadioButton RManager;
    @FXML
    private Button BCancel;
    @FXML
    private Button BCAccount;

    public static String role;
    public static int Addscene = 0;
    FxController alertwindow = new FxController();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //valiation
    }   
    
    public void loadScenePane(String SceneName){
        try {
            Parent root1 = FXMLLoader.load(getClass().getResource(SceneName));
            SceneP.getChildren().setAll(root1);
        } catch (IOException ex) {
            Logger.getLogger(AddAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setRedEffect(TextField tf){
        DropShadow red = new DropShadow();
        red.setColor(javafx.scene.paint.Color.RED);
        tf.setEffect(red);
    }
    
    public void setGreenEffect(TextField tf){
        DropShadow green = new DropShadow();
        green.setColor(javafx.scene.paint.Color.GREEN);
        tf.setEffect(green);
    }
    
    private boolean valiateFirstname(){
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(TFname.getText());
        if(m.find() && m.group().equals(TFname.getText())){
            setGreenEffect(TFname);
            return true;
        }else{
            setRedEffect(TFname);
            return false;
        }
    }
    
    private boolean valiateSurname(){
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(TLname.getText());
        if(m.find() && m.group().equals(TLname.getText())){
            setGreenEffect(TLname);
            return true;
        }else{
            setRedEffect(TLname);
            return false;
        }
    }
    
    private boolean valiateDateofBirth(){
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        df.setLenient(false);
        boolean valid = false;
        try {
            df.parse(TDob.getText());
            valid = true;
            setGreenEffect(TDob);
        } catch (ParseException e) { 
            // valid will still be false
        }
        if (!valid) {
            setRedEffect(TDob);
            return false;
        }
        return false;
    }
    
    private boolean valiatePhone(){
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(TPnumber.getText());
        if(m.find() && m.group().equals(TPnumber.getText())){
            setGreenEffect(TPnumber);
            return true;
        }else{
            setRedEffect(TPnumber);
            return false;
        }
    }
    
    private boolean valiateAddress(){// not complete yet
        Pattern p = Pattern.compile("\\d+\\s+(([a-zA-Z])+|([a-zA-Z]+\\s+[a-zA-Z]+))\\s+[a-zA-Z]*");//[0-9]+([,.][0-9]{1,2})?
        Matcher m = p.matcher(TAddress.getText());
        if(m.find() && m.group().equals(TAddress.getText())){
            setGreenEffect(TAddress);
            return true;
        }else{
            setRedEffect(TAddress);
            return false;
        }
    }
    
    private boolean valiateUsername(){
        Pattern p = Pattern.compile("[a-zA-Z0-9]+");
        Matcher m = p.matcher(TUname.getText());
        if(m.find() && m.group().equals(TUname.getText())){
            setGreenEffect(TUname);
            return true;
        }else{
            setRedEffect(TUname);
            return false;
        }
    }
    
    private boolean valiatePassword(){//limit 6 digits, max 20 digits, need to test
        Pattern p = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})");//[a-zA-Z0-9]+[@#$%^&+=].{7,14}
        Matcher m = p.matcher(TPword.getText());
        if(m.find() && m.group().equals(TPword.getText())){
            setGreenEffect(TPword);
            return true;
        }else{
            setRedEffect(TPword);
            return false;
        }
    }
    
    @FXML
    private void RStaffClicked(MouseEvent event) {
        RManager.selectedProperty().setValue(Boolean.FALSE);
        role = "S";
    }

    @FXML
    private void RManagerClicked(MouseEvent event) {
        RStaff.selectedProperty().setValue(Boolean.FALSE);
        role = "M";
    }

    @FXML
    private void BCancelClicked(MouseEvent event) {
        Addscene = 1;
        loadScenePane("Manage.fxml");
    }

    @FXML
    private void validation(ActionEvent event) {
        alertwindow.AlertWarningwindow(null, null, "Pleases enter correct details.");
    }    

    @FXML
    private void BCreateClicked(MouseEvent event) {
        addAccount(TFname.getText(),TLname.getText(),TDob.getText(),TPnumber.getText(),TAddress.getText(),TUname.getText(),TPword.getText(),role);
        alertwindow.AlertInforwindow(null, null, "Account created.");
        Addscene = 1;
        loadScenePane("Manage.fxml");
    }

    @FXML
    private void VFname(KeyEvent event) {
        valiateFirstname();
    }

    @FXML
    private void VLname(KeyEvent event) {
        valiateSurname();
    }

    @FXML
    private void VDob(KeyEvent event) {
        valiateDateofBirth();
    }

    @FXML
    private void VPNumber(KeyEvent event) {
        valiatePhone();
    }

    @FXML
    private void VAddress(KeyEvent event) {
        //valiateAddress();
    }

    @FXML
    private void VUName(KeyEvent event) {
        valiateUsername();
    }

    @FXML
    private void VPWord(KeyEvent event) {
        //valiatePassword();
    }
}
