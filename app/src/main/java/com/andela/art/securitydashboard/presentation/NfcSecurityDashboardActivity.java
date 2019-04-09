package com.andela.art.securitydashboard.presentation;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.andela.art.R;
import com.andela.art.api.UserAssetResponse;
import com.andela.art.checkin.CheckInActivity;
import com.andela.art.databinding.NfcSecurityDashboardBinding;
import com.andela.art.login.LoginActivity;
import com.andela.art.models.Asset;
import com.andela.art.root.ApplicationComponent;
import com.andela.art.root.ApplicationModule;
import com.andela.art.root.ArtApplication;
import com.andela.art.root.BaseMenuActivity;
import com.andela.art.securitydashboard.injection.DaggerSerialEntryComponent;
import com.andela.art.securitydashboard.injection.FirebasePresenterModule;
import com.andela.art.securitydashboard.injection.SerialEntryModule;
import com.andela.art.securitydashboard.presentation.threading.NdefReaderTask;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

/**
 * Display Dialog box to show asset details from nfc and retrieve further details of asset.
 */

public class NfcSecurityDashboardActivity extends BaseMenuActivity implements NfcView {

    private NfcAdapter mNfcAdapter;

    public static final String MIME_TEXT_PLAIN = "text/plain";

    public static final String TAG = "check";

    String nfcSerial;

    Boolean isActivityActive = false;

    private View mProgressView;

    @Inject
    NfcPresenter nfcPresenter;

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

        setSupportActionBar((Toolbar) nfcSecurityDashboardBinding.nfcToolBar);
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

        mProgressView = findViewById(R.id.asset_details_progress_bar);

        if (mNfcAdapter == null && getIntent().getStringExtra("developer_override") == null) {
            Intent intent = new Intent(NfcSecurityDashboardActivity.this,
                    SecurityDashboardActivity.class);
            startActivity(intent);
        }

        if (mNfcAdapter != null) {
            if (!mNfcAdapter.isEnabled()) {
                Toast.makeText(this.getApplicationContext(), "NFC is disabled.",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this.getApplicationContext(), "NFC is enabled.",
                        Toast.LENGTH_LONG).show();
            }
        }
        isActivityActive = true;
        nfcPresenter.attachVieww(this);
        firebasePresenter.attachVieww(this);
        firebasePresenter.onAuthStateChanged();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
        getNfcData();
    }

    /**
     * show toast if tag has no data.
     */
    public void showTagHasNoData() {
        toast = Toast.makeText(this.getApplicationContext(), "No records found on Scanned Tag",
                Toast.LENGTH_SHORT);
        toast.show();
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
     * handle new intent. Read ndef data on nfc tag.
     * @param intent the intent.
     */
    public void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) {

                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                new NdefReaderTask(this).execute(tag);

            } else {
                Log.d(TAG, "Wrong mime type: " + type);
            }
        } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] techList = tag.getTechList();
            String searchedTech = Ndef.class.getName();

            for (String tech : techList) {
                if (searchedTech.equals(tech)) {
                    new NdefReaderTask(this).execute(tag);
                    break;
                }
            }
        }

        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        new NdefReaderTask(this).execute(tag);
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

    /**
     *
     * @param serial serial entered by ehrn user confirms nfcTag scan.
     */
    public void onConfirmClicked(String serial) {
        showProgressBar(true);
        if (serial.isEmpty()) {
            showProgressBar(false);
            nfcSecurityDashboardBinding.scanNfcTitleTextView
                    .setVisibility(View.VISIBLE);
            showTagHasNoData();
        } else {
            nfcPresenter.getAsset(serial);
        }
    }

    /**
     * Start checkin Activity with asset data.
     *
     * @param asset - asset data sent to check in activity
     */
    public void sendIntent(UserAssetResponse asset) {
        if (asset.getAssets() == null) {
            toast = Toast.makeText(this.getApplicationContext(),
                    "This asset is not in the system.", Toast.LENGTH_LONG);
            toast.show();
        } else {
            Asset assetInfo = asset.getAssets().get(0);
            if (assetInfo.getCurrentStatus().equals("Available")) {
                showProgressBar(false);
                nfcSecurityDashboardBinding.scanNfcTitleTextView
                        .setVisibility(View.VISIBLE);
                toast = Toast.makeText(this.getApplicationContext(),
                        "Asset with this serial number is not assigned to anyone.",
                        Toast.LENGTH_LONG);
                toast.show();
            } else {
                Intent checkInIntent = new Intent(NfcSecurityDashboardActivity.this,
                        CheckInActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("asset", assetInfo);
                checkInIntent.putExtras(bundle);
                startActivity(checkInIntent);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mNfcAdapter == null && getIntent().getStringExtra("developer_override") == null) {
            Intent intent = new Intent(NfcSecurityDashboardActivity.this,
                    SecurityDashboardActivity.class);
            startActivity(intent);
        }
        setupForegroundDispatch(this, mNfcAdapter);
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
        showProgressBar(false);
        String message = "The asset is not available.";
        nfcSecurityDashboardBinding.scanNfcTitleTextView
                .setVisibility(View.VISIBLE);
        toast = Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void updateSerial(String result) {
        nfcSecurityDashboardBinding.scanNfcTitleTextView
                .setVisibility(View.GONE);
        nfcSerial = result;
    }

    @Override
    protected void onStart() {
        super.onStart();
        showProgressBar(false);
        nfcSecurityDashboardBinding.scanNfcTitleTextView
                .setVisibility(View.VISIBLE);
        firebasePresenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebasePresenter.stop();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopForegroundDispatch(this, mNfcAdapter);
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

    /**
     * Set up foreground dispatch.
     * @param activity current activity.
     * @param adapter the nfc adapter.
     */
    public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent
                .getActivity(activity.getApplicationContext(),
                0, intent, 0);

        IntentFilter[] filters = new IntentFilter[2];
        String[][] techList = new String[][]{new String[] {"android.nfc.tech.Ndef"}};

        filters[0] = new IntentFilter();
        filters[1] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[1].addAction(NfcAdapter.ACTION_TECH_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        filters[1].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType(MIME_TEXT_PLAIN);
        } catch (IntentFilter.MalformedMimeTypeException e) {
            Log.e(TAG, "Check your mime type", e);
        }
        if (adapter != null) {
            adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
        }
    }

    /**
     * stop foreground dispatch.
     *
     * @param activity current activity.
     * @param adapter the nfc adapter.
     */
    public static void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        if (adapter != null) {
            adapter.disableForegroundDispatch(activity);
        }
    }

}


