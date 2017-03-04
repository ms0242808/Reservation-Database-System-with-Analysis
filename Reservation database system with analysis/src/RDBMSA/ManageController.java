/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RDBMSA;

import static RDBMSA.Database.passwordCheck;
import static RDBMSA.Database.userCheck;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * FXML Controller class
 *
 * @author jerry
 */
public class ManageController implements Initializable {
    @FXML
    private AnchorPane SceneP;
    @FXML
    private AnchorPane DetailCustomerP;
    @FXML
    private TableView<customerList> CustomerTable;
    @FXML
    private TableColumn<customerList, Integer> cID;
    @FXML
    private TableColumn<customerList, String> Fname;
    @FXML
    private TableColumn<customerList, String> Lname;
    @FXML
    private TableColumn<customerList, String> NoDiner;
    @FXML
    private TableColumn<customerList, String> BDate;
    @FXML
    private TableColumn<customerList, String> BTime;
    @FXML
    private TableColumn<customerList, String> Pnumber;
    @FXML
    private TableColumn<customerList, String> eMail;
    @FXML
    private TableColumn<customerList, String> SRequest;
    @FXML
    private TableColumn<customerList, String> POrder;
    @FXML
    private TableColumn<customerList, String> Ccode;
    @FXML
    private DatePicker sortDate;
    @FXML
    private AnchorPane DetailAccountP;
    @FXML
    private TableView<staffList> StaffTable;
    @FXML
    private TableColumn<staffList, Integer> StaffID;
    @FXML
    private TableColumn<staffList, String> SFname;
    @FXML
    private TableColumn<staffList, String> SLname;
    @FXML
    private TableColumn<staffList, String> SDob;
    @FXML
    private TableColumn<staffList, Integer> SPhone;
    @FXML
    private TableColumn<staffList, String> SAddress;
    @FXML
    private TableColumn<staffList, String> SUsername;
    @FXML
    private TableColumn<staffList, String> SPassword;

    private final ObservableList<customerList> CList = FXCollections.observableArrayList();;
    private static Connection connection = null;
    private static Statement statement;
    private final ObservableList<staffList> SList = FXCollections.observableArrayList();;
    public static Stage LoginStage;    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        cID.setCellValueFactory(new PropertyValueFactory("CustomerID"));
        Fname.setCellValueFactory(new PropertyValueFactory("FirstName"));
        Lname.setCellValueFactory(new PropertyValueFactory("SurName"));
        NoDiner.setCellValueFactory(new PropertyValueFactory("Numberofdiner"));
        BDate.setCellValueFactory(new PropertyValueFactory("Bdate"));
        BTime.setCellValueFactory(new PropertyValueFactory("Btime"));
        Pnumber.setCellValueFactory(new PropertyValueFactory("Pnumber"));
        eMail.setCellValueFactory(new PropertyValueFactory("Email"));
        SRequest.setCellValueFactory(new PropertyValueFactory("Srequest"));
        POrder.setCellValueFactory(new PropertyValueFactory("Porder"));
        Ccode.setCellValueFactory(new PropertyValueFactory("Ccode"));
        
        StaffID.setCellValueFactory(new PropertyValueFactory("StaffID"));
        SFname.setCellValueFactory(new PropertyValueFactory("FirstName"));
        SLname.setCellValueFactory(new PropertyValueFactory("SurName"));
        SDob.setCellValueFactory(new PropertyValueFactory("DOB"));
        SPhone.setCellValueFactory(new PropertyValueFactory("Pnumber"));
        SAddress.setCellValueFactory(new PropertyValueFactory("SA"));
        SUsername.setCellValueFactory(new PropertyValueFactory("SU"));
        SPassword.setCellValueFactory(new PropertyValueFactory("SPW"));
        loginDialog();
    }    
    public void loginDialog(){                
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Manager/Staff Login");
        dialog.setHeaderText("Manager/Staff Login");
        
        // Set the icon (must be included in the project).
        //dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
        
        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);    
        
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
        
        // Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);
        
        // Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());
        //dialog.showAndWait();
        
        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
        if (dialogButton == loginButtonType) {
            return new Pair<>(username.getText(), password.getText());
        }
        //SetCustomerVisibles();
        try {
            //SetCustomerVisibles();
            //ClearFields();
            //SetDisables();
            Parent root1 = FXMLLoader.load(getClass().getResource("Booking.fxml"));
            SceneP.getChildren().setAll(root1);
        } catch (IOException ex) {
            Logger.getLogger(FxController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        });
        
        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(usernamePassword -> {
            //System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
            String user = username.getText();
            String pass = password.getText();
            System.out.println(user + " " + pass);
            try{
                int login = 0;
                int loginPass = 0;            
                login = userCheck(user);
                System.out.println(login);
                loginPass = passwordCheck(login, pass);
                System.out.println(user + " "+loginPass);            
                if (loginPass < 0){
                    //AccountMenu.setVisible(true);
                    SceneP.setVisible(true);
                    CustomerTable();
                    System.out.println("Success!");
                }
                else{
                    //check if its failed to log in, alert
                    System.out.println("Try again.");
                }   
            } catch(NullPointerException e){
                System.out.println("Catched");
            }
        });
    }
        
    public void CustomerTable(){
        CList.clear();
        try{
            String CustomerQuery = "SELECT * from customer";
            ResultSet rs2 = Database.RetSet(CustomerQuery);
            while (rs2.next()){
                CList.add(new customerList(rs2.getInt("CustomerID"), 
                                           rs2.getString("Firstname"),
                                           rs2.getString("Lastname"),
                                           rs2.getString("NumberOfDiner"),
                                           rs2.getString("Date"),
                                           rs2.getString("Time"),
                                           rs2.getInt("Phone"),
                                           rs2.getString("Email"),
                                           rs2.getString("AdditionalRequest"),
                                           rs2.getString("PreOrder"),
                                           rs2.getString("ConfirmCode")));
                CustomerTable.setItems(this.CList);
            }
        }
        catch (Exception e)
        {
            //System.out.println(e);
        } 
        CustomerTable.setItems(CList); 
        Database.close();
    }
    
    public void StaffListTable(){       
        SList.clear();
        try{
            String StaffQuery = "SELECT * from account";
            ResultSet rs2 = Database.RetSet(StaffQuery);
            while (rs2.next()){
                SList.add(new staffList(rs2.getInt("AccountID"), 
                                           rs2.getString("FirstName"),
                                           rs2.getString("LastName"),
                                           rs2.getString("DateOfBirth"),
                                           rs2.getInt("Phone"),
                                           rs2.getString("Address"),
                                           rs2.getString("UserName"),
                                           rs2.getString("PassWord")));
                StaffTable.setItems(this.SList);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        } 
        StaffTable.setItems(SList); 
        Database.close();
    }
    
    public void CustomerSortTable(String sorttype, String sortinput){
        CList.clear();
        try{            
            String CustomerQuery = "SELECT * from customer WHERE '"+ sorttype +"' = '"+ sortinput +"'";
            ResultSet rs2 = Database.RetSet(CustomerQuery);
            while (rs2.next()){
                CList.add(new customerList(rs2.getInt("CustomerID"), 
                                           rs2.getString("Firstname"),
                                           rs2.getString("Lastname"),
                                           rs2.getString("NumberOfDiner"),
                                           rs2.getString("Date"),
                                           rs2.getString("Time"),
                                           rs2.getInt("Phone"),
                                           rs2.getString("Email"),
                                           rs2.getString("AdditionalRequest"),
                                           rs2.getString("PreOrder"),
                                           rs2.getString("ConfirmCode")));
                CustomerTable.setItems(this.CList);
            }
        }
        catch (Exception e)
        {
            //System.out.println(e);
        } 
        CustomerTable.setItems(CList); 
        Database.close();
    }
}
