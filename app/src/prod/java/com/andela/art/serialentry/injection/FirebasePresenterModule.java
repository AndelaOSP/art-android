package com.andela.art.serialentry.injection;

import com.andela.art.serialentry.presentation.FirebasePresenter;
import com.google.firebase.auth.FirebaseAuth;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zack on 3/29/18.
 */
@Activity
@Module
public class FirebasePresenterModule {

    /**
     * Provide firebase presenter.
     * @param firebaseAuth - firebaseAuth
     * @param authStateListener - authStateListener
     * @return FirebasePresenter
     */
    @Activity
    @Provides
    FirebasePresenter provideFirebasePresenter(FirebaseAuth firebaseAuth,
                                               FirebaseAuth.AuthStateListener authStateListener) {
        return new FirebasePresenter(firebaseAuth, authStateListener);
    }
}
