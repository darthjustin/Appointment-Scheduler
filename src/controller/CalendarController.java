package controller;

import helper.AppointmentQuery;
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
import model.Appointments;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class CalendarController implements Initializable {

    Stage stage;
    Parent scene;

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
    private TableColumn<Appointments, Timestamp> startCol;

    @FXML
    private TableColumn<Appointments, Timestamp> endCol;

    @FXML
    private TableColumn<Appointments, Integer> userIdCol;

    @FXML
    private TableColumn<Appointments, Integer> custIdCol;

    @FXML
    private Button appointmentsButton;

    @FXML
    private Button contactsButton;

    @FXML
    private Label location;

    @FXML
    private Button logoutButton;

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
    void onActionAddAppointment(ActionEvent event) {

    }

    @FXML
    void onActionUpdateAppointment(ActionEvent event) {

    }

    @FXML
    void onActionDeleteAppointment(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Appointments> allAppts = AppointmentQuery.getAllAppointments();

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
