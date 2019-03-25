package com.andela.art.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * This is the backend. The Art database. This used to be done by the OpenHelper.
 */

@Database(entities = {CheckInEntity.class}, version = 1)
public abstract class ArtDatabase extends RoomDatabase {

    private static volatile ArtDatabase instance;

    /**
     * Data Access Object.
     * @return transaction status
     */
    public abstract ArtDao mArtDao();

    /**
     * Get database to ensure atomic access to the variable.
     * @param context - Context
     * @return instance - database instance
     */
    static ArtDatabase getDatabase(final Context context) {
        synchronized (ArtDatabase.class) {
            if (instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                            ArtDatabase.class, "artdb").build();
            }
        }
        return instance;
    }

}
