package com.andela.art.room;

import android.app.Application;
import android.os.AsyncTask;

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */

@SuppressWarnings("PMD.ImmutableField")
public class CheckInRepository {
    private  ArtDao mArtDao;

    /**
     * Check in repository constructor.
     * @param application - Application.
     */
    public CheckInRepository(Application application) {
        ArtDatabase db = ArtDatabase.getDatabase(application);
        mArtDao = db.mArtDao();
    }

    /**
     * You must call this on a non-UI thread or your app will crash.
     * @param mCheckInEntity - CheckInEntity.
    */
    public void insert(CheckInEntity mCheckInEntity) {
        new InsertAsyncTask(mArtDao).execute(mCheckInEntity);
    }

    /**
     * non-UI thread to insert data into the DB.
     */
    private static class InsertAsyncTask extends AsyncTask<CheckInEntity, Void, Void> {

        private  ArtDao mAsyncTaskDao;

        /**
         * InsertAsyncTask constructor.
         * @param dao - ArtDao.
         */
        InsertAsyncTask(ArtDao dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(CheckInEntity... checkInEntities) {
            mAsyncTaskDao.insertCheckIn(checkInEntities[0]);
            return null;
        }
    }
}
