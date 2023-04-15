package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Customers;

public class CustomerQuery {

    public static ObservableList<Customers> getAllCustomers() {
        ObservableList<Customers> customerList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT a.*, c.country, d.division FROM customers a\n" +
                    "INNER JOIN first_level_divisions d on a.Division_ID = d.Division_ID\n" +
                    "INNER JOIN countries c on d.COUNTRY_ID = c.Country_ID";

            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phoneNum = rs.getString("Phone");
                Date createDate = rs.getDate("Create_Date");
                String createdBy = rs.getString("Created_By");
                Date lastUpdated = rs.getDate("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                String division = rs.getString("Division");
                String country = rs.getString("Country");

                System.out.println("ID: " + customerId + " | Name: " + customerName + " | Address: " + address + " | Postal Code: " + postalCode +
                        " | Phone: " + phoneNum + " | Created: " + createDate + " | Created By: " + createdBy + " | Last Update: " + lastUpdated +
                        " | Last Updated By: " + lastUpdatedBy + " | Division: " + division + " | Country: " + country);

                customerList.add(new Customers(customerId, customerName, address, postalCode, phoneNum, createDate, createdBy, lastUpdated, lastUpdatedBy, division, country));
            }
        } catch(SQLException e){
            //do nothing
        }
        return customerList;
    }

    public static void select() throws SQLException {
        String sql = "SELECT * FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){

        }
    }
}
