package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import model.*;
import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.function.Predicate;

/** Holds the methods for error checking throughout the application. */
public abstract class Errors {


    /** Confirms that the user wishes to cancel.
     * Displays a confirmation alert asking the user if they want to cancel the current operation and lose any unsaved changes.
     @return true if the user confirms the cancellation, false otherwise.
     */
    public static boolean cancelConfirmation(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You will lose any unsaved changes. Do you wish to continue?");

        alert.setHeaderText("Please Confirm");
        alert.setTitle("Title goes here.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK){
            return true;
        }
        return false;
    }

    /** Validates the customer information entered in the form.
     * Displays an error message if any field is missing data and returns false, otherwise, returns true.
     * One lambda expression is used to check whether a text field is empty. Another lambda expression is used to check if a ComboBox is null.
     * This helps clean up the code used to generate error messages. 
     * @param title The TextField for the appointment title.
     * @param description The TextField for the appointment description.
     * @param location The TextField for the appointment location.
     * @param customer The ComboBox for the appointment customer.
     * @param date The DatePicker for the appointment date.
     * @param sHour The ComboBox for the appointment start hour.
     * @param sMin The ComboBox for the appointment start hour.
     * @param eHour The ComboBox for the appointment start hour.
     * @param eMin The ComboBox for the appointment start hour.
     * @param contact The ComboBox for the appointment contact.
     * @param user The ComboBox for the appointment user ID.
     * @return A boolean value indicating whether the appointment information is valid or not.
     */
    public static boolean saveApptConfirmation(TextField title, TextField description, TextField location, ComboBox<Customer> customer, DatePicker date, ComboBox<String> sHour, ComboBox<String> sMin, ComboBox<String> eHour, ComboBox<String> eMin, ComboBox<Contact> contact, ComboBox<Integer> user){
        StringBuilder errors = new StringBuilder();
        boolean pass = true;
        LocalTime start = LocalTime.of(Integer.parseInt(sHour.getSelectionModel().getSelectedItem()), Integer.parseInt(sMin.getSelectionModel().getSelectedItem()));
        LocalTime end = LocalTime.of(Integer.parseInt(eHour.getSelectionModel().getSelectedItem()), Integer.parseInt(eMin.getSelectionModel().getSelectedItem()));

        //Lambda expression to clear up the clutter in the if statements. Allows the if statement to test if a text field is empty.
        Predicate<TextField> isEmpty = (textField) -> textField.getText().isEmpty();
        //Lambda expression to clear up the clutter in the if statements. Allows the if statement to test if a comboBox is null.
        Predicate<ComboBox> isNull = (comboBox) -> comboBox.getSelectionModel().getSelectedItem() == null;

        if (isEmpty.test(title)){
            errors.append("No data in Title field.\n");
        }

        if (isEmpty.test(description)){
            errors.append("No data in Description field.\n");
        }

        if (isEmpty.test(location)){
            errors.append("No data in Location field.\n");
        }

        if (customer.getSelectionModel().getSelectedItem() == null){
            errors.append("No data in Customer field.\n");
        }

        if (date.getValue() == null){
            errors.append("No data in Date field.\n");
        }

        if (isNull.test(sHour)){
            errors.append("No data in Start Hour field.\n");
        }

        if (isNull.test(sMin)){
            errors.append("No data in Start Minute field.\n");
        }

        if (isNull.test(eHour)){
            errors.append("No data in End Hour field.\n");
        }

        if (isNull.test(eMin)){
            errors.append("No data in End Minute field.\n");
        }

        if (start.isAfter(end)){
            errors.append("End time is before Start time.\n");
        }

        if (isNull.test(contact)){
            errors.append("No data in Contact field.\n");
        }

        if (isNull.test(user)){
            errors.append("No data in User field.\n");
        }

        if (!errors.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, errors.toString());
            alert.setTitle("Appointment Error");
            alert.setHeaderText("Please address the following errors to continue:");
            Optional<ButtonType> result = alert.showAndWait();
            pass = false;
        }

        return pass;

    }

    /** Confirms that the proposed appointment doesn't overlap with another for the same customer.
     * Displays an error message if the customer has another appointment at the same time.
     * @param appt The appointment instance to be compared to existing appointments.
     * @return A boolean value indicating whether the customer has an overlapping appointment.
     */
    public static boolean apptOverlap(Appointments appt){
        boolean noOverlap = true;
        ObservableList<Appointments> custAppts = AppointmentQuery.custAppointmentsById(appt);
        StringBuilder string = new StringBuilder("Proposed appointment time overlaps with another appointment.\n");

        LocalDateTime s = DateTimeProcessor.toUTC(appt.getStart());
        LocalDateTime e = DateTimeProcessor.toUTC(appt.getEnd());

        for (Appointments a : custAppts){
            //if created appointment start is after or equal to current appointment start and before current appointment end.
            if ((s.isAfter(a.getStart()) || s.isEqual(a.getStart())) && s.isBefore(a.getEnd())){
                noOverlap = false;
                string.append(a.getStart().toString() + " - " + a.getEnd().toString());
                break;
            }
            //if created appointment end is after current appointment start and before or equal to current appointment end.
            else if (e.isAfter(a.getStart()) && (e.isBefore(a.getEnd()) || e.isEqual(a.getEnd()))){
                noOverlap = false;
                string.append(a.getStart().toString() + " - " + a.getEnd().toString());
                break;
            }
            //if created appointment start is before or equal to current appoinment start and after or equal to current appointment end.
            else if ((s.isBefore(a.getStart()) || s.isEqual(a.getStart())) && (e.isAfter(a.getEnd())) || e.isEqual(a.getEnd())){
                noOverlap = false;
                string.append(a.getStart().toString() + " - " + a.getEnd().toString());
                break;
            }
        }
        if (noOverlap == false){
            Alert alert = new Alert(Alert.AlertType.ERROR, string.toString());
            alert.setTitle("Error Detected");
            alert.setHeaderText("Appointment Error");
            Optional<ButtonType> result;
            result = alert.showAndWait();
        }

        return noOverlap;
    }

    /** Confirms that the entered times are between 8am-5pm EST.
     * Displays an error message if the proposed appointment time is
     * outside of business hours. Returns true otherwise.
     * @param start The LocalDateTime for the appointment start time.
     * @param end The LocalDateTime for the appointment end time.
     * @return A boolean value indicating if the appointment is in business hours or not.
     */
    public static boolean inBusinessHours(LocalDateTime start, LocalDateTime end){
        boolean inBizHours = true;
        LocalDateTime s = DateTimeProcessor.toEST(start);
        LocalTime sTime = s.toLocalTime();
        LocalDateTime e = DateTimeProcessor.toEST(end);
        LocalTime eTime = e.toLocalTime();

        Alert alert = new Alert(Alert.AlertType.ERROR, "Proposed appointment time is outside of business hours.\nBusiness hours are 8am-10pm EST.");
        alert.setTitle("Error Detected");
        alert.setHeaderText("Appointment Error");
        Optional<ButtonType> result;

        if(sTime.isBefore(LocalTime.of(8,0))){
            result = alert.showAndWait();
            inBizHours = false;
        } else if(eTime.isAfter(LocalTime.of(22,0))){
            result = alert.showAndWait();
            inBizHours = false;
        }

        return inBizHours;
    }

    /** Validates the customer information entered in the form.
     * Displays an error message if any field is missing data and returns false, otherwise, returns true.
     * @param name The TextField for the customer's name.
     * @param address The TextField for the customer's address.
     * @param postalCode The TextField for the customer's postal code.
     * @param phone The TextField for the customer's phone number.
     * @param country The ComboBox for the customer's country.
     * @param division The ComboBox for the customer's state/province.
     * @return A boolean value indicating whether the customer information is valid or not.
     */
    public static boolean saveCustomerConfirmation(TextField name, TextField address, TextField postalCode, TextField phone, ComboBox<Country> country, ComboBox<Division> division){
        boolean pass = true;
        StringBuilder errors = new StringBuilder();

        if(name.getText() == ""){
            errors.append("No data in Name field.\n");
        }
        if(address.getText() == ""){
            errors.append("No data in Address field.\n");
        }
        if(postalCode.getText() == ""){
            errors.append("No data in Postal Code field.\n");
        }
        if(phone.getText() == ""){
            errors.append("No data in Phone Number field.\n");
        }
        if(country.getSelectionModel().getSelectedItem() == null){
            errors.append("No data in Country field.\n");
        }
        if(division.getSelectionModel().getSelectedItem() == null){
            errors.append("No data in State/Province field.\n");
        }

        if (!errors.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, errors.toString());
            alert.setTitle("Customer Error");
            alert.setHeaderText("Please address the following errors to continue:");
            Optional<ButtonType> result = alert.showAndWait();
            pass = false;
        }
        return pass;
    }

    /** Displays an error message indicating that the customer has associated appointments.
     * User must delete customer appointments to continue.
     */
    public static void customerHasAppointments(){
        Alert alert = new Alert(Alert.AlertType.ERROR, "Delete customer appointments to continue.");
        alert.setTitle("Customer Error");
        alert.setHeaderText("Customer has associated appointments.");
        Optional<ButtonType> result = alert.showAndWait();
    }

    /** Displays a message when there are upcoming appointments within 15 minutes of the user logging into the application.
     * If there are no upcoming appointments, the user will not receive a message.
     * @param appts An ObservableList of all Appointment objects.
     */
    public static void upcomingAppointments(ObservableList<Appointments> appts){
        boolean upcomingAppt = false;
        StringBuilder message = new StringBuilder();
        LocalDateTime now = LocalDateTime.now();

        for (Appointments appt : appts){
            if (appt.getStart().isAfter(now) && appt.getStart().isBefore(now.plusMinutes(15))){
                upcomingAppt = true;
                message.append("Appointment ID: " + appt.getApptId() + "\t Time: " + appt.getStart() + " - " + appt.getEnd());
            }
        }

        if(upcomingAppt){
            message.insert(0, "Appointments within the next 15 minutes:\n");
            Alert alert = new Alert(Alert.AlertType.INFORMATION, message.toString());
            alert.setTitle("Upcoming Appointments");
            alert.setHeaderText("Upcoming Appointments");
            alert.showAndWait();
        }

    }

}
