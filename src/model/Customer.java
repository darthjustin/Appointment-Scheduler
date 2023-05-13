package model;

/** Class for holding Customer information. */
public class Customer {

    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNum;
    private int divisionId;
    private String country;
    private String division;

    /** Constructor for creating a Customer object.
     * @param customerId The unique ID of the customer.
     * @param customerName The customer name.
     * @param address The customer address.
     * @param postalCode The customer postal code.
     * @param phoneNum The customer phone number.
     * @param divisionId The divisionID of the customer.
     * @param country The customer country.
     * @param division The division of the customer. */
    public Customer(int customerId, String customerName, String address, String postalCode, String phoneNum, int divisionId, String country, String division) {

        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNum = phoneNum;
        this.divisionId = divisionId;
        this.country = country;
        this.division = division;
    }

    /** Retrieves the unique ID of the customer.
     * @return The integer of the customer ID.*/
    public int getCustomerId() {

        return customerId;
    }

    /** Sets the unique customer ID.
     * @param customerId The unique ID of the customer. */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /** Retrieves the name of the customer.
     * @return The string of the customer name. */
    public String getCustomerName() {
        return customerName;
    }

    /** Sets the customer name.
     * @param customerName The name of the customer. */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /** Retrieves the address of the customer.
     * @return The string of the customer address. */
    public String getAddress() {
        return address;
    }

    /** Sets the customer address.
     * @param address The address of the customer. */
    public void setAddress(String address) {
        this.address = address;
    }

    /** Retrieves the postal code of the customer.
     * @return The string of the customer postal code.*/
    public String getPostalCode() {
        return postalCode;
    }

    /** Sets the customer postal code.
     * @param postalCode The postal code of the customer.*/
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /** Retrieves the phone number of the customer.
     * @return The string of the customer phone number.*/
    public String getPhoneNum() {
        return phoneNum;
    }

    /** Sets the customer phone number.
     * @param phoneNum The phone number of the customer. */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /** Retrieves the division ID of the customer.
     * @return The integer of the customer division ID.*/
    public int getDivisionId() {
        return divisionId;
    }

    /** Sets the customer division ID.
     * @param divisionId The division ID associated with the customer. */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /** Retrieves the country of the customer.
     * @return The string of the customer country.*/
    public String getCountry() {
        return country;
    }

    /** Sets the customer country.
     * @param country The country associated with the customer. */
    public void setCountry(String country) {
        this.country = country;
    }

    /** Retrieves the division of the customer.
     * @return The string of the customer division.*/
    public String getDivision(){
        return division;
    }

    /** Sets the customer division.
     * @param division The division associated with the customer. */
    public void setDivision(String division){
        this.division = division;
    }

    /** Overrides the toString() method to return just the customerID and the customerName.
     * @return The string containing the customer ID and customer name. */
    @Override
    public String toString(){
        return (customerId + " " + customerName);
    }
}
