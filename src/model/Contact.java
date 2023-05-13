package model;

/** Class for holding Contact information. */
public class Contact {

    private int contactId;
    private String name;
    private String email;

    /** Constructor for creating a Contact object.
     * @param contactId The unique ID of the contact.
     * @param name The name of the contact.
     * @param email The email address of the contact. */
    public Contact(int contactId, String name, String email) {
        this.contactId = contactId;
        this.name = name;
        this.email = email;
    }

    /** Retrieves the contact ID.
     * @return The integer of the contact ID.*/
    public int getContactId() {
        return contactId;
    }

    /** Sets the contact ID.
     * @param contactId The integer of the unique contact ID. */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /** Retrieves the contact name.
     * @return The string of the contact name.*/
    public String getName() {
        return name;
    }

    /** Sets the contact name.
     * @param name The string of the contact name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Retrieves the contact email address.
     * @return The string of the contact email address.*/
    public String getEmail() {
        return email;
    }

    /** Sets the contact email address.
     * @param email The string of the contact email address. */
    public void setEmail(String email) {
        this.email = email;
    }

    /** Overrides the toString() method to return just the contactId and the contact name.
     * @return The string containing the contact ID and the contact name. */
    @Override
    public String toString(){
        return (contactId + " " + name);

    }
}
