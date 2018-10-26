package com.andela.art.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.andela.art.R;
import com.andela.art.root.ApplicationComponent;
import com.andela.art.databinding.ActivityLoginBinding;
import com.andela.art.login.injection.DaggerLoginComponent;
import com.andela.art.login.injection.LoginModule;
import com.andela.art.root.ApplicationModule;
import com.andela.art.securitydashboard.presentation.SecurityDashboardActivity;
import com.andela.art.root.ArtApplication;
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
    FirebaseAuth.AuthStateListener mAuthListener;
    ProgressDialog mConnectionProgressDialog;
    Intent dashboard;
    boolean andelan;

    public List<String> allowedEmailAddresses = new ArrayList();
    private static final int UNAUTHORIZED_CODE = 14672;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

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

        mAuthListener = firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() != null) {
                mConnectionProgressDialog.dismiss();
                // filter out Andela email addresses
                if (mAuth.getCurrentUser().getEmail().endsWith("andela.com")) {
                    Toast.makeText(this, "Andela email", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, UserDashBoardActivity.class);
                    startActivity(intent);

                } else {
                        // filter out only specific GMail addresses assigned to the guards
                        if (isAllowedNonAndelaEmail(mAuth.getCurrentUser().getEmail())) {
                            Toast.makeText(this, "Allowed non Andela email",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this,
                                    SecurityDashboardActivity.class);
                            startActivity(intent);
                        } else {
                            mGoogleSignInClient.signOut();
                            try {
                                throw new ApiException(new Status(UNAUTHORIZED_CODE));
                            } catch (ApiException e) {
                                e.printStackTrace();
                            }
                        }
                }
            }
        };

        dashboard = new Intent(LoginActivity.this, SecurityDashboardActivity.class);
        ActivityLoginBinding activityLoginBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_login);
        activityLoginBinding.googleSignInButton.setOnClickListener(view -> signIn());

        mAuth = FirebaseAuth.getInstance();

        mConnectionProgressDialog = new ProgressDialog(this);
        mConnectionProgressDialog.setMessage("Signing in...");
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
                                if (andelan) {
                                    Toast.makeText(LoginActivity.this,
                                            "Andela email",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this,
                                            UserDashBoardActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(LoginActivity.this,
                                            "Allowed non Andela email",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this,
                                            SecurityDashboardActivity.class);
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
