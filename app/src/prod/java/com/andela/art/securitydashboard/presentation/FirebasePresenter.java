package com.andela.art.securitydashboard.presentation;




import com.andela.art.root.Presenter;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by zack on 3/29/18.
 */

public class FirebasePresenter implements Presenter<SerialView> {
    private SerialView serialView;
    private final FirebaseAuth firebaseAuth;
    private final FirebaseAuth.AuthStateListener authStateListener;

    /**
     * Constructor.
     * @param firebaseAuth firebaseAuth
     * @param authStateListener authStateListener
     */
    public FirebasePresenter(FirebaseAuth firebaseAuth,
                             FirebaseAuth.AuthStateListener authStateListener) {
        this.firebaseAuth = firebaseAuth;
        this.authStateListener = authStateListener;
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
        if (firebaseAuth.getCurrentUser() == null) {
            serialView.redirectLoggedOutUser();
        } else {
            setAccountDetails();
        }
    }

    /**
     * Set account details.
     */
    public void setAccountDetails() {
       String email = firebaseAuth.getCurrentUser().getEmail();
       String name = firebaseAuth.getCurrentUser().getDisplayName();
       String photo = firebaseAuth.getCurrentUser().getPhotoUrl().toString();
       serialView.setAccountDetails(email, name, photo);
    }
}
