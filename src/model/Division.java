package model;

/** Class for holding Division information. */
public class Division {
    private int divisionID;
    private String divisionName;
    private int countryId;

    /** Constructor for creating a Division object. */
    public Division(int divisionID, String divisionName, int countryId) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryId = countryId;
    }

    /** Retrieves the ID of the division.
     * @return The integer ID of the division. */
    public int getDivisionID() {
        return divisionID;
    }

    /** Sets the unique ID for the division.
     * @param divisionID The unique ID of the division. */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /** Retrieves the ID of the country.
     * @return The string of the division name. */
    public String getDivisionName() {
        return divisionName;
    }

    /** Sets the name for the division.
     * @param divisionName The name of the division. */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /** Retrieves the countryID of the division.
     * @return The integer ID of the division. */
    public int getCountryId() {
        return countryId;
    }

    /** Sets the country ID for the division.
     * @param countryId The country ID associated with the division. */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /** Overrides the toString() method to return just the division name.
     * @return The string containing the division name. */
    @Override
    public String toString() {
        return divisionName;
    }
}
