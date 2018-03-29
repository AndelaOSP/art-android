package com.andela.art.login;

/**
 * Created by Mugiwara_Munyi on 28/02/2018.
 */

public class User {
    private int id;
    private String firstName;
    private String lastName;

    /**
     * Set the first name and last name of the user.
     *  */
    public User() {
        this.firstName = "Fox";
        this.lastName = "Mulder";
    }

    /**
     * Get user ID.
     *
     * @return id - The user id.
     */
    public int getId() {
        return id;
    }

    /**
     * Set user ID.
     *
     */
    public void setId() {
        this.id = 0;
    }

    /**
     * Get first name of the user.
     *
     * @return firstName - The first name of the user.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the first name of the user.
     *
     * @param firstName - The first name of the user.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get the last name of the user.
     *
     * @return lastName - The last name of the user.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the last name of the user.
     *
     * @param lastName - The last name of the user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
