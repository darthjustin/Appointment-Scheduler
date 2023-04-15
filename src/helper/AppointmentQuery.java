package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;

import java.sql.*;

public class AppointmentQuery {

    public static ObservableList<Appointments> getAllAppointments(){
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, Type, c.Contact_Name, Start, End, customer_ID, User_ID FROM appointments\n" +
                    "INNER JOIN contacts c on appointments.Contact_ID = c.Contact_ID";

            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int apptId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                String contact = rs.getString("Contact_Name");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int custId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");



                Appointments appointment = new Appointments(apptId, title, description, location, contact, type, start, end, custId, userId);
                appointmentsList.add(appointment);
            }
        }
        catch (SQLException e){
            //do nothing
        }

        return appointmentsList;
    }
}
