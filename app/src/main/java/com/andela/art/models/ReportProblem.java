package com.andela.art.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Asset POJO class.
 */
public class ReportProblem implements Serializable {

    @SerializedName("reportedBy")
    @Expose
    private String reportedBy;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("reportType")
    @Expose
    private String reportType;

    /**
     * Set message.
     *
     * @param message message submitted
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Return the message.
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set report type.
     *
     * @param type The type of the report
     */
    public void setReportType(String type) {
        this.reportType = type;
    }

    /**
     * Get report type.
     *
     * @return reportType The type of the report
     */
    public String getReportType() {
        return reportType;
    }

    /**
     * Set reporter.
     *
     * @param reportedBy The email sending the report
     */
    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    /**
     * Get the person who made the submitted the report.
     *
     * @return reportType The type of the report
     */
    public String getReportedBy() {
        return reportedBy;
    }

}
