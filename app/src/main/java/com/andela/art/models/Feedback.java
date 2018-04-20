package com.andela.art.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by allan on 17/04/2018.
 */

public class Feedback implements Serializable {

    @SerializedName("reported_by")
    @Expose
    private String reportedBy;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("report_type")
    @Expose
    private String reportType;

    /**
     * No args constructor for use in serialization.
     *
     */
    public Feedback() {
        // This constructor is empty. Needed for serialization
    }

    /**
     * All args constructor.
     *
     * @param message the feedback message a user types
     * @param reportType "feedback" report type
     * @param reportedBy the email address of the user
     */
    public Feedback(String reportedBy, String message, String reportType) {
        super();
        this.reportedBy = reportedBy;
        this.message = message;
        this.reportType = reportType;
    }

    /**
     * Get the email address of the user who wrote the feedback.
     *
     * @return reportedBy: the email address of the user
     */
    public String getReportedBy() {
        return reportedBy;
    }

    /**
     * Set the email address of the user who wrote the feedback.
     *
     * @param reportedBy the email address of the user
     */
    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    /**
     * Get the message of the feedback.
     *
     * @return the message in the feedback
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the message of the feedback.
     *
     * @param message the feedback message a user types
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get the report type.
     *
     * @return the report type which is "feedback"
     */
    public String getReportType() {
        return reportType;
    }

    /**
     * Set the report type.
     *
     * @param reportType "feedback" report type
     */
    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

}

