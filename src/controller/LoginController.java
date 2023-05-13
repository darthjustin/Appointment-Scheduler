package controller;

import helper.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;
import model.Appointments;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/** Controller handling the methods for the Login screen. */
public class LoginController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Button clearButton;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Label passwordLabel;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Label userLocation;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField usernameText;

    public static String userName;

    /** Initializes the Login screen and pre-populates the TableView.
     * Language will update based on the user's system default language.
     * @param url The URL of the view being loaded.
     * @param rb The ResourceBundle used to determine the Locale.*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Locale france = Locale.FRANCE;
        Locale english = Locale.ENGLISH;
        Locale language = Locale.getDefault();


        if(language.equals(france)){
            Locale.setDefault(france);
        }else{
            Locale.setDefault(english);
        }

        rb = ResourceBundle.getBundle("language/Nat", Locale.getDefault());
        userLocation.setVisible(true);
        userLocation.setText(rb.getString("location") + ZoneId.systemDefault());
        usernameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        clearButton.setText(rb.getString("clear"));
        loginButton.setText(rb.getString("login"));

    }

    /** Clears all input fields and errors on the Login form.
     * @param event An ActionEvent object created when the user clicks the Clear button.
     */
    @FXML
    public void onActionClearText(ActionEvent event) {

        passwordText.clear();
        usernameText.clear();
        errorLabel.setVisible(false);
    }

    /** Verifies the user's login information to allow them access to the application.
     * If the username or password are incorrect, an error message will be displayed in the user's current system language.
     * User login attempts are written to a text file in the application directory.
     * If there are appointments within 15 minutes of the user loggin in, they will receive a message indicating which appointments are upcoming.
     * @param event An ActionEvent object created when the user clicks the Clear button.
     * @throws IOException Throws exception if view is not found. */
    @FXML
    public void onActionLogin(ActionEvent event) throws IOException {
        ResourceBundle rb = ResourceBundle.getBundle("language/Nat", Locale.getDefault());
        String username = usernameText.getText();
        String password = passwordText.getText();
        String filename = "login_activity.txt";
        FileWriter txtFile = new FileWriter(filename, true);
        PrintWriter pw = new PrintWriter(txtFile);

        if (errorLabel.isVisible()){
            errorLabel.setVisible(false);
        }


        if (UserQuery.userLogin(username, password)) {
            pw.println("Attempted login from Username: " + username + " at " + Timestamp.valueOf(LocalDateTime.now()) + ". Status - Succeeded.");
            userName = username;
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Main.class.getResource("/view/appointments-view.fxml"));
            stage.setTitle("Appointment Scheduler");
            stage.setScene(new Scene(scene));
            stage.setX(200);
            stage.setY(100);
            stage.show();

            ObservableList<Appointments> allAppts = AppointmentQuery.getAllAppointments();
            Errors.upcomingAppointments(allAppts);
        }
        else {
            pw.println("Attempted login from Username: " + username + " at " + Timestamp.valueOf(LocalDateTime.now()) + ". Status - Failed.");
            errorLabel.setText(rb.getString("error"));
            errorLabel.setVisible(true);
        }
        pw.close();

    }

}
