package com.andela.art.serialentry.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;

import com.andela.art.R;
import com.andela.art.checkin.CheckInActivity;
import com.andela.art.common.ApplicationComponent;
import com.andela.art.common.ArtApplication;
import com.andela.art.serialentry.data.Asset;
import com.andela.art.serialentry.injection.DaggerSerialEntryComponent;
import com.andela.art.serialentry.injection.SerialEntryModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Display Dialog box to enter serial and retrieve asset details.
 */

public class SerialEntryActivity extends AppCompatActivity implements SerialView {

    @Inject
    SerialPresenter serialPresenter;

    @BindView(R.id.addSerial)
    Button addSerialButton;

    /**
     * Activity on create method.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serial_entry_layout);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Resource Tracker");
        ApplicationComponent applicationComponent = ((ArtApplication) getApplication())
                .applicationComponent();

        DaggerSerialEntryComponent.builder()
                .applicationComponent(applicationComponent)
                .serialEntryModule(new SerialEntryModule())
                .build()
                .inject(this);
        serialPresenter.attachView(this);
    }

    /**
     * Create dialog for serial when add button is clicked.
     */
    @OnClick(R.id.addSerial)
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
     * @param asset
     */
    public void sendIntent(Asset asset) {
        Intent checkInIntent = new Intent(SerialEntryActivity.this, CheckInActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("asset", asset);
        checkInIntent.putExtras(bundle);
        startActivity(checkInIntent);

    }

    /**
     *
     * @param menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.serial_menu, menu);
        return true;
    }
}
