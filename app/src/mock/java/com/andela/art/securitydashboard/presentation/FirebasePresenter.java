package com.andela.art.securitydashboard.presentation;

import com.andela.art.api.UserAssetResponse;
import com.andela.art.models.Asset;
import com.andela.art.root.NPresenter;
import com.andela.art.root.Presenter;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by zack on 3/29/18.
 */

public class FirebasePresenter implements Presenter<SerialView>, NPresenter<NfcView> {
    private NfcView nfcView;
    private SerialView serialView;
    private final FirebaseAuth firebaseAuth;
    private final FirebaseAuth.AuthStateListener authStateListener;
    public MockUser dummyUser;
    String imagePath = "file:///android_asset/images/dummyPhoto.png";

    /**
     * Constructor.
     * @param firebaseAuth firebaseAuth
     * @param authStateListener authStateListener
     */
    public FirebasePresenter(FirebaseAuth firebaseAuth,
                             FirebaseAuth.AuthStateListener authStateListener) {
        this.firebaseAuth = firebaseAuth;
        this.authStateListener = authStateListener;
        dummyUser = new MockUser("Zacharia Mwangi", "zac@gmail.com",
                imagePath);
    }

    /**
     * Attach the view to the nfcpresenter.
     * @param view view that will be instantiated by the presenter
     */
    @Override
    public void attachVieww(NfcView view) {
        this.nfcView = view;
    }

    /**
     * Attach the view to the presenter.
     * @param view view that will be instantiated by the presenter
     */
    @Override
    public void attachView(SerialView view) {
        this.serialView = view;
    }

    /**
     * Add auth state listener at the start of the activity.
     */
    public void start() {

        firebaseAuth.addAuthStateListener(authStateListener);
    }

    /**
     * Add auth state listener at the stop of the activity.
     */
    public void stop() {
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    /**
     * Called to check if user is logged in.
     */
    public void onAuthStateChanged() {
        if (dummyUser == null) {
            serialView.redirectLoggedOutUser();
        } else {
            setAccountDetails();
        }
    }

    /**
     * Set account details.
     */
    public void setAccountDetails() {
       String email = dummyUser.email;
       String name = dummyUser.displayName;
       String photo = dummyUser.photoURI;
       if (serialView == null) {
           serialView = new SerialView() {
               @Override
               public void onConfirmClicked(String serial, String assetCode) {

               }

               @Override
               public void sendIntent(UserAssetResponse asset) {

               }

               @Override
               public void redirectLoggedOutUser() {

               }

               @Override
               public void setAccountDetails(String email, String name, String photo) {

               }

               @Override
               public void displayErrorMessage(Throwable error) {

               }

               @Override
               public void handleToast(String toastMessage, Integer toastLength,
                                       Boolean showProgressBar) {

               }

               @Override
               public void handleCheckinIntent(Asset assetInfo) {

               }
           };
       }

       serialView.setAccountDetails(email, name, photo);
    }

    /**
     * Mock user class.
     */
    public static class MockUser {
        String displayName;
        String email;
        String photoURI;

        /**
         * MockUser constructor.
         * @param displayName - displayName
         * @param email - email
         * @param photoURI - photoURI
         */
        public MockUser(String displayName, String email, String photoURI) {
            this.displayName = displayName;
            this.email = email;
            this.photoURI = photoURI;
        }
    }
}
