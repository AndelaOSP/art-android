package com.andela.art.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.andela.art.R;
import com.andela.art.databinding.ActivityLoginBinding;
import com.andela.art.login.injection.DaggerLoginComponent;
import com.andela.art.login.injection.LoginModule;
import com.andela.art.root.ApplicationComponent;
import com.andela.art.root.ApplicationModule;
import com.andela.art.root.ArtApplication;
import com.andela.art.securitydashboard.presentation.NfcSecurityDashboardActivity;
import com.andela.art.userdashboard.presentation.UserDashBoardActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

/**
 * LoginActivity handles the login of user into the application.
 */
public class LoginActivity extends AppCompatActivity implements SecurityEmailsView,
        TokenAuthView {

    @Inject GoogleSignInClient mGoogleSignInClient;
    @Inject SecurityEmailsPresenter securityEmailsPresenter;
    @Inject TokenAuthPresenter tokenAuthPresenter;
    FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 2;
    static final String TAG = "LoginActivity";
    ProgressDialog mConnectionProgressDialog;
    Intent dashboard;
    boolean andelan;

    public List<String> allowedEmailAddresses = new ArrayList();
    private static final int UNAUTHORIZED_CODE = 14672;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ApplicationComponent applicationComponent = ((ArtApplication) getApplication())
                .applicationComponent();
        DaggerLoginComponent.builder()
                .applicationComponent(applicationComponent)
                .applicationModule(new ApplicationModule(getApplication()))
                .loginModule(new LoginModule())
                .build()
                .inject(this);
        securityEmailsPresenter.attachView(this);
        tokenAuthPresenter.attachView(this);
        securityEmailsPresenter.retrieveOauthToken();

        dashboard = new Intent(LoginActivity.this, NfcSecurityDashboardActivity.class);
        ActivityLoginBinding activityLoginBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_login);
        activityLoginBinding.googleSignInButton.setOnClickListener(view -> signIn());

        mAuth = FirebaseAuth.getInstance();

        mConnectionProgressDialog = new ProgressDialog(this);
        mConnectionProgressDialog.setMessage("Signing in...");

        AppCenter.start(getApplication(), "f37dc08d-4d29-4c85-bf2c-82eb95a916e6",
        Analytics.class, Crashes.class);
    }

    /**
     * Log in with google.
     * @param account - google account
     */
    public void firebasewithGoogleAuth(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider
                .getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        tokenAuthPresenter.saveToken(new TokenAuthPresenter.CompletionListener() {
                            @Override
                            public void onComplete() {
                                // Add check if user is admin here in future
                                //This has been implemented using the mAuthListener in onCreate()
                                if (andelan) {
                                    Intent intent = new Intent(LoginActivity.this,
                                            UserDashBoardActivity.class);
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(LoginActivity.this,
                                            NfcSecurityDashboardActivity.class);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onError() {

                            }
                        });


                    } else {
                        // Hide the progress dialog if its showing.
                        mConnectionProgressDialog.dismiss();
                        // If sign in fails, display a message to the user.
                        Toast.makeText(LoginActivity.this,
                                "Authentication Failed: "
                                        + "Ensure you have an internet connection.",
                                Toast.LENGTH_LONG).show();
                    }
                });

    }

    /**
     * Sign in and redirect.
     */
    public void signIn() {
        // Show the dialog as we are now signing in.
        mConnectionProgressDialog.show();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                String emailDomain = account.getEmail().split("@")[1];

                // filter out Andela email addresses
                if ("andela.com".equals(emailDomain)) {
                    andelan = true;
                    firebasewithGoogleAuth(account);
                } else {
                    // filter out only specific GMail addresses assigned to the guards
                    if (isAllowedNonAndelaEmail(account.getEmail())) {
                        andelan = false;
                        firebasewithGoogleAuth(account);
                    } else {
                        mGoogleSignInClient.signOut();
                        throw new ApiException(new Status(UNAUTHORIZED_CODE));
                    }

                }

            } catch (ApiException e) {
                // Hide the progress dialog if its showing.
                mConnectionProgressDialog.dismiss();
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                if (e.getStatusCode() == UNAUTHORIZED_CODE) {
                    Toast.makeText(LoginActivity.this,
                            "Please use your Andela email address",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Authorization Failed: Ensure you have selected a Google account.",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    /**
     * Check to see if a given non Andela GMail address is among the ones allowed to login.
     *
     * @param email Email address for the user
     * @return boolean true if an email is allowed, false if the email is not allowed
     */
    public boolean isAllowedNonAndelaEmail(String email) {
        return !allowedEmailAddresses.isEmpty() && allowedEmailAddresses.contains(email);
    }

    /**
     * Populate allowed emails with emails from endpoint.
     * @param emails - list of emails
     */
    @Override
    public void populateEmailList(List<String> emails) {
        Log.d(TAG, "populateEmailList: " + emails);
        if (emails.isEmpty()) {
            return;
        }
        allowedEmailAddresses.addAll(emails);
    }

    /**
     * Report error.
     * @param exception - exception
     */
    @Override
    public void reportError(Exception exception) {
        Toast.makeText(this, exception.getMessage().toString(), Toast.LENGTH_LONG).show();
    }
}
