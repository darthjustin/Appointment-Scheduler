package model;

/** Class for holding Country information. */
public class Country {
    private int id;
    private String name;
    private int count;

    /** Constructor to create a Country object.
     * @param id The unique ID of the country.
     * @param name The name of the country. */
    public Country(int id, String name){
        this.id = id;
        this.name = name;
    }

    /** Retrieves the ID of the country.
     * @return The integer of the country ID. */
    public int getId() {
        return id;
    }

    /** Sets the unique ID for the country.
     * @param id The unique ID of the country. */
    public void setId(int id) {
        this.id = id;
    }

    /** Retrieves the name of the country.
     * @return The string of the country name. */
    public String getName() {
        return name;
    }

    /** Sets the name for the country.
     * @param name The name of the country. */
    public void setName(String name) {
        this.name = name;
    }

    /** Sets the number of times the country appears in the database.
     * @param count The integer of the country count. */
    public void setCount(int count){
        this.count = count;
    }

    /** Retrieves the number of times a country appears in the appointment list.
     * @return The integer of the country count. */
    public int getCount(){
        return count;
    }

    /** Overrides the toString() method to return just the country name.
     * @return The string containing the country name. */
    @Override
    public String toString(){
        return name;
    }

}
