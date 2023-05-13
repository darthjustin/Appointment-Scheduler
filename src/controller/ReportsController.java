package controller;

import helper.AppointmentQuery;
import helper.ContactQuery;
import helper.CountryQuery;
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
import model.Contact;
import model.Country;
import model.TypeReport;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;

/** Controller handling the methods for the Reports screen. */
public class ReportsController implements Initializable {

    Stage stage;

    @FXML
    private Label location;

    @FXML
    private Label userId;

    @FXML
    private TableView<TypeReport> table;

    @FXML
    private TableView<Country> table2;

    @FXML
    private ComboBox<Contact> selectionCombo;

    /** Changes the contents of the table view to display all appointments associated with a specific contact.
     * If no contact is selected, the table is cleared
     * and made visible. Otherwise, the table is populated with appointments associated with the selected contact.
     * @param event ActionEvent object created by the "Appointments by Contact" button.
     */
    @FXML
    void onActionApptsByContact(ActionEvent event) {
        if(selectionCombo.getValue() == null){
            table.getColumns().clear();
            table.setVisible(true);
            table2.setVisible(false);
        }
        ObservableList<Contact> contacts = ContactQuery.getAllContacts();
        selectionCombo.setVisible(true);
        selectionCombo.setItems(contacts);
        selectionCombo.setVisible(true);
        selectionCombo.setItems(contacts);
        selectionCombo.setPromptText("Select Contact");
    }

    /** Changes the content of the table view for the newly selected contact.
     * @param event ActionEvent object created by the "Appointments by Contact" button.
     */
    @FXML
    void onActionChangeContact(ActionEvent event) {
        table.getColumns().clear();
        table.setVisible(true);
        table2.setVisible(false);

        if(selectionCombo.getValue() != null){
            ObservableList<TypeReport> report = AppointmentQuery.apptByContact(selectionCombo.getValue());
            table.setItems(report);
            TableColumn<TypeReport, String> column1 = new TableColumn<>("Appt. ID");
            column1.setMinWidth(60);
            TableColumn<TypeReport, String> column2 = new TableColumn<>("Title");
            column2.setMinWidth(60);
            TableColumn<TypeReport, Integer> column3 = new TableColumn<>("Type");
            column3.setMinWidth(120);
            TableColumn<TypeReport, Integer> column4 = new TableColumn<>("Description");
            column4.setMinWidth(60);
            TableColumn<TypeReport, Integer> column5 = new TableColumn<>("Start");
            column5.setMinWidth(60);
            TableColumn<TypeReport, Integer> column6 = new TableColumn<>("End");
            column6.setMinWidth(60);
            TableColumn<TypeReport, Integer> column7 = new TableColumn<>("Cust. ID");
            column7.setMinWidth(60);
            column1.setCellValueFactory(new PropertyValueFactory<>("apptId"));
            column2.setCellValueFactory(new PropertyValueFactory<>("title"));
            column3.setCellValueFactory(new PropertyValueFactory<>("type"));
            column4.setCellValueFactory(new PropertyValueFactory<>("description"));
            column5.setCellValueFactory(new PropertyValueFactory<>("start"));
            column6.setCellValueFactory(new PropertyValueFactory<>("end"));
            column7.setCellValueFactory(new PropertyValueFactory<>("custId"));
            table.getColumns().add(column1);
            table.getColumns().add(column2);
            table.getColumns().add(column3);
            table.getColumns().add(column4);
            table.getColumns().add(column5);
            table.getColumns().add(column6);
            table.getColumns().add(column7);
        }

    }

    /** Changes the contents of the table view to display the number of appointments for each type.
     * @param event ActionEvent object created by the "Appointment Type by Month" button.
     */
    @FXML
    void onActionApptsByType(ActionEvent event) {
        table.getColumns().clear();
        table.setVisible(true);
        table2.setVisible(false);
        selectionCombo.setVisible(false);
        ObservableList<TypeReport> report = AppointmentQuery.apptTypeByMonth();

        table.setItems(report);
        TableColumn<TypeReport, String> column1 = new TableColumn<>("Month");
        column1.setMinWidth(140);
        TableColumn<TypeReport, String> column2 = new TableColumn<>("Type");
        column2.setMinWidth(140);
        TableColumn<TypeReport, Integer> column3 = new TableColumn<>("No. Of Appointments");
        column3.setMinWidth(140);
        column1.setCellValueFactory(new PropertyValueFactory<>("month"));
        column2.setCellValueFactory(new PropertyValueFactory<>("type"));
        column3.setCellValueFactory(new PropertyValueFactory<>("count"));
        table.getColumns().add(column1);
        table.getColumns().add(column2);
        table.getColumns().add(column3);

    }

    /** Changes the contents of the table view to display the number of customers for each country.
     * @param event ActionEvent object created by the "Number of Customers by Country" button.
     */
    @FXML
    void onActionNumCustByCountry(ActionEvent event) {
        table.getColumns().clear();
        table.setVisible(false);
        selectionCombo.setVisible(false);
        selectionCombo.setPromptText("Select a Contact");
        table2.getColumns().clear();
        table2.setVisible(true);
        ObservableList<Country> report = CountryQuery.numCustomersByCountry();

        table2.setItems(report);
        TableColumn<Country, Integer> column1 = new TableColumn<>("Country ID");
        TableColumn<Country, String> column2 = new TableColumn<>("Country");
        TableColumn<Country, Integer> column3 = new TableColumn<>("No. Of Customers");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));
        column3.setCellValueFactory(new PropertyValueFactory<>("count"));
        table2.getColumns().add(column1);
        table2.getColumns().add(column2);
        table2.getColumns().add(column3);

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

    /** Initializes the Reports screen.
     * @param url The URL of the view being loaded.
     * @param resourceBundle The ResourceBundle used to determine the Locale.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        userId.setText("User: " + LoginController.userName);
        location.setText("Location: " + ZoneId.systemDefault());

    }
}
