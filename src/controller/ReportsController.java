package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    Stage stage;
    Parent scene;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
