package controller;

import helper.AppointmentQuery;
import helper.CustomerQuery;
import helper.DBCountries;
import helper.UserQuery;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import model.Appointments;
import model.Countries;
import model.Customers;

import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;




public class LoginController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField passwordText;

    @FXML
    private TextField usernameText;

    @FXML
    private Label errorLabel;

    @FXML
    private Label userLocation;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");

        userLocation.setVisible(true);
        userLocation.setText("User Location: ");

        ObservableList<Customers> customers = CustomerQuery.getAllCustomers();
        for(Customers c : customers){
            System.out.println("Customer Address: " + c.getAddress() + " | " + c.getDivision() + ", ");
        }
    }
    @FXML
    void onActionClearText(ActionEvent event) {
        passwordText.clear();
        usernameText.clear();
        errorLabel.setVisible(false);
    }

    @FXML
    void onActionLogin(ActionEvent event) throws IOException, SQLException {

        String username = usernameText.getText();
        String password = passwordText.getText();

        if (errorLabel.isVisible()){
            errorLabel.setVisible(false);
        }

        System.out.println("Input: " + username + " | " + password);

        if (UserQuery.userLogin(username, password)) {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Main.class.getResource("/view/calendar-view.fxml"));
            stage.setTitle("Appointment Scheduler");
            stage.setScene(new Scene(scene));
            stage.setX(200);
            stage.setY(100);
            stage.show();
        }
        else {
            errorLabel.setVisible(true);
            errorLabel.setText("Invalid Username or Password. Try again.");

        }

    }

}
