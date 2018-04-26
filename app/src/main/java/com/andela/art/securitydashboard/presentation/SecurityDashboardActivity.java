package com.andela.art.securitydashboard.presentation;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import com.andela.art.R;
import com.andela.art.checkin.CheckInActivity;
import com.andela.art.models.Asset;
import com.andela.art.root.ApplicationComponent;
import com.andela.art.root.ApplicationModule;
import com.andela.art.root.ArtApplication;
import com.andela.art.databinding.SecurityDashboardBinding;
import com.andela.art.login.LoginActivity;
import com.andela.art.root.BaseMenuActivity;
import com.andela.art.securitydashboard.injection.DaggerSerialEntryComponent;
import com.andela.art.securitydashboard.injection.SerialEntryModule;
import com.andela.art.securitydashboard.injection.FirebasePresenterModule;
import com.squareup.picasso.Picasso;
import javax.inject.Inject;

/**
 * Display Dialog box to enter serial and retrieve asset details.
 */

public class SecurityDashboardActivity extends BaseMenuActivity implements SerialView {

    @Inject
    SerialPresenter serialPresenter;

    SecurityDashboardBinding securityDashboardBinding;

    @Inject
    FirebasePresenter firebasePresenter;

    boolean backButtonToExitPressedTwice = false;

    /**
     * Activity on create method.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        securityDashboardBinding = DataBindingUtil.setContentView(this,
                R.layout.security_dashboard);
        setSupportActionBar((Toolbar) securityDashboardBinding.mToolBar);
        securityDashboardBinding.addSerial.setOnClickListener(view -> openDialog());
        ApplicationComponent applicationComponent = ((ArtApplication) getApplication())
                .applicationComponent();

        DaggerSerialEntryComponent.builder()
                .applicationComponent(applicationComponent)
                .applicationModule(new ApplicationModule(getApplication()))
                .serialEntryModule(new SerialEntryModule())
                .firebasePresenterModule(new FirebasePresenterModule())
                .build()
                .inject(this);
        serialPresenter.attachView(this);
        firebasePresenter.attachView(this);
        firebasePresenter.onAuthStateChanged();
    }

    /**
     * Create dialog for serial when add button is clicked.
     */
    public void openDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SerialDialog serialDialog = SerialDialog.newInstance();
        serialDialog.show(fragmentManager, "Serial Dialog");
    }

    /**
     * Retrieve asset on confirm button is clicked.
     *
     * @param serial
     */
    @Override
    public void onConfirmClicked(String serial) {
        serialPresenter.getAsset(serial);
    }

    /**
     * Start checkin Activity with asset data.
     *
     * @param asset - asset data sent to check in activity
     */
    public void sendIntent(Asset asset) {
        Intent checkInIntent = new Intent(SecurityDashboardActivity.this,
                CheckInActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("asset", asset);
        checkInIntent.putExtras(bundle);
        startActivity(checkInIntent);

    }

    /**
     * Redirect user if they are not logged in.
     */
    @Override
    public void redirectLoggedOutUser() {
        Intent redirect = new Intent(this, LoginActivity.class);
        startActivity(redirect);
    }

    /**
     * Set account details once a user logs in.
     * @param email email
     * @param name name
     * @param photo photo
     */
    @Override
    public void setAccountDetails(String email, String name, String photo) {
        Uri photoUri = Uri.parse(photo);
        securityDashboardBinding.emailAddress.setText(email);
        securityDashboardBinding.displayName.setText(name);
        Picasso.with(getApplicationContext())
                .load(photoUri)
                .fit()
                .centerCrop()
                .into(securityDashboardBinding.profilePhoto);
    }

    @Override
    public void displayErrorMessage(Throwable error) {
        String message = error.getMessage().toString();
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebasePresenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebasePresenter.stop();
    }

    @Override
    public void onBackPressed() {
        if (backButtonToExitPressedTwice) {
            super.onBackPressed();
            finish();
            moveTaskToBack(true);
        } else {
            Toast.makeText(this.getApplicationContext(), "Press again to exit.",
                    Toast.LENGTH_SHORT).show();
        }

        this.backButtonToExitPressedTwice = true;

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                backButtonToExitPressedTwice = false;
            }

        }, 2000);
    }
}
