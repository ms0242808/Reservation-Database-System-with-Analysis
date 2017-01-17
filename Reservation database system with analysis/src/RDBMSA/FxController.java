/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RDBMSA;

import static RDBMSA.Database.booktable;
import static RDBMSA.Database.userCheck;
import static RDBMSA.Database.passwordCheck;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
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
    @FXML
    private Menu GeneralMenu;
    @FXML
    private Menu AccountMenu;
    @FXML
    private MenuItem CRMenu;
    @FXML
    private MenuItem SDMenu;
    @FXML
    private ProgressBar ProgBar;
    @FXML
    private Button BCBack;
    @FXML
    private AnchorPane DetailCustomerP;
    @FXML
    private AnchorPane DetailAccountP;
    @FXML
    private TableView<customerList> CustomerTable;
    @FXML
    private TableColumn<customerList, Integer> cID;
    @FXML
    private TableColumn<customerList, String> Fname;
    @FXML
    private TableColumn<customerList, String> Lname;
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
    
    private final ObservableList<customerList> CList = FXCollections.observableArrayList();;
    private static Connection connection = null;
    private static Statement statement;

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
    
    private final ObservableList<staffList> SList = FXCollections.observableArrayList();;
    @FXML
    private TableColumn<?, ?> SPassword;
    
    public static Stage LoginStage;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Exitmenu.setOnAction(actionEvent -> Platform.exit());
        valiadteDate();
        
        SetCustomerVisibles();
        
        SetDisables();

        ClearFields();
        
        NPeople.getItems().addAll("1","2","3","4","5","6","7","8","9","10");
        Time.getItems().addAll("12.00","12.30","13.00");
        
        //ProgBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS); // running automiac repeatly
        
        cID.setCellValueFactory(new PropertyValueFactory("CustomerID"));
        Fname.setCellValueFactory(new PropertyValueFactory("FirstName"));
        Lname.setCellValueFactory(new PropertyValueFactory("SurName"));
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
    }    

    public void SetCustomerVisibles(){
        CustomerP.setVisible(true);
        ManagerP.setVisible(false);
        CustomerP1.setVisible(true);
        CustomerP2.setVisible(false);
        CustomerP3.setVisible(false);
        GeneralMenu.setText("Customer");
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
    
    @FXML
    private void BCNextClicked(MouseEvent event) {
        CustomerP1.setVisible(false);
        CustomerP2.setVisible(true);
        CustomerP3.setVisible(false);

        updateProgBar(3);
        
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
    
    private boolean valiateFirstname(){
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(Ftextfield.getText());
        if(m.find() && m.group().equals(Ftextfield.getText())){
            return true;
        }else{
            Alert alert = new Alert(AlertType.WARNING);
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
            Alert alert = new Alert(AlertType.WARNING);
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
            Alert alert = new Alert(AlertType.WARNING);
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
            Alert alert = new Alert(AlertType.WARNING);
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
    private void BCBackClicked(MouseEvent event) {
        CustomerP1.setVisible(true);
        CustomerP2.setVisible(false);
        CustomerP3.setVisible(false);
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
        
        BCSee.setText("See you at " + Dt + " , " + T);
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
    private void BCseeClicked(MouseEvent event) {
        CustomerP1.setVisible(true);
        CustomerP2.setVisible(false);
        CustomerP3.setVisible(false);
        ProgBar.progressProperty().unbind();
        ProgBar.setProgress(0);
        ClearFields();
        SetDisables();
    }

    @FXML
    private void CustomerMClicked(ActionEvent event) {       
        SetCustomerVisibles();
        ClearFields();
        SetDisables();
    }

    @FXML
    private void ManagerMClicked(ActionEvent event) throws IOException {
        CustomerP.setVisible(false);        
        GeneralMenu.setText("Manager/Staff");
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
        //loginDialog();
        return null;
        });
        
        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(usernamePassword -> {
            //System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
            String user = username.getText().toString();
            String pass = password.getText().toString();
            System.out.println(user + " " + pass);
            try{
                int login = 0;
                int loginPass = 0;            
                login = userCheck(user);
                System.out.println(login);
                loginPass = passwordCheck(login, pass);
                System.out.println(user + " "+loginPass);            
                if (loginPass < 0){
                    AccountMenu.setVisible(true);
                    ManagerP.setVisible(true);
                    CustomerTable();
                    System.out.println("Success!");
                }
                else{
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
            while (rs2.next())
            {
                CList.add(new customerList(rs2.getInt("CustomerID"), 
                                           rs2.getString("Firstname"),
                                           rs2.getString("Lastname"),
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
            while (rs2.next())
            {
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

    @FXML
    private void CRMenuClicked(ActionEvent event) {
        DetailCustomerP.setVisible(true);
        DetailAccountP.setVisible(false);
        CustomerTable();
    }

    @FXML
    private void SDMenuClicked(ActionEvent event) {
        DetailCustomerP.setVisible(false);
        DetailAccountP.setVisible(true);
        StaffListTable();
    }
 
}