package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.constant.Constable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Holds the methods to retrieve User information from the database. */
public abstract class UserQuery {

    /** Queries the database for a user with the given username and password.
     * @param username The username input of the application user.
     * @param password The password input of the application user.
     * @return True if the credentials are valid, false otherwise.
     */
    public static boolean userLogin(String username, String password){

        String sql = "SELECT * FROM users";
        try{
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String rsUsername = rs.getString("User_Name");
                String rsPassword = rs.getString("Password");

                if (username.equals(rsUsername) && password.equals(rsPassword)){
                    System.out.println("Login successful");
                    return true;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }


        return false;
    }

    /** Queries the database for all user IDs.
     * @return An observable list of user IDs.*/
    public static ObservableList<Integer> getAllUserIds(){
        ObservableList<Integer> allUserId = FXCollections.observableArrayList();
        String sql = "SELECT User_ID FROM users\n" +
                "ORDER BY User_ID ASC;";

        try{
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int userId = rs.getInt("User_ID");
                allUserId.add(userId);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return allUserId;
    }

}
