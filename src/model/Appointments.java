package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

/** Class for holding Appointment information. */
public class Appointments {

    private int apptId;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int custId;
    private int userId;

    /** Constructor to create an Appointment object.
     * @param apptId The unique ID of the appointment.
     * @param title The title of the appointment.
     * @param description The description of the appointment.
     * @param location The location of the appointment.
     * @param contact The contact associated with the appointment.
     * @param type The type of appointment.
     * @param start The start date and time of the appointment.
     * @param end The end date and time of the appointment.
     * @param custId The ID of the customer associated with the appointment.
     * @param userId the ID of the user associated with the appointment.*/
    public Appointments(int apptId, String title, String description, String location, String contact, String type, LocalDateTime start, LocalDateTime end, int custId, int userId) {
        this.apptId = apptId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.start = start;
        this.end = end;
        this.custId = custId;
        this.userId = userId;
    }

    /** Retrieves an appointment ID.
     * @return The integer of the appointment ID.*/
    public int getApptId() {
        return apptId;
    }

    /** Sets an appointment ID.
     * @param apptId  The integer of the appointment ID.*/
    public void setApptId(int apptId) {
        this.apptId = apptId;
    }

    /** Retrieves an appointment title.
     * @return The string of the appointment title.*/
    public String getTitle() {
        return title;
    }

    /** Sets an appointment title.
     * @param title  The string of the appointment title.*/
    public void setTitle(String title) {
        this.title = title;
    }

    /** Retrieves an appointment description.
     * @return The string of the appointment description.*/
    public String getDescription() {
        return description;
    }

    /** Sets an appointment description.
     * @param description  The string of the appointment description.*/
    public void setDescription(String description) {
        this.description = description;
    }

    /** Retrieves an appointment location.
     * @return The string of the appointment location.*/
    public String getLocation() {
        return location;
    }

    /** Sets an appointment location.
     * @param location  The string of the appointment location.*/
    public void setLocation(String location) {
        this.location = location;
    }

    /** Retrieves an appointment contact.
     * @return The string of the appointment contact.*/
    public String getContact() {
        return contact;
    }

    /** Sets an appointment contact.
     * @param contact  The string of the appointment contact.*/
    public void setContact(String contact) {
        this.contact = contact;
    }

    /** Retrieves an appointment type.
     * @return The string of the appointment type.*/
    public String getType() {
        return type;
    }

    /** Sets an appointment type.
     * @param type  The string of the appointment type.*/
    public void setType(String type) {
        this.type = type;
    }

    /** Retrieves an appointment start date and time.
     * @return The LocalDateTime object of the appointment start date and time.*/
    public LocalDateTime getStart() {

        return start;
    }

    /** Sets an appointment start date and time.
     * @param start The appointment start date and time.*/
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /** Retrieves an appointment end date and time.
     * @return The LocalDateTime object of the appointment end date and time.*/
    public LocalDateTime getEnd() {
        return end;
    }

    /** Sets an appointment end date and time.
     * @param end The appointment end date and time.*/
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /** Retrieves an appointment associated customer ID.
     * @return The interger of the associated customer ID.*/
    public int getCustId() {
        return custId;
    }

    /** Sets an appointment associated customer ID.
     * @param custId The integer associated customer ID. */
    public void setCustId(int custId) {
        this.custId = custId;
    }

    /** Retrieves an appointment associated user ID.
     * @return The interger of the associated user ID.*/
    public int getUserId() {
        return userId;
    }

    /** Sets an appointment associated user ID.
     * @param userId The integer associated user ID. */
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
