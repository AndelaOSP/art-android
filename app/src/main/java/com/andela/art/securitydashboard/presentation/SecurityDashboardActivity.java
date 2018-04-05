package com.andela.art.securitydashboard.presentation;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.andela.art.R;
import com.andela.art.checkin.CheckInActivity;
import com.andela.art.common.ApplicationComponent;
import com.andela.art.common.ArtApplication;
import com.andela.art.databinding.SecurityDashboardBinding;
import com.andela.art.login.LoginActivity;
import com.andela.art.securitydashboard.data.Asset;
import com.andela.art.securitydashboard.injection.DaggerSerialEntryComponent;
import com.andela.art.securitydashboard.injection.SerialEntryModule;
import com.andela.art.securitydashboard.injection.FirebasePresenterModule;
import com.andela.art.settings.SettingsActivity;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

/**
 * Display Dialog box to enter serial and retrieve asset details.
 */

public class SecurityDashboardActivity extends AppCompatActivity implements SerialView {

    @Inject
    SerialPresenter serialPresenter;

    SecurityDashboardBinding securityDashboardBinding;

    @Inject
    FirebasePresenter firebasePresenter;

    /**
     * Activity on create method.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        securityDashboardBinding = DataBindingUtil.setContentView(this,
                R.layout.security_dashboard);

        securityDashboardBinding.addSerial.setOnClickListener(view -> openDialog());
        setSupportActionBar((Toolbar) securityDashboardBinding.mToolBar);
        ApplicationComponent applicationComponent = ((ArtApplication) getApplication())
                .applicationComponent();

        DaggerSerialEntryComponent.builder()
                .applicationComponent(applicationComponent)
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
    protected void onStart() {
        super.onStart();
        firebasePresenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebasePresenter.stop();
    }

    /**
     * Create a menu.
     * @param menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent settings = new Intent(SecurityDashboardActivity.this,
                SettingsActivity.class);
        startActivity(settings);
        return true;
    }
}
