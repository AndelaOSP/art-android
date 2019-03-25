package com.andela.art.room;

import android.app.Application;
import android.os.AsyncTask;

import com.andela.art.checkin.CheckInPresenter;

import java.util.List;

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */

@SuppressWarnings("PMD.ImmutableField")
public class CheckInRepository {
    private  ArtDao mArtDao;
    private CheckInPresenter presenter;

    /**
     * Set presenter value.
     * @param presenter CheckinPresenter
     */
    public void setPresenter(CheckInPresenter presenter) {
        this.presenter = presenter;
    }

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
     *
     */
    public void query() {
        new QueryAsyncTask(mArtDao).execute();
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

    /**
     * Non-UI thread to query data from the DB.
     */
    private class QueryAsyncTask extends AsyncTask<Void, Void, Void> {

        private ArtDao mAsyncTaskDao;

        /**
         * Constructor.
         * @param mArtDao ArtDao.
         */
        QueryAsyncTask(ArtDao mArtDao) {
            mAsyncTaskDao = mArtDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            List<CheckInEntity> checkInData = mAsyncTaskDao.getAllCheckInData();
            if (checkInData.isEmpty()) {
                //Do nothing
                return null;
            } else {
                presenter.checkIn(checkInData.get(0).getId(), //NOPMD
                        checkInData.get(0).getLogStatus()); //NOPMD
                mAsyncTaskDao.deleteAllRecords();

            }
            return null;
        }
    }

}
