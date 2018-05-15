package com.andela.art.login;

import android.util.Log;

import com.andela.art.root.Presenter;
import com.andela.art.root.SharedPrefsWrapper;
import com.google.firebase.auth.FirebaseAuth;


/**
 * Created by zack on 4/18/18.
 */
public class TokenAuthPresenter implements Presenter<TokenAuthView> {

    private TokenAuthView tokenAuthView;
    private final FirebaseAuth firebaseAuth;
    private final SharedPrefsWrapper sharedPrefsWrapper;

    /**
     *
     * @param firebaseAuth - firebaseAuth
     * @param sharedPrefsWrapper - sharedPrefsWrapper
     */
    public TokenAuthPresenter(FirebaseAuth firebaseAuth, SharedPrefsWrapper sharedPrefsWrapper) {
        this.firebaseAuth = firebaseAuth;
        this.sharedPrefsWrapper = sharedPrefsWrapper;
    }

    /**
     * Save token retrieved from firebase to shared preferences.
     * @param completionListener - Completion listener.
     */
    public void saveToken(CompletionListener completionListener) {
        firebaseAuth.getCurrentUser().getIdToken(true)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String idToken = task.getResult().getToken();
                        Log.d("CURRENT", idToken);
                        sharedPrefsWrapper.putString("AUTH_TOKEN", idToken);
                        Log.d("AUTH_TOKEN", sharedPrefsWrapper.getString("AUTH_TOKEN"));
                        completionListener.onComplete();
                    } else {
                        tokenAuthView.reportError(task.getException());
                        completionListener.onError();
                    }
                });

    }

    /**
     * @param view view that will be instantiated by the presenter
     */
    @Override
    public void attachView(TokenAuthView view) {
        this.tokenAuthView = view;
    }

    /**
     * Login completion callback. Called when firebase token is fetched or if an error occurs.
     */
    interface CompletionListener {
        /**
         * Called when token is fetched.
         */
        void onComplete();

        /**
         * Called when error occurs.
         */
        void onError();
    }
}
