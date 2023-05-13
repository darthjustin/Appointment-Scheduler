package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/** Holds the methods to retrieve country information from the database. */
public class CountryQuery {

    /** Retrieves a list of countries from the database.
     * @return An ObservableList of Country objects.*/
    public static ObservableList<Country> getAllCountries() {
        ObservableList<Country> countryList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM countries";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int country_ID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");

                Country c = new Country(country_ID, countryName);

                countryList.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countryList;

    }

    /** Retrieves a list of first level divisions from the database.
     * @param country The Country object with the first level division information.
     * @return An ObservableList of Division objects.
     * */
    public static ObservableList<Division> getDivisions(Country country) {
        ObservableList<Division> divisionList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Division_ID, Division, Country_ID FROM first_level_divisions\n" +
                    "WHERE Country_ID = ?\n" +
                    "ORDER BY Division ASC";

            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, country.getId());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divId = rs.getInt("Division_ID");
                String div = rs.getString("Division");
                int countryID = rs.getInt("Country_ID");

                divisionList.add(new Division(divId, div, countryID));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return divisionList;
    }

    /** Retrieves the country associated with a first level division.
     * @param divisionID The ID of the division associated with a particular country.
     * @return The Country object related to the first level division. */
    public static Country getCountryByDivision(int divisionID) {
        String sql = "SELECT d.*, c.Country FROM first_level_divisions d\n" +
                "INNER JOIN countries c on c.Country_ID = d.COUNTRY_ID\n" +
                "WHERE Division_ID = ?";
        Country country = new Country(0, null);

        try {

            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, divisionID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("COUNTRY_ID");
                String name = rs.getString("Country");

                country.setId(id);
                country.setName(name);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }

    /** Retrieves a first level division record from the database.
     * @param divisionID The ID of the first level division to be retrieved from the database.
     * @return The Division object associated with the ID. */
    public static Division getDivisionByID(int divisionID) {
        String sql = "SELECT * FROM first_level_divisions\n" +
                "WHERE Division_ID = ?";
        Division division = new Division(0, null, 0);

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, divisionID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Division_ID");
                String name = rs.getString("Division");
                int countryId = rs.getInt("COUNTRY_ID");

                division.setDivisionID(id);
                division.setDivisionName(name);
                division.setCountryId(countryId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return division;
    }

    /** Retrieves the number of customers for each country in the database.
     * @return An ObservableList of Country objects.*/
    public static ObservableList numCustomersByCountry() {
        ObservableList<Country> countries = FXCollections.observableArrayList();
        String sql = "SELECT c.Country_ID, c.country, COUNT(a.Customer_ID) as Count FROM customers a\n" +
                "INNER JOIN first_level_divisions d on a.Division_ID = d.Division_ID\n" +
                "INNER JOIN countries c on d.COUNTRY_ID = c.Country_ID\n" +
                "GROUP BY c.country_ID, c.country;";

        try {
           PreparedStatement ps = JDBC.connection.prepareStatement(sql);
           ResultSet rs = ps.executeQuery();

           while(rs.next()){
               int id = rs.getInt("Country_ID");
               String name = rs.getString("Country");
               int count = rs.getInt("Count");

               Country country = new Country(id, name);
               country.setCount(count);

               countries.add(country);
           }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return countries;
    }
}
