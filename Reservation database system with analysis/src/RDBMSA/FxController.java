/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RDBMSA;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

/**
 * FXML Controller class
 *
 * @author jerry
 */
public class FxController implements Initializable {
    @FXML
    private AnchorPane MenuP;
    @FXML
    private Menu Menuname;
    @FXML
    private MenuItem Customermenu;
    @FXML
    private MenuItem Managermenu;
    @FXML
    private AnchorPane SceneP;
    @FXML
    private AnchorPane CustomerP;
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
    private Button BCConfirm;
    @FXML
    private AnchorPane CustomerP3;
    @FXML
    private Label ConfirmLabel;
    @FXML
    private Button BCSee;
    @FXML
    private AnchorPane ManagerP;
    @FXML
    private AnchorPane CustomerDP;
    @FXML
    private MenuItem Exitmenu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Exitmenu.setOnAction(actionEvent -> Platform.exit());
        
        CustomerP.setVisible(true);
        ManagerP.setVisible(false);
        CustomerP1.setVisible(true);
        
        Date.setDisable(true);
        Time.setDisable(true);
        CustomerDP.setDisable(true);
        BCnext1.setDisable(true);
        
        CustomerP2.setVisible(false);
        CustomerP3.setVisible(false);
        
        ClearFields();
        
        NPeople.getItems().addAll("1","2","3","4","5","6","7","8","9","10");
        Time.getItems().addAll("12.00","12.30","13.00");
        
        //MenuItem exitMenuItem = new MenuItem("Exit");
        //fileMenu.getItems().add(exitMenuItem);
        //exitMenuItem.setOnAction(actionEvent -> Platform.exit());

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
    
    @FXML
    private void BCNextClicked(MouseEvent event) {
        CustomerP1.setVisible(false);
        CustomerP2.setVisible(true);
        CustomerP3.setVisible(false);
    }

    @FXML
    private void BCConfirmClicked(MouseEvent event) {
        CustomerP1.setVisible(false);
        CustomerP2.setVisible(false);
        CustomerP3.setVisible(true);
    }

    @FXML
    private void BCseeClicked(MouseEvent event) {
        CustomerP1.setVisible(true);
        CustomerP2.setVisible(false);
        CustomerP3.setVisible(false);
        ClearFields();
    }

    @FXML
    private void CustomerMClicked(ActionEvent event) {
        CustomerP.setVisible(true);
        ManagerP.setVisible(false);
        CustomerP1.setVisible(true);
        CustomerP2.setVisible(false);
        CustomerP3.setVisible(false);
        Menuname.setText("Customer");
        
        ClearFields();
    }

    @FXML
    private void ManagerMClicked(ActionEvent event) {
        CustomerP.setVisible(false);
        ManagerP.setVisible(true);
        Menuname.setText("Manager/Staff");
        
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Login Dialog");
        dialog.setHeaderText("Manager/Staff Login");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
        ButtonType cancelButtonType = ButtonType.CANCEL;
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, cancelButtonType);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);
        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
        if (dialogButton == loginButtonType) {
            return new Pair<>(username.getText(), password.getText());
        }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
        });
    }

    @FXML
    private void SelectedNP(ActionEvent event) {
        //String npvalue = NPeople.getValue();
        //System.out.println(npvalue);
        if(NPeople.getValue() != null){
            Date.setDisable(false);
        }
        else{
        
        }
    }

    @FXML
    private void SelectedDate(ActionEvent event) {
        //System.out.println("1");
        if(Date.getValue() != null){
            //System.out.println(Date.getValue());
            Time.setDisable(false);
        }
    }

    @FXML
    private void SelectedTime(ActionEvent event) {
        if(Time.getValue() != null){
            CustomerDP.setDisable(false);
        }
    }

    @FXML
    private void EditedFname(KeyEvent event) {
        if(Ftextfield.getText() != null){
            Stextfield.setDisable(false);
        }   
    }

    @FXML
    private void EditedSname(KeyEvent event) {
        if(Stextfield.getText() != null){
            Ptextfield.setDisable(false);
        }
    }

    @FXML
    private void EditedP(KeyEvent event) {
        if(Ptextfield.getText() != null){
            Etextfield.setDisable(false);
        }
    }

    @FXML
    private void EditedE(KeyEvent event) {
        if(Etextfield.getText() != null){
            BCnext1.setDisable(false);
        }
    }
 
}