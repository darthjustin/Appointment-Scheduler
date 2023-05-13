package controller;

import helper.CountryQuery;
import helper.CustomerQuery;
import helper.Errors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import model.Country;
import model.Customer;
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;

/** Controller handling the methods for the Update Customer screen. */
public class UpdateCustomerController implements Initializable {

    private Stage stage;

    @FXML
    private TextField contactIdText;

    @FXML
    private TextField nameText;

    @FXML
    private TextField addressText;

    @FXML
    private ComboBox<Country> countryCombo;

    @FXML
    private ComboBox<Division> divisionCombo;

    @FXML
    private Label location;

    @FXML
    private TextField postalCodeText;

    @FXML
    private TextField phoneNumText;

    @FXML
    private Label userId;

    private static Customer updatedCustomer;

    /** Retrives the selected Customer information from the Customers screen.
     * @param customer The selected Customer object from the Customers screen. */
    public static void sendCustomer (Customer customer){
        updatedCustomer = customer;
    }

    /** Initializes the Update Customer screen and populates combo boxes.
     * Retrieves existing customer information from the selected customer
     * on the customer screen to pre-populate input fields.
     * @param url The URL of the view being loaded.
     * @param resourceBundle The ResourceBundle used to determine the Locale.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        userId.setText("User: " + LoginController.userName);
        location.setText("Location: " + ZoneId.systemDefault());
        System.out.println(updatedCustomer.getCustomerName() + " " + updatedCustomer.getDivisionId());
        contactIdText.setText(String.valueOf(updatedCustomer.getCustomerId()));
        nameText.setText(updatedCustomer.getCustomerName());
        addressText.setText(updatedCustomer.getAddress());
        postalCodeText.setText(updatedCustomer.getPostalCode());
        phoneNumText.setText(updatedCustomer.getPhoneNum());
        countryCombo.setItems(CountryQuery.getAllCountries());
        countryCombo.setValue(CountryQuery.getCountryByDivision(updatedCustomer.getDivisionId()));
        divisionCombo.setValue(CountryQuery.getDivisionByID(updatedCustomer.getDivisionId()));

    }

    /** Updates the list of available selections in the Division ComboBox.
     * When the user changes the country selection, the first level division ComboBox is updated with the relevant divisions for that country.
     * @param event The ActionEvent object created when the user changes the Country ComboBox. */
    @FXML
    void onActionSelectDivisions(ActionEvent event) {
        Country c = countryCombo.getValue();
        if (c != null){
            divisionCombo.setItems(CountryQuery.getDivisions(c));
        } else {
            divisionCombo.getSelectionModel().clearSelection();
        }
    }

    /** Cancels the current operation.
     * Confirms that the user acknowledges they will lose any inputted information and then returns to the previous screen.
     * @param event An ActionEvent object created when the user clicks the Cancel button.
     * @throws IOException Throws exception if view is not found.*/
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        if (Errors.cancelConfirmation()){
            Parent root = FXMLLoader.load(getClass().getResource("/view/customer-view.fxml"));

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.setTitle("Appointment Scheduler");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    /** Clears all fields on the Update Customer form.
     * @param event An ActionEvent object created when the user clicks the Clear button.
     */
    @FXML
    void onActionClear(ActionEvent event) {

        nameText.clear();
        addressText.clear();
        postalCodeText.clear();
        phoneNumText.clear();
        divisionCombo.getSelectionModel().clearSelection();
        countryCombo.getSelectionModel().clearSelection();

    }

    /** Attempts to save the proposed customer.
     * If the customer is valid, the customer will be saved to the database and return to the previous screen.
     * Otherwise the user will receive error messages.
     * @param event An ActionEvent object created when the user clicks the Save button.
     * @throws IOException Throws exception if view is not found. */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {

        if(Errors.saveCustomerConfirmation(nameText, addressText, postalCodeText, phoneNumText, countryCombo, divisionCombo)){
            int custID = Integer.parseInt(contactIdText.getText());
            String name = nameText.getText();
            String address = addressText.getText();
            String postal = postalCodeText.getText();
            String phone = phoneNumText.getText();
            String country = countryCombo.getSelectionModel().getSelectedItem().toString();
            Division division = divisionCombo.getSelectionModel().getSelectedItem();
            int divisionId = division.getDivisionID();
            String divName = division.getDivisionName();

            Customer customer = new Customer(custID, name, address, postal,phone, divisionId, country, divName);

            if(CustomerQuery.updateCustomer(customer) > 0){
                System.out.println("Insert Successful!");

                Parent root = FXMLLoader.load(getClass().getResource("/view/customer-view.fxml"));

                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                stage.setTitle("Appointment Scheduler");
                stage.setScene(new Scene(root));
                stage.show();

            } else {
                System.out.println("Insert Unsuccessful");
            }

        }


    }

    /** Logs the user out of the application and returns to the Login screen.
     * User will receive a Cancel confirmation message before logging out.
     * @param event An ActionEvent object created when the user clicks the Logout button.
     * @throws IOException Throws exception if view is not found. */
    @FXML
    void onActionLogout(ActionEvent event) throws IOException {

        if(Errors.cancelConfirmation()) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/login-view.fxml"));

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    /** Takes the user to the Appointments screen.
     * User will receive a Cancel confirmation message before changing scenes.
     * @param event An ActionEvent object created when the user clicks the Appointments button.
     * @throws IOException Throws exception if view is not found. */
    @FXML
    void onActionShowAppointments(ActionEvent event) throws IOException {
        if (Errors.cancelConfirmation()){
            Parent root = FXMLLoader.load(getClass().getResource("/view/appointments-view.fxml"));

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.setTitle("Appointment Scheduler");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    /** Takes the user to the Customer screen.
     * User will receive a Cancel confirmation message before changing scenes.
     * @param event An ActionEvent object created when the user clicks the Customers button.
     * @throws IOException Throws exception if view is not found. */
    @FXML
    void onActionShowCustomers(ActionEvent event) throws IOException{
        if(Errors.cancelConfirmation()){
            Parent root = FXMLLoader.load(getClass().getResource("/view/customer-view.fxml"));

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.setTitle("Appointment Scheduler");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    /** Takes the user to the Reports screen.
     * User will receive a Cancel confirmation message before changing scenes.
     * @param event An ActionEvent object created when the user clicks the Reports button.
     * @throws IOException Throws exception if view is not found. */
    @FXML
    void onActionShowReports(ActionEvent event) throws IOException {

        if(Errors.cancelConfirmation()){
            Parent root = FXMLLoader.load(getClass().getResource("/view/reports-view.fxml"));

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.setTitle("Appointment Scheduler");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

}
