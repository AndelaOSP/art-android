package com.andela.art.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Check in model.
 */

public class CheckInModel implements Serializable {

    @SerializedName("serial_number")
    private String mSerialNumber;
    @SerializedName("log_type")
    private String mLogType;

    /**
     * Log type Getter.
     * @return mLogType - string
     */
    public String getLogType() {
        return mLogType;
    }

    /**
     * Log type setter.
     * @param log - type of log
     */
    public void setLogType(String log) {
        mLogType = log;
    }

    /**
     * Serial number getter.
     * @return mSerialNumber - String
     */
    public String getSerialNumber() {
        return mSerialNumber;
    }

    /**
     * Serial number setter.
     * @param serial - String
     */
    public void setSerialNumber(String serial) {
        mSerialNumber = serial;
    }

}

