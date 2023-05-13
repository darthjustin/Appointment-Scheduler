package controller;

import helper.AppointmentQuery;
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
import model.Appointments;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ResourceBundle;

/** Controller handling the methods for the Appointments screen. */
public class AppointmentsController implements Initializable {

    Stage stage;

    @FXML
    private TableView<Appointments> appointmentTableView;

    @FXML
    private TableColumn<Appointments, Integer> apptIdCol;

    @FXML
    private TableColumn<Appointments, String> titleCol;

    @FXML
    private TableColumn<Appointments, String> descCol;

    @FXML
    private TableColumn<Appointments, String> locationCol;

    @FXML
    private TableColumn<Appointments, String> contactCol;

    @FXML
    private TableColumn<Appointments, String> typeCol;

    @FXML
    private TableColumn<Appointments, LocalDateTime> startCol;

    @FXML
    private TableColumn<Appointments, LocalDateTime> endCol;

    @FXML
    private TableColumn<Appointments, Integer> userIdCol;

    @FXML
    private TableColumn<Appointments, Integer> custIdCol;

    @FXML
    private ToggleGroup viewToggle;

    @FXML
    private Label location;

    @FXML
    private Label userId;

    /** Shows all appointments from the database.
     * @param event The ActionEvent object created when selecting the All radio button. */
    @FXML
    void onActionShowAllAppts(ActionEvent event) {
        appointmentTableView.setItems(AppointmentQuery.getAllAppointments());
    }

    /** Shows all appointments for the next 7 days from the database.
     * @param event The ActionEvent object created when selecting the By Week radio button. */
    @FXML
    void onActionShowApptsByWeek(ActionEvent event) {
        appointmentTableView.setItems(AppointmentQuery.getApptsByWeek());
    }

    /** Shows all appointments for the next 31 from the database.
     * @param event The ActionEvent object created when selecting the By Month radio button. */
    @FXML
    void onActionShowApptsByMonth(ActionEvent event) {
        appointmentTableView.setItems(AppointmentQuery.getApptsByMonth());
    }

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

    /** Takes the user to the Add Appointment screen.
     * @param event An ActionEvent object created when the user clicks the Add button.
     * @throws IOException Throws exception if view is not found. */
    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/addAppointment-view.fxml"));

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("Appointment Scheduler");
        stage.setScene(new Scene(root));
        stage.show();

    }

    /** Takes the user to the Update Appointment screen.
     * Retrieves information from the currently selected appointment in the table view.
     * If no appointment is currently selected, the user will get a message asking them to select an appointment.
     * @param event An ActionEvent object created when the user clicks the Update button.
     * @throws IOException Throws exception if view is not found. */
    @FXML
    void onActionUpdateAppointment(ActionEvent event) throws IOException {


        if(appointmentTableView.getSelectionModel().getSelectedItem() != null){
            UpdateAppointmentController.sendAppt(appointmentTableView.getSelectionModel().getSelectedItem());
            Parent root = FXMLLoader.load(getClass().getResource("/view/updateAppointment-view.fxml"));

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.setTitle("Appointment Scheduler");
            stage.setScene(new Scene(root));
            stage.show();
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an appointment.");
            alert.show();

        }
    }

    /** Deletes the selected appointment from the database.
     * Retrieves information from the currently selected appointment in the table view.
     * If no appointment is currently selected, the user will get a message asking them to select an appointment.
     * Displays a message with the Appointment Id and the Type of appointment that was deleted.
     * @param event An ActionEvent object created when the user clicks the Update button.
     * @throws IOException Throws exception if view is not found. */
    @FXML
    void onActionDeleteAppointment(ActionEvent event) {

        if(appointmentTableView.getSelectionModel().getSelectedItem() != null){
            Appointments appt = appointmentTableView.getSelectionModel().getSelectedItem();
            int deletedRows = AppointmentQuery.deleteAppointment(appt);
            if (deletedRows > 0){
                System.out.println("Delete Successful.");
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appointment ID: " + appt.getApptId() + " | Appointment Type: " + appt.getType());
                alert.setHeaderText("Appointment deleted.");
                alert.setTitle("Appointment deleted.");
                alert.showAndWait();
                appointmentTableView.setItems(AppointmentQuery.getAllAppointments());
            }else{
                System.out.println("Delete unsuccessful.");
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an appointment.");
            alert.show();
        }

    }

    /** Initializes the Appointments screen and pre-populates the TableView.
     * @param url The URL of the view being loaded.
     * @param resourceBundle The ResourceBundle used to determine the Locale.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Appointments> allAppts = AppointmentQuery.getAllAppointments();

        userId.setText("User: " + LoginController.userName);
        location.setText("Location: " + ZoneId.systemDefault());
        appointmentTableView.setItems(allAppts);
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("apptId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("custId"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }
}
