package helper;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import model.Countries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DBCountries {
    public static ObservableList<Countries> getAllCountries() {
        ObservableList<Countries> countriesList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM countries";
            String dates = "SELECT Create_Date FROM countries";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            PreparedStatement psDates = JDBC.getConnection().prepareStatement(dates);
            ResultSet rsDates = psDates.executeQuery();

            while (rs.next()){
                int country_ID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");

                Countries c = new Countries(country_ID, countryName);

                countriesList.add(c);
            }
            while (rsDates.next()){
                Timestamp createdDate = rsDates.getTimestamp("Create_Date");
                System.out.println(createdDate.toLocalDateTime());
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return countriesList;

    }
}
