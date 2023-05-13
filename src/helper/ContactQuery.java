package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Holds the methods to retrieve contact information from the database. */
public class ContactQuery {

    /** Retrieves all contacts from the database.
     * @return An ObservableList of Contact objects.*/
    public static ObservableList<Contact> getAllContacts(){
        ObservableList allContacts = FXCollections.observableArrayList();

        String sql = "SELECT * FROM contacts";

        try{
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");

                Contact contact = new Contact(contactID, contactName, contactEmail);
                allContacts.add(contact);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return allContacts;
    }

    /** Retrieves a specific contact from the database.
     * @param contact The string containing the name of the contact.
     * @return The Contact object with the specified name. */
    public static Contact getContact(String contact){
        Contact foundContact = new Contact(0, null, null);
        String sql = "SELECT * FROM contacts WHERE Contact_Name = ?";

        try{
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, contact);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");

                foundContact.setContactId(contactID);
                foundContact.setName(contactName);
                foundContact.setEmail(contactEmail);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return foundContact;
    }

    /** Retrieves the ID for a specific contact from the database.
     * @param contact The name string of the contact to retrieve from the database.
     * @return The ID integer of the associated contact.*/
    public static int getContactId(String contact){
        int contactId;
        String sql = "SELECT Contact_ID FROM contacts WHERE Contact_Name = ?";

        try{
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, contact);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){

                contactId = rs.getInt("Contact_ID");
                return contactId;
            }


        }catch (SQLException e){
            e.printStackTrace();
        }

        return 0;
    }
}
