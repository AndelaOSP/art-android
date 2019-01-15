package com.andela.art.securitydashboard.presentation;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
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

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javax.inject.Inject;

/**
 * Display Dialog box to show asset details from nfc and retrieve further details of asset.
 */

public class NfcSecurityDashboardActivity extends AppCompatActivity implements NfcView {

    private NfcAdapter mNfcAdapter;

    public static final String MIME_TEXT_PLAIN = "text/plain";

    String nfcSerial;

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

        setSupportActionBar((Toolbar) nfcSecurityDashboardBinding.mToolBar);
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

        if (mNfcAdapter == null && getIntent().getStringExtra("developer_override") == null) {
            Intent intent = new Intent(NfcSecurityDashboardActivity.this,
                    SecurityDashboardActivity.class);
            startActivity(intent);
        }

        if (mNfcAdapter != null && !mNfcAdapter.isEnabled()) {
            Toast.makeText(this, "NFC is disabled.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "NFC is enabled.", Toast.LENGTH_LONG).show();
        }
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
     * handle new intent. Read ndef data on nfc tag.
     * @param intent the intent.
     */
    public void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) {

                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                new NdefReaderTask().execute(tag);

            } else {
                Log.d("check", "Wrong mime type: " + type);
            }
        } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] techList = tag.getTechList();
            String searchedTech = Ndef.class.getName();

            for (String tech : techList) {
                if (searchedTech.equals(tech)) {
                    new NdefReaderTask().execute(tag);
                    break;
                }
            }
        }

        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        new NdefReaderTask().execute(tag);

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
        nfcPresenter.getAsset(serial);
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

        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};

        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType(MIME_TEXT_PLAIN);
        } catch (IntentFilter.MalformedMimeTypeException e) {
            Log.e("check", "Check your mime type", e);
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

    /**
     * Tag reader async task.
     */
    private class NdefReaderTask extends AsyncTask<Tag, Void, String> {

        @Override
        protected String doInBackground(Tag... params) {
            Tag tag = params[0];

            Ndef ndef = Ndef.get(tag);
            if (ndef == null) {
                return null;
            }

            NdefMessage ndefMessage = ndef.getCachedNdefMessage();

            NdefRecord[] records = ndefMessage.getRecords();
            for (NdefRecord ndefRecord : records) {
                if (ndefRecord.getTnf() == NdefRecord
                        .TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(),
                        NdefRecord.RTD_TEXT)) {
                    try {
                        return readText(ndefRecord);
                    } catch (UnsupportedEncodingException e) {
                        Log.e("check", "Unsupported Encoding", e);
                    }
                }
            }

            return null;
        }

        /**
         * Read ndef data.
         * @param record read a record in tag.
         * @return string
         * @throws UnsupportedEncodingException exception.
         */
        private String readText(NdefRecord record) throws UnsupportedEncodingException {

            byte[] payload = record.getPayload();

            String textEncoding;
            if ((payload[0] & 128) == 0) {
                textEncoding = "UTF-8";
            } else {
                textEncoding = "UTF-16";
            }

            int languageCodeLength = payload[0] & 0063; //NOPMD

            return new String(payload,
                    languageCodeLength + 1,
                    payload.length - languageCodeLength - 1,
                    textEncoding);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                nfcSecurityDashboardBinding.scanNfcTitleTextView
                        .setText(String.format("Serial: %s", result));
                nfcSerial = result;

            }
        }
    }

}


