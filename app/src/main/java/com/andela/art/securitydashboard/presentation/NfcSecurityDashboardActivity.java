package com.andela.art.securitydashboard.presentation;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.andela.art.R;
import com.andela.art.checkin.CheckInActivity;
import com.andela.art.databinding.NfcSecurityDashboardBinding;
import com.andela.art.login.LoginActivity;
import com.andela.art.models.Asset;
import com.andela.art.root.ApplicationComponent;
import com.andela.art.root.ApplicationModule;
import com.andela.art.root.ArtApplication;
import com.andela.art.securitydashboard.injection.DaggerSerialEntryComponent;
import com.andela.art.securitydashboard.injection.FirebasePresenterModule;
import com.andela.art.securitydashboard.injection.SerialEntryModule;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

/**
 * Display Dialog box to show asset details from nfc and retrieve further details of asset.
 */

public class NfcSecurityDashboardActivity extends AppCompatActivity implements SerialView {

    private NfcAdapter mNfcAdapter;

    @Inject
    SerialPresenter serialPresenter;

    NfcSecurityDashboardBinding nfcSecurityDashboardBinding;

    @Inject
    FirebasePresenter firebasePresenter;

    boolean backButtonToExitPressedTwice = false;

    @VisibleForTesting
    public Toast toast;

    /**
     * Activity on create method.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nfcSecurityDashboardBinding = DataBindingUtil.setContentView(this,
                R.layout.nfc_security_dashboard);
        setSupportActionBar((Toolbar) nfcSecurityDashboardBinding.mToolBar);
        nfcSecurityDashboardBinding.scanNFcButton.setOnClickListener(view -> getNfcData());
        ApplicationComponent applicationComponent = ((ArtApplication) getApplication())
                .applicationComponent();
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        DaggerSerialEntryComponent.builder()
                .applicationComponent(applicationComponent)
                .applicationModule(new ApplicationModule(getApplication()))
                .serialEntryModule(new SerialEntryModule())
                .firebasePresenterModule(new FirebasePresenterModule())
                .build()
                .inject(this);
        Log.d("check", "onCreate: adapter " + mNfcAdapter);

        if (mNfcAdapter == null) {
            Intent intent = new Intent(NfcSecurityDashboardActivity.this,
                    SecurityDashboardActivity.class);
            startActivity(intent);
        }

        if (mNfcAdapter != null && !mNfcAdapter.isEnabled()) {
            Toast.makeText(this, "NFC is disabled.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "NFC is enabled.", Toast.LENGTH_LONG).show();
        }

        firebasePresenter.attachView(this);
        firebasePresenter.onAuthStateChanged();
    }

    /**
     * Get data from nfc tag.
     */
    public void getNfcData() {

        openDialog();
    }

    /**
     * Create dialog for serial when add button is clicked.
     */
    public void openDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        NfcDialog nfcDialog = NfcDialog.newInstance();
        nfcDialog.show(fragmentManager, "Nfc Dialog");
    }

    @Override
    public void onConfirmClicked(String serial) {
        //TODO: Set up asset acquisition from api using serial from nfc tag.
        toast = Toast.makeText(this, "Retrieve data", Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * Start checkin Activity with asset data.
     *
     * @param asset - asset data sent to check in activity
     */
    public void sendIntent(Asset asset) {
        if (asset.getAssignee() == null) {
            toast = Toast.makeText(this, "Asset not assigned.", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Intent checkInIntent = new Intent(NfcSecurityDashboardActivity.this,
                    CheckInActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("asset", asset);
            checkInIntent.putExtras(bundle);
            startActivity(checkInIntent);
        }

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
     *
     * @param email email
     * @param name  name
     * @param photo photo
     */
    @Override
    public void setAccountDetails(String email, String name, String photo) {
        Uri photoUri = Uri.parse(photo);
        nfcSecurityDashboardBinding.emailAddress.setText(email);
        nfcSecurityDashboardBinding.displayName.setText(name);
        Picasso.with(getApplicationContext())
                .load(photoUri)
                .fit()
                .centerCrop()
                .into(nfcSecurityDashboardBinding.profilePhoto);
    }

    @Override
    public void displayErrorMessage(Throwable error) {
        String message = error.getMessage().toString();
        toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
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
            toast = Toast.makeText(this.getApplicationContext(), "Press again to exit.",
                    Toast.LENGTH_SHORT);
            toast.show();
        }

        this.backButtonToExitPressedTwice = true;

        new Handler().postDelayed(() -> backButtonToExitPressedTwice = false, 2000);
    }
}
