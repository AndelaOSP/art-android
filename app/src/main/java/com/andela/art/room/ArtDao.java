package com.andela.art.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


/**
 * The Room Magic is in this file, where you map a Java method call to an SQL query.
 * Use @Insert(onConflict = OnConflictStrategy.REPLACE) to update a row.
 */

@Dao
public interface ArtDao {

    /**
     * Add items into the database.
     * @param checkInEntity - CheckInEntity
     */
    @Insert
    void insertCheckIn(CheckInEntity checkInEntity);

    /**
     * Get checkin data.
     * @return Return list of checkIn entities.
     */
    @Query("SELECT * FROM checkIn")
    List<CheckInEntity> getAllCheckInData();

    /**
     * Delete all Check In Records.
     */
    @Query("DELETE FROM checkIn")
    void deleteAllRecords();

}
