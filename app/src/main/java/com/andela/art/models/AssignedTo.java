
package com.andela.art.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Assigned to model.
 */
public class AssignedTo implements Serializable {

    @SerializedName("cohort")
    private int mCohort;
    @SerializedName("date_joined")
    private String mDateJoined;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("first_name")
    private String mFirstName;
    @SerializedName("full_name")
    private String mFullName;
    @SerializedName("id")
    private int mId;
    @SerializedName("last_login")
    private String mLastLogin;
    @SerializedName("last_modified")
    private String mLastModified;
    @SerializedName("last_name")
    private String mLastName;
    @SerializedName("phone_number")
    private String mPhoneNumber;
    @SerializedName("picture")
    private String mPicture;
    @SerializedName("slack_handle")
    private String mSlackHandle;

    /**
     * Cohort number getter.
     * @return mCohort - int
     */
    public int getCohort() {
        return mCohort;
    }

    /**
     * Cohort number setter.
     * @param cohort - int
     */
    public void setCohort(int cohort) {
        mCohort = cohort;
    }

    /**
     * Date joined getter.
     * @return mDateJoined - String
     */
    public String getDateJoined() {
        return mDateJoined;
    }

    /**
     * Date joined setter.
     * @param dateJoined - String
     */
    public void setDateJoined(String dateJoined) {
        mDateJoined = dateJoined;
    }

    /**
     * Email getter.
     * @return mEmail - String
     */
    public String getEmail() {
        return mEmail;
    }

    /**
     * Email setter.
     * @param email - String
     */
    public void setEmail(String email) {
        mEmail = email;
    }

    /**
     * First name getter.
     * @return mFirstName - String
     */
    public String getFirstName() {
        return mFirstName;
    }

    /**
     * First name setter.
     * @param firstName - String
     */
    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    /**
     * Full name getter.
     * @return mFullName - String
     */
    public String getFullName() {
        return mFullName;
    }

    /**
     * Full name setter.
     * @param fullName - String
     */
    public void setFullName(String fullName) {
        mFullName = fullName;
    }

    /**
     * User id getter.
     * @return mId - int
     */
    public int getId() {
        return mId;
    }

    /**
     * User id setter.
     * @param id - int
     */
    public void setId(int id) {
        mId = id;
    }

    /**
     * Last login getter.
     * @return mLastLogin - String
     */
    public String getLastLogin() {
        return mLastLogin;
    }

    /**
     * Last login setter.
     * @param lastLogin - string
     */
    public void setLastLogin(String lastLogin) {
        mLastLogin = lastLogin;
    }

    /**
     * Last modified getter.
     * @return mLastModified - String
     */
    public String getLastModified() {
        return mLastModified;
    }

    /**
     * Last modified setter.
     * @param lastModified - String
     */
    public void setLastModified(String lastModified) {
        mLastModified = lastModified;
    }

    /**
     * Last Name getter.
     * @return mLastName - String
     */
    public String getLastName() {
        return mLastName;
    }

    /**
     * User Last Name setter.
     * @param lastName - String
     */
    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    /**
     * User phone number getter.
     * @return mPhoneNumber - String
     */
    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    /**
     * User phone number setter.
     * @param phoneNumber - String
     */
    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    /**
     * User image url getter.
     * @return mPicture - String
     */
    public String getPicture() {
        return mPicture;
    }

    /**
     * User image url setter.
     * @param picture - String
     */
    public void setPicture(String picture) {
        mPicture = picture;
    }

    /**
     * User slack handle getter.
     * @return mSlackHandle - String
     */
    public String getSlackHandle() {
        return mSlackHandle;
    }

    /**
     * User slack handle setter.
     * @param slackHandle - String
     */
    public void setSlackHandle(String slackHandle) {
        mSlackHandle = slackHandle;
    }

}
