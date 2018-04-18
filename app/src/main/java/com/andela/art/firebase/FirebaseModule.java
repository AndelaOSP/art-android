package com.andela.art.firebase;

import com.google.firebase.auth.FirebaseAuth;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zack on 3/29/18.
 */

@Module
public class FirebaseModule {

    /**
     * Provide FirebaseAuth.
     * @return FirebaseAuth instance
     */
    @Provides
    FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    /**
     *  Provide AuthStateListener.
     * @return authStateListener
     */
    @Provides
    FirebaseAuth.AuthStateListener providesAuthStateListener() {
        return firebaseAuth -> {

        };
    }

}
