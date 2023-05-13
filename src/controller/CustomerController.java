package controller;

import helper.CustomerQuery;
import helper.Errors;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;

/** Controller handling the methods for the Customer screen. */
public class CustomerController implements Initializable {

    Stage stage;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, Integer> custIdCol;

    @FXML
    private TableColumn<Customer, String> custNameCol;

    @FXML
    private TableColumn<Customer, String> addressCol;

    @FXML
    private TableColumn<Customer, Integer> postalCodeCol;

    @FXML
    private TableColumn<Customer, String> phoneNumCol;

    @FXML
    private TableColumn<Customer, String> divisionCol;

    @FXML
    private TableColumn<Customer, String> countryCol;

    @FXML
    private Label location;

    @FXML
    private Label userId;

    /** Logs the user out of the application and returns to the Login screen.
     * @param event An ActionEvent object created when the user clicks the Logout button.
     * @throws IOException Throws exception if view is not found. */
    @FXML
    void onActionLogout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/login-view.fxml"));

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /** Takes the user to the Appointments screen.
     * @param event An ActionEvent object created when the user clicks the Appointments button.
     * @throws IOException Throws exception if view is not found. */
    @FXML
    void onActionShowAppointments(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/appointments-view.fxml"));

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("Appointment Scheduler");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /** Takes the user to the Customers screen.
     * @param event An ActionEvent object created when the user clicks the Customers button.
     * @throws IOException Throws exception if view is not found. */
    @FXML
    void onActionShowCustomers(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/customer-view.fxml"));

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("Appointment Scheduler");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /** Takes the user to the Reports screen.
     * @param event An ActionEvent object created when the user clicks the Reports button.
     * @throws IOException Throws exception if view is not found. */
    @FXML
    void onActionShowReports(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/reports-view.fxml"));

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("Appointment Scheduler");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /** Takes the user to the Add Customer screen.
     * @param event An ActionEvent object created when the user clicks the Add button.
     * @throws IOException Throws exception if view is not found. */
    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addCustomer-view.fxml"));

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("Appointment Scheduler");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /** Takes the user to the Update Customer screen.
     * Retrieves information from the currently selected customer in the table view.
     * If no customer is currently selected, the user will get a message asking them to select an customer.
     * @param event An ActionEvent object created when the user clicks the Update button.
     * @throws IOException Throws exception if view is not found. */
    @FXML
    void onActionUpdateCustomer(ActionEvent event) throws IOException {
        if(customerTableView.getSelectionModel().getSelectedItem() != null){
            Customer customer = customerTableView.getSelectionModel().getSelectedItem();
            UpdateCustomerController.sendCustomer(customer);

            Parent root = FXMLLoader.load(getClass().getResource("/view/updateCustomer-view.fxml"));

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.setTitle("Appointment Scheduler");
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a customer.");
            alert.show();
        }

    }

    /** Deletes the selected customer from the database.
     * Retrieves information from the currently selected customer in the table view.
     * If no customer is currently selected, the user will get a message asking them to select an customer.
     * Displays a message with the Appointment Id and the Type of appointment that was deleted.
     * @param event An ActionEvent object created when the user clicks the Update button.
     * @throws IOException Throws exception if view is not found. */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) {

        if (customerTableView.getSelectionModel().getSelectedItem() != null){

            Customer customer = customerTableView.getSelectionModel().getSelectedItem();

            if (CustomerQuery.apptsByCustID(customer.getCustomerId()).size() == 0){

                if (CustomerQuery.deleteCustomer(customerTableView.getSelectionModel().getSelectedItem()) > 0){
                    System.out.println("Delete Successful");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Customer ID: " + customer.getCustomerId() + " | Customer Name: " + customer.getCustomerName());
                    alert.setHeaderText("Customer deleted.");
                    alert.setTitle("Customer deleted.");
                    alert.showAndWait();
                    customerTableView.setItems(CustomerQuery.getAllCustomers());
                } else {
                    System.out.println("Delete Unsuccessful");
                }
            }else{
                Errors.customerHasAppointments();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a customer.");
            alert.show();
        }
    }

    /** Initializes the Customer screen and pre-populates the TableView.
     * @param url The URL of the view being loaded.
     * @param resourceBundle The ResourceBundle used to determine the Locale.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Customer> customerList = CustomerQuery.getAllCustomers();
        customerTableView.setItems(customerList);

        userId.setText("User: " + LoginController.userName);
        location.setText("Location: " + ZoneId.systemDefault());
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneNumCol.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));

    }
}
