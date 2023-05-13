package helper;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import model.Appointments;
import model.Contact;
import model.TypeReport;

import java.lang.reflect.Type;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

/** Holds the methods to retrieve appointment information from the database. */
public class AppointmentQuery {

    /** Retrieves all appointments from the database.
     * @return An ObservableList of Appointment objects. */
    public static ObservableList<Appointments> getAllAppointments(){
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, Type, c.Contact_Name, Start, End, customer_ID, User_ID FROM appointments\n" +
                    "INNER JOIN contacts c on appointments.Contact_ID = c.Contact_ID\n" +
                    "ORDER BY Appointment_ID asc";

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

                LocalDateTime tsToLDT = start.toLocalDateTime();
                LocalDateTime startLDT = DateTimeProcessor.toLocalZone(tsToLDT);
                LocalDateTime tsEndToLDT = end.toLocalDateTime();
                LocalDateTime endLDT = DateTimeProcessor.toLocalZone(tsEndToLDT);

                Appointments appointment = new Appointments(apptId, title, description, location, contact, type, startLDT, endLDT, custId, userId);
                appointmentsList.add(appointment);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return appointmentsList;
    }

    /** Retrieves all appointments occurring in the current week from the database.
     * @return An ObservableList of Appointment objects. */
    public static ObservableList<Appointments> getApptsByWeek(){
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID \n" +
                    "WHERE Start between CAST(CURRENT_DATE() as Date) and ADDDATE(CURRENT_DATE(), 7);\n";

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

                LocalDateTime tsToLDT = start.toLocalDateTime();
                LocalDateTime startLDT = DateTimeProcessor.toLocalZone(tsToLDT);
                LocalDateTime tsEndToLDT = end.toLocalDateTime();
                LocalDateTime endLDT = DateTimeProcessor.toLocalZone(tsEndToLDT);

                Appointments appointment = new Appointments(apptId, title, description, location, contact, type, startLDT, endLDT, custId, userId);
                appointmentsList.add(appointment);

            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return appointmentsList;
    }

    /** Retrieves all appointments occuring in the current month from the database.
     * @return An ObservableList of Appointment objects. */
    public static ObservableList<Appointments> getApptsByMonth(){
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID \n" +
                    "WHERE Start >= CAST(CURRENT_DATE() as Date) AND Start <= ADDDATE(current_date(), 31);";

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

                LocalDateTime tsToLDT = start.toLocalDateTime();
                LocalDateTime startLDT = DateTimeProcessor.toLocalZone(tsToLDT);
                LocalDateTime tsEndToLDT = end.toLocalDateTime();
                LocalDateTime endLDT = DateTimeProcessor.toLocalZone(tsEndToLDT);

                Appointments appointment = new Appointments(apptId, title, description, location, contact, type, startLDT, endLDT, custId, userId);
                appointmentsList.add(appointment);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return appointmentsList;
    }

    /** Retrieves the next available appointment ID from the database.
     * @return The next available appoitment ID integer. */
    public static int getNextId() {
        int nextID = 0;
        int currentMaxId;
        String sql = "SELECT MAX(appointment_id) as Appointment_ID FROM appointments";

        try{
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                currentMaxId = rs.getInt("Appointment_ID");
                nextID = currentMaxId + 1;

            }
        } catch (SQLException e){
            e.printStackTrace();
        }


        return nextID;
    }

    /** Inserts an appointment into the database.
     * @param appt The Appointment object containing the information to insert into the database.
     * @return An integer of the number of rows affected in the database. */
    public static int insertAppointment(Appointments appt) {
        int rowsAffected = 0;
        String sql = "INSERT INTO appointments VALUES(?, ?, ?, ?, ?, ?, ?, NOW(), 'me', NOW(), 'me', ?, ?, ?);";

        //timestamp conversion
        LocalDateTime ldtStart = appt.getStart();
        LocalDateTime ldtEnd = appt.getEnd();
        Timestamp tsStart = DateTimeProcessor.toTimestamp(ldtStart);
        Timestamp tsEnd = DateTimeProcessor.toTimestamp(ldtEnd);

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, appt.getApptId());
            ps.setString(2, appt.getTitle());
            ps.setString(3, appt.getDescription());
            ps.setString(4, appt.getLocation());
            ps.setString(5, appt.getType());
            ps.setTimestamp(6, tsStart);
            ps.setTimestamp(7, tsEnd);
            ps.setInt(8, appt.getCustId());
            ps.setInt(9, appt.getUserId());
            ps.setInt(10, ContactQuery.getContactId(appt.getContact()));

            rowsAffected = ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return rowsAffected;
    }

    /** Retrieves a list of appointments for a particular customer.
     * @param appt The appointment containing the associated customer information.
     * @return An ObservableList of Appointment objects. */
    public static ObservableList custAppointmentsById(Appointments appt){
        ObservableList<Appointments> custAppts  = FXCollections.observableArrayList();

        String sql = "SELECT Appointment_ID, Title, Description, Location, Type, c.Contact_Name, Start, End, customer_ID, User_ID FROM appointments\n" +
                "INNER JOIN contacts c on appointments.Contact_ID = c.Contact_ID\n" +
                "WHERE appointments.Customer_ID = ?\n" +
                "AND NOT appointments.Appointment_ID = ?;";

        //Timestamp conversion
        LocalDateTime ldtStart = appt.getStart();
        LocalDateTime ldtEnd = appt.getEnd();

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, appt.getCustId());
            ps.setInt(2, appt.getApptId());
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

                LocalDateTime startLDT = start.toLocalDateTime();
                LocalDateTime endLDT = end.toLocalDateTime();

                Appointments appointment = new Appointments(apptId, title, description, location, contact, type, startLDT, endLDT, custId, userId);
                custAppts.add(appointment);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return custAppts;
    }

    /** Deletes a specified appointment from the database.
     * @param appt The Appointment object containing the information to be deleted from the database.
     * @return The integer of the number of rows affected in the database. */
    public static int deleteAppointment(Appointments appt){
        int rowsAffected = 0;
        String sql = "DELETE FROM appointments WHERE appointment_ID = ?";

        try{
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, appt.getApptId());
            rowsAffected = ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return rowsAffected;
    }

    /** Updates a specified appointment in the database.
     * @param appt The Appointment object containing the information to be updated in the database.
     * @return An integer of the number of rows affected in the database. */
    public static int updateAppointment(Appointments appt){
        int rowsAffected = 0;
        String sql = "UPDATE appointments\n" +
                "SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, Last_Update = NOW(), Last_Updated_By = 'me', User_ID = ?, Contact_ID = ?\n" +
                "WHERE Appointment_ID = ?;";

        //timestamp conversion
        LocalDateTime ldtStart = appt.getStart();
        LocalDateTime ldtEnd = appt.getEnd();
        Timestamp tsStart = DateTimeProcessor.toTimestamp(ldtStart);
        Timestamp tsEnd = DateTimeProcessor.toTimestamp(ldtEnd);

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, appt.getTitle());
            ps.setString(2, appt.getDescription());
            ps.setString(3, appt.getLocation());
            ps.setString(4, appt.getType());
            ps.setTimestamp(5, tsStart);
            ps.setTimestamp(6, tsEnd);
            ps.setInt(7, appt.getCustId());
            ps.setInt(8, appt.getUserId());
            ps.setInt(9, ContactQuery.getContactId(appt.getContact()));
            ps.setInt(10, appt.getApptId());

            rowsAffected = ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return rowsAffected;
    }

    /** Retrieves a list of appointments for each month from the database.
     * @return An ObservableList of TypeReport objects. */
    public static ObservableList<TypeReport> apptTypeByMonth(){
        ObservableList<TypeReport> typeReports = FXCollections.observableArrayList();


        String sql = "SELECT MONTHNAME(`Start`) AS Month, Type, COUNT(*) as 'No. Of Appointments'\n" +
                "FROM appointments\n" +
                "GROUP BY MONTHNAME(`Start`), Type";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String month = rs.getString("Month");
                String type =  rs.getString("Type");
                int count = rs.getInt("No. Of Appointments");

                TypeReport report = new TypeReport(month, type, count);
                typeReports.add(report);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        //return strings;
        return typeReports;
    }

    /** Retrieves a list of appointments associated with a specific contact from the database.
     * @param contact The Contact object containing the associated appointment information.
     * @return An ObservableList of Appointment objects for the specified Contact. */
    public static ObservableList apptByContact(Contact contact){
        ObservableList<Appointments> contactAppt = FXCollections.observableArrayList();

        String sql = "SELECT Appointment_ID, Title, Description, Location, Type, c.Contact_Name, Start, End, customer_ID, User_ID FROM appointments\n" +
                "INNER JOIN contacts c on appointments.Contact_ID = c.Contact_ID\n" +
                "WHERE appointments.Contact_ID = ?;";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, contact.getContactId());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int apptId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                String contactName = rs.getString("Contact_Name");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int custId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");

                LocalDateTime startLDT = start.toLocalDateTime();
                LocalDateTime endLDT = end.toLocalDateTime();

                Appointments appointment = new Appointments(apptId, title, description, location, contactName, type, startLDT, endLDT, custId, userId);
                contactAppt.add(appointment);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return contactAppt;
    }
}
