/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RDBMSA;

import static RDBMSA.Database.getDate;
import static RDBMSA.Database.getEmail;
import static RDBMSA.Database.getRole;
import static RDBMSA.Database.removeAccount;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private Button BStaffview;
    @FXML
    private TextField TASearch;
    @FXML
    private Button BAAccount;
    @FXML
    private Button BRAccount;
    @FXML
    private Button BLOut;
    
    public static int sceneID = 2;
    public static int accountStage = 0;
    private final ObservableList<customerList> CList = FXCollections.observableArrayList();;
    private static Connection connection = null;
    private static Statement statement;
    private final ObservableList<staffList> SList = FXCollections.observableArrayList();;
    FxController alertwindow = new FxController();
    public static int staffID, customerID, diner;
    public static String fn, ln, date, pnumber;
    public static String address, un, pw, ro;
    public static String ctime, cemail, srequest, porder;
    private final ObservableList<customerList> todayList = FXCollections.observableArrayList();;
    
    @FXML
    private AnchorPane TodaysBookedP;
    @FXML
    private TableView<customerList> TodaysBTable;
    @FXML
    private TableColumn<customerList, String> Todayfname;
    @FXML
    private TableColumn<customerList, String> Todaysname;
    @FXML
    private TableColumn<customerList, Integer> Todaydiners;
    @FXML
    private TableColumn<customerList, String> Todaytime;
    @FXML
    private TableColumn<customerList, String> Todayphone;
    @FXML
    private TableColumn<customerList, String> Todayrequest;
    @FXML
    private TableColumn<customerList, String> Todaypreorder;
    @FXML
    private JFXTextField TTodaySearch;
    @FXML
    private JFXButton BCupdate;
    @FXML
    private JFXButton BCToday;
    @FXML
    private JFXButton BCStaffview;
    @FXML
    private JFXButton BAUpdate;
    @FXML
    private JFXButton BAToday;
    @FXML
    private JFXButton BAAll;
    @FXML
    private JFXButton BTAll;
    @FXML
    private JFXButton BTUpdate;
    @FXML
    private JFXButton BTStaff;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        int LID = RDBMSA.LoginController.LogId;
        String role = getRole(LID);
        if(role.equals("M")){
            BCStaffview.setVisible(true);
        } else{
            BCStaffview.setVisible(false);
        }
        
        if(sceneID == 2){
            sceneID = 2;
            DetailAccountP.setVisible(false);
            DetailCustomerP.setVisible(false);
            TodaysBookedP.setVisible(true);
            TodaysBookedTable();
        } else if(sceneID == 1){
            sceneID = 1;
            DetailAccountP.setVisible(true);
            DetailCustomerP.setVisible(false);
            TodaysBookedP.setVisible(false);
            StaffListTable();
        }else{
            sceneID = 0;
            DetailAccountP.setVisible(false);
            DetailCustomerP.setVisible(true);
            TodaysBookedP.setVisible(false);
            CustomerTable();
        }
        
        Todayfname.setCellValueFactory(new PropertyValueFactory("FirstName"));
        Todaysname.setCellValueFactory(new PropertyValueFactory("SurName"));
        Todaydiners.setCellValueFactory(new PropertyValueFactory("Numberofdiner"));
        Todaytime.setCellValueFactory(new PropertyValueFactory("Btime"));
        Todayphone.setCellValueFactory(new PropertyValueFactory("Pnumber"));
        Todayrequest.setCellValueFactory(new PropertyValueFactory("Srequest"));
        Todaypreorder.setCellValueFactory(new PropertyValueFactory("Porder"));
        
        
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
        //CustomerTable();        
        
        StaffID.setCellValueFactory(new PropertyValueFactory("StaffID"));
        SFname.setCellValueFactory(new PropertyValueFactory("FirstName"));
        SLname.setCellValueFactory(new PropertyValueFactory("SurName"));
        SDob.setCellValueFactory(new PropertyValueFactory("DOB"));
        SPhone.setCellValueFactory(new PropertyValueFactory("Pnumber"));
        SAddress.setCellValueFactory(new PropertyValueFactory("SA"));
        SUsername.setCellValueFactory(new PropertyValueFactory("SU"));
        SPassword.setCellValueFactory(new PropertyValueFactory("SPW"));
        SRole.setCellValueFactory(new PropertyValueFactory("SROLE"));
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
                                           rs2.getInt("NumberOfDiner"),
                                           rs2.getString("Date"),
                                           rs2.getString("Time"),
                                           rs2.getString("Phone"),
                                           rs2.getString("Email"),
                                           rs2.getString("AdditionalRequest"),
                                           rs2.getString("PreOrder"),
                                           rs2.getString("ConfirmCode")));
                CustomerTable.setItems(this.CList);
            }
        }catch (Exception e){
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
                                           rs2.getString("Phone"),
                                           rs2.getString("Address"),
                                           rs2.getString("UserName"),
                                           rs2.getString("PassWord"),
                                           rs2.getString("ROLE")));// add to table
                StaffTable.setItems(this.SList);
            }
        }catch (Exception e){
            System.out.println(e);
        } 
        StaffTable.setItems(SList); 
        Database.close();
    }
    
    public void TodaysBookedTable(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date de = new Date();
        String today = dateFormat.format(de);
        todayList.clear();
        try{
            String CustomerQuery = "SELECT * from customer WHERE Date  ='" + today + "'";
            ResultSet rs2 = Database.RetSet(CustomerQuery);
            while (rs2.next()){
                todayList.add(new customerList(rs2.getInt("CustomerID"), 
                                           rs2.getString("Firstname"),
                                           rs2.getString("Lastname"),
                                           rs2.getInt("NumberOfDiner"),
                                           rs2.getString("Date"),
                                           rs2.getString("Time"),
                                           rs2.getString("Phone"),
                                           rs2.getString("Email"),
                                           rs2.getString("AdditionalRequest"),
                                           rs2.getString("PreOrder"),
                                           rs2.getString("ConfirmCode")));
                TodaysBTable.setItems(this.todayList);
            }
        }catch (Exception e){
            //System.out.println(e);
        } 
        TodaysBTable.setItems(todayList); 
        Database.close();
    }

    @FXML
    private void BGraphicClicked(MouseEvent event) throws IOException {
        loadScenePane("Statistics.fxml");
    }

    @FXML
    private void TSearchRekeased(KeyEvent event) {
        //TSearch.setText(null);
        FilteredList<customerList> filteredData = new FilteredList<>(CList, e -> true);
        TSearch.setOnKeyReleased(e -> {
            TSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super customerList>) bookings -> {
                    if (newValue == null || newValue.isEmpty()){
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (bookings.getFirstName().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    } else if (bookings.getSurName().toLowerCase().contains(lowerCaseFilter)){
                            return true;
                    } else if (bookings.getCcode().toLowerCase().contains(lowerCaseFilter)){
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
    private void BCStaffClicked(MouseEvent event) {
        sceneID = 1;
        DetailAccountP.setVisible(true);
        DetailCustomerP.setVisible(false);
        TodaysBookedP.setVisible(false);
        StaffListTable();
    }

    @FXML
    private void TASearchReleased(KeyEvent event) {
        //TASearch.setText(null);
        FilteredList<staffList> filteredData = new FilteredList<>(SList, e -> true);
        TASearch.setOnKeyReleased(e -> {
            TASearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super staffList>) accounts -> {
                    if (newValue == null || newValue.isEmpty()){
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (accounts.getFirstName().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    } else if (accounts.getSurName().toLowerCase().contains(lowerCaseFilter)){
                            return true;
                    } else if (accounts.getSU().toLowerCase().contains(lowerCaseFilter)){
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
    private void BAAllClicked(MouseEvent event) {
        sceneID = 0;
        DetailAccountP.setVisible(false);
        DetailCustomerP.setVisible(true);
        TodaysBookedP.setVisible(false);
        CustomerTable();
    }

    @FXML
    private void BAAccountClicked(MouseEvent event) {
        accountStage = 0;
        loadScenePane("UpdateAccount.fxml");
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
    private void BAUpdateClicked(MouseEvent event) {
        int selectedIndex = StaffTable.getSelectionModel().getSelectedIndex() + 1;
        if (selectedIndex >= 1){
            accountStage = 1;
            staffID = StaffTable.getSelectionModel().getSelectedItem().getStaffID();
            fn = StaffTable.getSelectionModel().getSelectedItem().getFirstName();
            ln = StaffTable.getSelectionModel().getSelectedItem().getSurName();
            date = StaffTable.getSelectionModel().getSelectedItem().getDOB();
            pnumber = StaffTable.getSelectionModel().getSelectedItem().getPnumber();
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
    private void BCUpdateClicked(MouseEvent event) {
        int selectedIndex = CustomerTable.getSelectionModel().getSelectedIndex() + 1;
        if (selectedIndex >= 1){
            customerID = CustomerTable.getSelectionModel().getSelectedItem().getCustomerID();
            fn = CustomerTable.getSelectionModel().getSelectedItem().getFirstName();
            ln = CustomerTable.getSelectionModel().getSelectedItem().getSurName();
            diner = CustomerTable.getSelectionModel().getSelectedItem().getNumberofdiner();
            date = CustomerTable.getSelectionModel().getSelectedItem().getBdate();
            ctime = CustomerTable.getSelectionModel().getSelectedItem().getBtime();
            pnumber = CustomerTable.getSelectionModel().getSelectedItem().getPnumber();
            cemail = CustomerTable.getSelectionModel().getSelectedItem().getEmail();
            srequest = CustomerTable.getSelectionModel().getSelectedItem().getSrequest();
            porder = CustomerTable.getSelectionModel().getSelectedItem().getPorder();
            loadScenePane("UpdateBooking.fxml");
        } else{
            alertwindow.AlertWarningwindow(null, null, "Please select a person in the table.");
        }
    }

    @FXML
    private void TTodaySearchReleased(KeyEvent event) {
        FilteredList<customerList> filteredData = new FilteredList<>(todayList, e -> true);
        TTodaySearch.setOnKeyReleased(e -> {
            TTodaySearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super customerList>) todayBooked -> {
                    if (newValue == null || newValue.isEmpty()){
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (todayBooked.getFirstName().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    } else if (todayBooked.getSurName().toLowerCase().contains(lowerCaseFilter)){
                            return true;
                    } else if (todayBooked.getPnumber().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }
                        return false;
                });
            });
        }); 
        SortedList<customerList> sortedResults = new SortedList<>(filteredData);
        sortedResults.comparatorProperty().bind(TodaysBTable.comparatorProperty());
        TodaysBTable.setItems(sortedResults);
    }

    @FXML
    private void BCTodayClicked(MouseEvent event) {
        sceneID = 2;
        DetailAccountP.setVisible(false);
        DetailCustomerP.setVisible(false);
        TodaysBookedP.setVisible(true);
        TodaysBookedTable();
    }

    @FXML
    private void BATodayClicked(MouseEvent event) {
        sceneID = 2;
        DetailAccountP.setVisible(false);
        DetailCustomerP.setVisible(false);
        TodaysBookedP.setVisible(true);
        TodaysBookedTable();
    }

    @FXML
    private void BTAllClicked(MouseEvent event) {
        sceneID = 0;
        DetailAccountP.setVisible(false);
        DetailCustomerP.setVisible(true);
        TodaysBookedP.setVisible(false);
        CustomerTable();
    }

    @FXML
    private void BTUpdateClicked(MouseEvent event) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date de = new Date();
        String today = dateFormat.format(de);
        int selectedIndex = TodaysBTable.getSelectionModel().getSelectedIndex() + 1;
        if (selectedIndex >= 1){
            customerID = TodaysBTable.getSelectionModel().getSelectedItem().getCustomerID();
            fn = TodaysBTable.getSelectionModel().getSelectedItem().getFirstName();
            ln = TodaysBTable.getSelectionModel().getSelectedItem().getSurName();
            date = getDate(customerID);
            diner = TodaysBTable.getSelectionModel().getSelectedItem().getNumberofdiner();
            ctime = TodaysBTable.getSelectionModel().getSelectedItem().getBtime();
            pnumber = TodaysBTable.getSelectionModel().getSelectedItem().getPnumber();
            cemail = getEmail(customerID);
            srequest = TodaysBTable.getSelectionModel().getSelectedItem().getSrequest();
            porder = TodaysBTable.getSelectionModel().getSelectedItem().getPorder();
            loadScenePane("UpdateBooking.fxml");
        } else{
            alertwindow.AlertWarningwindow(null, null, "Please select a person in the table.");
        }
    }

    @FXML
    private void BTStaffClicked(MouseEvent event) {
        sceneID = 1;
        DetailAccountP.setVisible(true);
        DetailCustomerP.setVisible(false);
        TodaysBookedP.setVisible(false);
        StaffListTable();
    }
}
