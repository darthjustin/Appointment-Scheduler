package model;

/** Class for holding TypeReport information for reporting. */
public class TypeReport {

    private String month;

    private String type;

    private int count;

    /** Constructor for creating a TypeReport object.
     * This holds information for reporting the number of appointments per month.
     * @param month The month of the appointments.
     * @param type The type of appointments.
     * @param count The total number of the associated appointment type per month. */
    public TypeReport(String month, String type, int count) {
        this.month = month;
        this.type = type;
        this.count = count;
    }

    /** Retrieves the month of the assocaiated appointment type.
     * @return The string of the month. */
    public String getMonth() {
        return month;
    }

    /** Sets the month of the TypeReport.
     * @param month The month of the TypeReport. */
    public void setMonth(String month) {
        this.month = month;
    }

    /** Retrieves the type of appointment.
     * @return The string of the appointment type. */
    public String getType() {
        return type;
    }

    /** Sets the type of the TypeReport.
     * @param type The type of the TypeReport. */
    public void setType(String type) {
        this.type = type;
    }

    /** Retrieves the count of the associated appointment type per month.
     * @return The integer of the count. */
    public int getCount() {
        return count;
    }

    /** Sets the count of the TypeReport.
     * @param count The count of the TypeReport. */
    public void setCount(int count) {
        this.count = count;
    }

    /** Overrides the toString() method to return the month, type, and count.
     * @return The string containing the country name. */
    @Override
    public String toString(){
        String string = "Month: " +getMonth() + " Type: " + getType() + " Count: " + getCount();
        return string;
    }
}
