package com.andela.art;

import android.net.Uri;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.internal.firebase_auth.zzcz;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.auth.UserInfo;

import java.util.List;

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
        FirebaseUser user = new FirebaseUser() {
            @NonNull
            @Override
            public String getUid() {
                return null;
            }

            @NonNull
            @Override
            public String getProviderId() {
                return null;
            }

            @Override
            public boolean isAnonymous() {
                return false;
            }

            @Nullable
            @Override
            public List<String> getProviders() {
                return null;
            }

            @NonNull
            @Override
            public List<? extends UserInfo> getProviderData() {
                return null;
            }

            @NonNull
            @Override
            public FirebaseUser zza(@NonNull List<? extends UserInfo> list) {
                return null;
            }

            @Override
            public FirebaseUser zzce() {
                return null;
            }

            @NonNull
            @Override
            public FirebaseApp zzcc() {
                return null;
            }

            @Nullable
            @Override
            public String getDisplayName() {
                return "Isaiah King'ori";
            }

            @Nullable
            @Override
            public Uri getPhotoUrl() {
                return Uri.parse("file:///android_asset/images/dummyPhoto.png");
            }

            @Nullable
            @Override
            public String getEmail() {
                return "isaiah.kingori@andela.com";
            }

            @Nullable
            @Override
            public String getPhoneNumber() {
                return null;
            }

            @Nullable
            @Override
            public String zzcf() {
                return null;
            }

            @NonNull
            @Override
            public zzcz zzcg() {
                return null;
            }

            @Override
            public void zza(@NonNull zzcz zzcz) {

            }

            @NonNull
            @Override
            public String zzch() {
                return null;
            }

            @NonNull
            @Override
            public String zzci() {
                return null;
            }

            @Nullable
            @Override
            public FirebaseUserMetadata getMetadata() {
                return null;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {

            }

            @Override
            public boolean isEmailVerified() {
                return false;
            }
        };
        return user;
    }
}
