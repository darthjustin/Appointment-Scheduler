package helper;

import java.lang.constant.Constable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class UserQuery {

    public static boolean userLogin(String username, String password) throws SQLException {

           String sql = "SELECT * FROM users";
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

        return false;
    }

}
