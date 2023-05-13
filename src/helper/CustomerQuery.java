package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import model.Appointments;
import model.Customer;

/** Holds the methods to retrieve customer information from the database. */
public class CustomerQuery {

    /** Retrieves all customers from the database.
     * @return an ObservableList of Customer objects */
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT a.*, c.country, d.division FROM customers a\n" +
                    "INNER JOIN first_level_divisions d on a.Division_ID = d.Division_ID\n" +
                    "INNER JOIN countries c on d.COUNTRY_ID = c.Country_ID\n" +
                    "ORDER BY Customer_ID ASC";

            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phoneNum = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");
                String country = rs.getString("Country");
                String division = rs.getString("Division");

                customerList.add(new Customer(customerId, customerName, address, postalCode, phoneNum, divisionId, country, division));
            }
        } catch(SQLException e){
            //do nothing
        }
        return customerList;
    }

    /** Retrieves a specific customer from the database based on their ID.
     * @param custId The customer ID to identify a customer record.
     * @return The Customer object associated with the ID.*/
    public static Customer getCustomer(int custId){
        Customer foundCustomer = new Customer(0,null, null, null, null, 0, null, null);
        String sql = "SELECT a.*, c.country, d.division FROM customers a\n" +
                "INNER JOIN first_level_divisions d on a.Division_ID = d.Division_ID\n" +
                "INNER JOIN countries c on d.COUNTRY_ID = c.Country_ID\n" +
                "WHERE Customer_ID = ?";

        try{
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, custId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phoneNum = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");
                String country = rs.getString("Country");
                String division = rs.getString("Division");

                foundCustomer.setCustomerId(customerId);
                foundCustomer.setCustomerName(customerName);
                foundCustomer.setAddress(address);
                foundCustomer.setPostalCode(postalCode);
                foundCustomer.setPhoneNum(phoneNum);
                foundCustomer.setDivisionId(divisionId);
                foundCustomer.setCountry(country);
                foundCustomer.setDivision(division);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return foundCustomer;
    }

    /** Retrieves the next available Customer ID from the database.
     * @return The Customer ID integer. */
    public static int getNextCustID(){
        int id = 0;
        String sql = "SELECT MAX(Customer_ID) as Customer_ID FROM customers";

        try{
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                id = rs.getInt("Customer_ID");
                id++;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return id;
    }

    /** Inserts a new customer into the database.
     * @param customer The customer object containing the data to be inserted into the database.
     * @return The number of rows affected by the insert statement.*/
    public static int insertCustomer(Customer customer){
        int rowsReturned = 0;
        String sql = "INSERT INTO customers VALUES(?, ?, ?, ?, ?, NOW(), 'me', NOW(), 'me', ?)";

        try{
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customer.getCustomerId());
            ps.setString(2, customer.getCustomerName());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getPostalCode());
            ps.setString(5, customer.getPhoneNum());
            ps.setInt(6, customer.getDivisionId());

            rowsReturned = ps.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }

        return rowsReturned;
    }

    /** Deletes a customer from the database.
     * @param customer The Customer object containing the customer to be deleted from the database.
     * @return The number of rows affected by the insert statement.*/
    public static int deleteCustomer(Customer customer){
        int rowsReturned = 0;
        String sql = "DELETE FROM customers WHERE customer_ID = ?";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customer.getCustomerId());
            rowsReturned = ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return rowsReturned;
    }

    /** Updates a customer from the database.
     * @param customer The Customer object containing the information to be updated from the database.
     * @return The number of rows affected by the insert statement.*/
    public static int updateCustomer(Customer customer){
        int rowsReturned = 0;
        String sql = "UPDATE customers\n" +
                "SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = NOW(), Last_Updated_By = 'me', Division_ID = ?\n" +
                "WHERE Customer_ID = ?;";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getPostalCode());
            ps.setString(4, customer.getPhoneNum());
            ps.setInt(5, customer.getDivisionId());
            ps.setInt(6, customer.getCustomerId());

            rowsReturned = ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return rowsReturned;
    }

    /** Retrieves all appointments for a specific customer.
     * @param custID The ID of the customer to be retrieved from the database.
     * @return The ObservableList of Appointment objects related to the customer. */
    public static ObservableList<Appointments> apptsByCustID(int custID){
        ObservableList<Appointments> appts = FXCollections.observableArrayList();
        String sql = "SELECT Appointment_ID, Title, Description, Location, Type, c.Contact_Name, Start, End, customer_ID, User_ID FROM appointments\n" +
                "INNER JOIN contacts c on appointments.Contact_ID = c.Contact_ID\n" +
                "WHERE appointments.Customer_ID = ?";

        try{
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, custID);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
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
                appts.add(appointment);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return appts;
    }

}
