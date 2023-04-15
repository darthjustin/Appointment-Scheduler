package controller;

import helper.CustomerQuery;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TableView<Customers> customerTableView;

    @FXML
    private TableColumn<Customers, Integer> custIdCol;

    @FXML
    private TableColumn<Customers, String> custNameCol;

    @FXML
    private TableColumn<Customers, String> addressCol;

    @FXML
    private TableColumn<Customers, Integer> postalCodeCol;

    @FXML
    private TableColumn<Customers, String> phoneNumCol;

    @FXML
    private TableColumn<Customers, Date> createDateCol;

    @FXML
    private TableColumn<Customers, String> createdByCol;

    @FXML
    private TableColumn<Customers, String> lastUpdatedCol;

    @FXML
    private TableColumn<Customers, String> lastUpdatedByCol;

    @FXML
    private TableColumn<Customers, String> divisionCol;

    @FXML
    private TableColumn<Customers, String> countryCol;

    @FXML
    private Label location;

    @FXML
    private Button logoutButton;

    @FXML
    private Button appointmentsButton;

    @FXML
    private Button contactsButton;

    @FXML
    private Button reportsButton;

    @FXML
    private Label userId;

    @FXML
    void onActionLogout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/login-view.fxml"));

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void onActionShowAppointments(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/calendar-view.fxml"));

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("Appointment Scheduler");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void onActionShowContacts(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/customer-view.fxml"));

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("Appointment Scheduler");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void onActionShowReports(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/reports-view.fxml"));

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("Appointment Scheduler");
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    void onActionAddCustomer(ActionEvent event) {

    }

    @FXML
    void onActionUpdateCustomer(ActionEvent event) {

    }

    @FXML
    void onActionDeleteCustomer(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Customers> customerList = CustomerQuery.getAllCustomers();
        customerTableView.setItems(customerList);

        custIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneNumCol.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        createDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdatedCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdated"));
        lastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));

    }
}
