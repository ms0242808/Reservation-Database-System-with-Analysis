/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RDBMSA;

import static RDBMSA.Database.getRole;
import static RDBMSA.Database.removeAccount;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
    @FXML
    private TableColumn<staffList, String> SRole;
    @FXML
    private Button BGraphic;
    @FXML
    private TextField TSearch;
    @FXML
    private Button BStaffview;
    @FXML
    private Button BCustomerview;
    @FXML
    private TextField TASearch;
    @FXML
    private Button BAAccount;
    @FXML
    private Button BRAccount;
    @FXML
    private Button BUpdate;
    @FXML
    private Button BLOut;
    @FXML
    private Button BBupdate;
    
    public static int sceneID = 0;
    private final ObservableList<customerList> CList = FXCollections.observableArrayList();;
    private static Connection connection = null;
    private static Statement statement;
    private final ObservableList<staffList> SList = FXCollections.observableArrayList();;
    FxController alertwindow = new FxController();
    public static int staffID, customerID, phone;
    public static String fn, ln, date;
    public static String address, un, pw, ro;
    public static String diner, ctime, cemail, srequest, porder;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        int LID = RDBMSA.LoginController.LogId;
        String role = getRole(LID);
        if(role.equals("M")){
            BStaffview.setVisible(true);
        } else{
            BStaffview.setVisible(false);
        }
        
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
        CustomerTable();        
        
        StaffID.setCellValueFactory(new PropertyValueFactory("StaffID"));
        SFname.setCellValueFactory(new PropertyValueFactory("FirstName"));
        SLname.setCellValueFactory(new PropertyValueFactory("SurName"));
        SDob.setCellValueFactory(new PropertyValueFactory("DOB"));
        SPhone.setCellValueFactory(new PropertyValueFactory("Pnumber"));
        SAddress.setCellValueFactory(new PropertyValueFactory("SA"));
        SUsername.setCellValueFactory(new PropertyValueFactory("SU"));
        SPassword.setCellValueFactory(new PropertyValueFactory("SPW"));
        SRole.setCellValueFactory(new PropertyValueFactory("SROLE"));
        
        if(sceneID == 1){
            sceneID = 0;
            DetailAccountP.setVisible(true);
            DetailCustomerP.setVisible(false);
            StaffListTable();
        } else{
            sceneID = 0;
            DetailAccountP.setVisible(false);
            DetailCustomerP.setVisible(true);
        }
    } 
        
    public void loadScenePane(String SceneName){
        try {
            Parent root1 = FXMLLoader.load(getClass().getResource(SceneName));
            SceneP.getChildren().setAll(root1);
        } catch (IOException ex) {
            Logger.getLogger(ManageController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                                           rs2.getString("PassWord"),
                                           rs2.getString("ROLE")));// add to table
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
    
    @FXML
    private void BGraphicClicked(MouseEvent event) {    
        StatisticsController controller = new StatisticsController();
        //controller.setPersonData(CList);
        loadScenePane("Statistics.fxml");
    }

    @FXML
    private void TSearchRekeased(KeyEvent event) {
        //TSearch.setText(null);
        
        FilteredList<customerList> filteredData = new FilteredList<>(CList, e -> true);
        TSearch.setOnKeyReleased(e -> {
        TSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super customerList>) bookings -> {
                if (newValue == null || newValue.isEmpty())
                {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (bookings.getFirstName().toLowerCase().contains(lowerCaseFilter))
                {
                    return true;
                }
                else if (bookings.getSurName().toLowerCase().contains(lowerCaseFilter))
                {
                        return true;
                } 
                else if (bookings.getCcode().toLowerCase().contains(lowerCaseFilter))
                {
                    return true;
                }     
 
            return false;
            });
        });
    }); 
        SortedList<customerList> sortedResults = new SortedList<>(filteredData);
        sortedResults.comparatorProperty().bind(CustomerTable.comparatorProperty());
        CustomerTable.setItems(sortedResults);
    }

    @FXML
    private void BStaffClicked(MouseEvent event) {
        DetailCustomerP.setVisible(false);
        DetailAccountP.setVisible(true);
        StaffListTable();
    }

    @FXML
    private void TASearchReleased(KeyEvent event) {
        //TASearch.setText(null);
        
        FilteredList<staffList> filteredData = new FilteredList<>(SList, e -> true);
        TASearch.setOnKeyReleased(e -> {
        TASearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super staffList>) accounts -> {
                if (newValue == null || newValue.isEmpty())
                {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (accounts.getFirstName().toLowerCase().contains(lowerCaseFilter))
                {
                    return true;
                }
                else if (accounts.getSurName().toLowerCase().contains(lowerCaseFilter))
                {
                        return true;
                } 
                else if (accounts.getSU().toLowerCase().contains(lowerCaseFilter))
                {
                    return true;
                }     
 
            return false;
            });
        });
    }); 
        SortedList<staffList> sortedResults = new SortedList<>(filteredData);
        sortedResults.comparatorProperty().bind(StaffTable.comparatorProperty());
        StaffTable.setItems(sortedResults);
    }

    @FXML
    private void BCustomerClicked(MouseEvent event) {
        DetailCustomerP.setVisible(true);
        DetailAccountP.setVisible(false);
    }

    @FXML
    private void BAAccountClicked(MouseEvent event) {
        loadScenePane("AddAccount.fxml");
    }

    @FXML
    private void BRAccountClciked(MouseEvent event) {
        int selectedIndex = StaffTable.getSelectionModel().getSelectedIndex() + 1;
        if (selectedIndex >= 1) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Delete selected account");
            alert.setHeaderText("Update account database");
            alert.setContentText("Are you sure you want to delete this?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                int SID = StaffTable.getSelectionModel().getSelectedItem().getStaffID();
                StaffTable.getItems().remove(selectedIndex - 1);
                removeAccount(SID);
            }
        } else{
            alertwindow.AlertWarningwindow(null, null, "Please select a person in the table.");
        }
    }

    @FXML
    private void BUpdateClicked(MouseEvent event) {
        int selectedIndex = StaffTable.getSelectionModel().getSelectedIndex() + 1;
        if (selectedIndex >= 1){
            staffID = StaffTable.getSelectionModel().getSelectedItem().getStaffID();
            fn = StaffTable.getSelectionModel().getSelectedItem().getFirstName();
            ln = StaffTable.getSelectionModel().getSelectedItem().getSurName();
            date = StaffTable.getSelectionModel().getSelectedItem().getDOB();
            phone = StaffTable.getSelectionModel().getSelectedItem().getPnumber();
            address = StaffTable.getSelectionModel().getSelectedItem().getSA();
            un = StaffTable.getSelectionModel().getSelectedItem().getSU();
            pw = StaffTable.getSelectionModel().getSelectedItem().getSPW();
            ro = StaffTable.getSelectionModel().getSelectedItem().getSROLE();
            loadScenePane("UpdateAccount.fxml");
        } else{
            alertwindow.AlertWarningwindow(null, null, "Please select a person in the table.");
        } 
    }

    @FXML
    private void BLoutClicked(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            loadScenePane("Login.fxml");
        } else{
            // ... user chose CANCEL or closed the dialog
        } 
    }

    @FXML
    private void BBUpdateClicked(MouseEvent event) {
        int selectedIndex = CustomerTable.getSelectionModel().getSelectedIndex() + 1;
        if (selectedIndex >= 1){
            customerID = CustomerTable.getSelectionModel().getSelectedItem().getCustomerID();
            fn = CustomerTable.getSelectionModel().getSelectedItem().getFirstName();
            ln = CustomerTable.getSelectionModel().getSelectedItem().getSurName();
            diner = CustomerTable.getSelectionModel().getSelectedItem().getNumberofdiner();
            date = CustomerTable.getSelectionModel().getSelectedItem().getBdate();
            ctime = CustomerTable.getSelectionModel().getSelectedItem().getBtime();
            phone = CustomerTable.getSelectionModel().getSelectedItem().getPnumber();
            cemail = CustomerTable.getSelectionModel().getSelectedItem().getEmail();
            srequest = CustomerTable.getSelectionModel().getSelectedItem().getSrequest();
            porder = CustomerTable.getSelectionModel().getSelectedItem().getPorder();
            loadScenePane("UpdateBooking.fxml");
        } else{
            alertwindow.AlertWarningwindow(null, null, "Please select a person in the table.");
        }
    }
}
