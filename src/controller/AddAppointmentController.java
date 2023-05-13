package controller;

import helper.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointments;
import model.Contact;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ResourceBundle;

/** Controller handling the methods for the Add Appointment screen. */
public class AddAppointmentController implements Initializable {

    private Stage stage;

    @FXML
    private TextField appointmentIdText;

    @FXML
    private TextField titleText;

    @FXML
    private TextField descriptionText;

    @FXML
    private TextField locationText;

    @FXML
    private TextField typeText;

    @FXML
    private ComboBox<Contact> contactCombo;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> startHourCombo;

    @FXML
    private ComboBox<String> startMinCombo;

    @FXML
    private ComboBox<String> endHourCombo;

    @FXML
    private ComboBox<String> endMinCombo;

    @FXML
    private ComboBox<Customer> customerCombo;

    @FXML
    private ComboBox<Integer> userCombo;

    @FXML
    private Label location;

    @FXML
    private Label userId;

    ObservableList<String> hours = FXCollections.observableArrayList();
    ObservableList<String> minutes = FXCollections.observableArrayList();

    /** Cancels the current operation.
     * Confirms that the user acknowledges they will lose any inputted information and then returns to the previous screen.
     * @param event An ActionEvent object created when the user clicks the Cancel button.
     * @throws IOException Throws exception if view is not found.*/
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        if (Errors.cancelConfirmation()){
            Parent root = FXMLLoader.load(getClass().getResource("/view/appointments-view.fxml"));

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.setTitle("Appointment Scheduler");
            stage.setScene(new Scene(root));
            stage.show();
        }

    }

    /** Attempts to save the proposed appointment.
     * If the appointment is valid, the appointment will be saved to the database and return to the previous screen.
     * Otherwise the user will receive error messages.
     * @param event An ActionEvent object created when the user clicks the Save button.
     * @throws IOException Throws exception if view is not found. */
    @FXML
    void onActionSave(ActionEvent event) throws IOException, SQLException {

        // Check for logical errors in input fields.
        if(Errors.saveApptConfirmation(titleText, descriptionText, locationText, customerCombo, datePicker, startHourCombo, startMinCombo, endHourCombo, endMinCombo, contactCombo, userCombo)) {

            Appointments newAppt = new Appointments(0, null, null, null, null, null, null, null, 0, 0);

            //gets local date time from date picker and time combo boxes
            LocalDate sDate = datePicker.getValue();
            String sHour = startHourCombo.getSelectionModel().getSelectedItem().toString();
            String sMin = startMinCombo.getSelectionModel().getSelectedItem().toString();
            String eHour = endHourCombo.getSelectionModel().getSelectedItem().toString();
            String eMin = endMinCombo.getSelectionModel().getSelectedItem().toString();
            LocalDateTime startTime = DateTimeProcessor.toLDT(sDate, sHour, sMin);
            LocalDateTime endTime = DateTimeProcessor.toLDT(sDate, eHour, eMin);

            // check if the start and end time are within EST business hours.
            if (Errors.inBusinessHours(startTime, endTime)){
                //create a new appt
                newAppt.setApptId(Integer.parseInt(appointmentIdText.getText()));
                newAppt.setTitle(titleText.getText());
                newAppt.setDescription(descriptionText.getText());
                newAppt.setLocation(locationText.getText());
                newAppt.setContact(contactCombo.getSelectionModel().getSelectedItem().getName());
                newAppt.setType(typeText.getText());
                newAppt.setStart(startTime);
                newAppt.setEnd(endTime);
                newAppt.setCustId(customerCombo.getSelectionModel().getSelectedItem().getCustomerId());
                newAppt.setUserId(userCombo.getSelectionModel().getSelectedItem());

                // Check for overlapping appointments for customer.
                if (Errors.apptOverlap(newAppt)){
                    if (AppointmentQuery.insertAppointment(newAppt) > 0) {
                        System.out.println("Insert Successful");
                    } else {
                        System.out.println("Insert Unsuccessful");
                    }

                    //show calendar view
                    Parent root = FXMLLoader.load(getClass().getResource("/view/appointments-view.fxml"));

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    stage.setTitle("Appointment Scheduler");
                    stage.setScene(new Scene(root));
                    stage.show();
                }
            }
        }
    }

    /** Logs the user out of the application and returns to the Login screen.
     * User will receive a Cancel confirmation message before logging out.
     * @param event An ActionEvent object created when the user clicks the Logout button.
     * @throws IOException Throws exception if view is not found. */
    @FXML
    void onActionLogout(ActionEvent event) throws IOException {

        if (Errors.cancelConfirmation()){
            Parent root = FXMLLoader.load(getClass().getResource("/view/login-view.fxml"));

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
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

    /** Clears all fields on the Add Appointment form.
     * @param event An ActionEvent object created when the user clicks the Clear button.
     */
    @FXML
    void onActionClear(ActionEvent event) {

        titleText.clear();
        descriptionText.clear();
        locationText.clear();
        typeText.clear();
        contactCombo.getSelectionModel().clearSelection();
        datePicker.setValue(null);
        startHourCombo.getSelectionModel().clearSelection();
        startMinCombo.getSelectionModel().clearSelection();
        endHourCombo.getSelectionModel().clearSelection();
        endMinCombo.getSelectionModel().clearSelection();
        customerCombo.getSelectionModel().clearSelection();
        userCombo.getSelectionModel().clearSelection();
    }

    /** Initializes the Add Appointment screen and populates combo boxes.
     * @param url The URL of the view being loaded.
     * @param resourceBundle The ResourceBundle used to determine the Locale.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hours.addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
                "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        minutes.addAll("00", "15", "30", "45");

        userId.setText("User: " + LoginController.userName);
        location.setText("Location: " + ZoneId.systemDefault());
        appointmentIdText.setText(String.valueOf(AppointmentQuery.getNextId()));
        contactCombo.setItems(ContactQuery.getAllContacts());
        startHourCombo.setItems(hours);
        startMinCombo.setItems(minutes);
        endHourCombo.setItems(hours);
        endMinCombo.setItems(minutes);
        customerCombo.setItems(CustomerQuery.getAllCustomers());
        userCombo.setItems(UserQuery.getAllUserIds());

    }

}
