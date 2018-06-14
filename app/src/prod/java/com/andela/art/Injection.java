package com.andela.art;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Enables injection of mock implementations for {@link FirebaseUser}.
 * This is useful for testing, since it allows us to use
 * a fake instance of the class to isolate the dependencies and run a test hermetically.
 */
public class Injection {
    /**
     * Provides a user to any presenter that needs a firebase user.
     * @return FirebaseUser
     */
    public static FirebaseUser provideGetUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }
}
