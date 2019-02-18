package com.andela.art.securitydashboard.presentation;

import android.annotation.TargetApi;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.andela.art.R;
import com.andela.art.api.UserAssetResponse;
import com.andela.art.checkin.CheckInActivity;
import com.andela.art.databinding.SecurityDashboardBinding;
import com.andela.art.login.LoginActivity;
import com.andela.art.models.Asset;
import com.andela.art.root.ApplicationComponent;
import com.andela.art.root.ApplicationModule;
import com.andela.art.root.ArtApplication;
import com.andela.art.root.BaseMenuActivity;
import com.andela.art.securitydashboard.injection.DaggerSerialEntryComponent;
import com.andela.art.securitydashboard.injection.FirebasePresenterModule;
import com.andela.art.securitydashboard.injection.SerialEntryModule;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

/**
 * Display Dialog box to enter serial and retrieve asset details.
 */

public class SecurityDashboardActivity extends BaseMenuActivity implements SerialView {

    @Inject
    SerialPresenter serialPresenter;

    SecurityDashboardBinding securityDashboardBinding;

    private View mProgressView;

    @Inject
    FirebasePresenter firebasePresenter;

    boolean backButtonToExitPressedTwice = false;

    @VisibleForTesting
    public Toast toast;

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

        mProgressView = findViewById(R.id.asset_details_progress_bar_serial);

        Snackbar snackbar = Snackbar.make(securityDashboardBinding.securityDashboardLayout,
                "This device doesn't support NFC.",
                Snackbar.LENGTH_INDEFINITE);
        View snackbarView = snackbar.getView();
        snackbarView.setPadding(10, 10, 10, 12);
        snackbarView.setBackgroundColor(ContextCompat.getColor(this,
                R.color.colorAccent));
        snackbar.show();

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
     * Shows the progress bar.
     * @param show Boolean to show progressbar.
     */
    @SuppressWarnings("AvoidInlineConditionals")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgressBar(final boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * Retrieve asset on confirm button is clicked.
     *
     * @param serial
     */
    @Override
    public void onConfirmClicked(String serial, String assetCode) {
        showProgressBar(true);
        if (serial.isEmpty()) {
            showNoRecordToast();
        } else {
            serialPresenter.getAsset(serial);
        }
    }

    /**
     * Show snackbar when user does not input serial.
     * //TODO: Use inbuilt checker instead
     */
    private void showNoRecordToast() {
        showProgressBar(false);
        toast = Toast.makeText(this.getApplicationContext(), "Please insert serial",
                Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * Start checkin Activity with asset data.
     *
     * @param asset - asset data sent to check in activity
     */
    public void sendIntent(UserAssetResponse asset) {
        if (asset.getAssets() == null) {
            toast = Toast.makeText(this,
                    "The asset serial number is not available.", Toast.LENGTH_LONG);
            toast.show();
        } else {
            Asset assetInfo = asset.getAssets().get(0);
            if (assetInfo.getCurrentStatus().equals("Available")) {
                showProgressBar(false);
                toast = Toast.makeText(this,
                        "The asset serial number is not assigned to any user.", Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundResource(android.R.drawable.toast_frame);
                TextView text = view.findViewById(android.R.id.message);
                text.setBackgroundColor(Color.parseColor("#DCDCDC"));
                toast.show();
            } else {
                Intent checkInIntent = new Intent(SecurityDashboardActivity.this,
                        CheckInActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("asset", assetInfo);
                checkInIntent.putExtras(bundle);
                startActivity(checkInIntent);
            }
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
        toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        showProgressBar(false);
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
