package com.andela.art.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * A basic class representing an entity that is a row in a three-column database table.
 *
 * @ Entity - You must annotate the class as an entity and supply a table name if not class name.
 * @ PrimaryKey - You must identify the primary key.
 *
 */

@Entity(tableName = "checkIn")
public class CheckInEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int serialNumber;

    private String logStatus;

    /**
     * Id status getter.
     * @return id - int
     */
    public int getId() {
        return id;
    }

    /**
     * Id status setter.
     * @param id - int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * SerialNumber status getter.
     * @return serialNumber - int
     */
    public int getSerialNumber() {
        return serialNumber;
    }

    /**
     * SerialNumber status setter.
     * @param serialNumber - int
     */
    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * Log status getter.
     * @return logStatus - String
     */
    public String getLogStatus() {
        return logStatus;
    }

    /**
     * LogStatus status setter.
     * @param logStatus - string
     */
    public void setLogStatus(String logStatus) {
        this.logStatus = logStatus;
    }
}
