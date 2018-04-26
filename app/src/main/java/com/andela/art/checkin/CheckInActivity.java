package com.andela.art.checkin;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.andela.art.R;
import com.andela.art.checkin.injection.CheckInModule;
import com.andela.art.checkin.injection.DaggerCheckInComponent;
import com.andela.art.databinding.ActivityCheckInBinding;
import com.andela.art.models.Asset;
import com.andela.art.models.Asignee;
import com.andela.art.root.ApplicationComponent;
import com.andela.art.root.ApplicationModule;
import com.andela.art.root.ArtApplication;
import com.andela.art.root.BaseMenuActivity;
import com.andela.art.securitydashboard.presentation.SecurityDashboardActivity;
import com.squareup.picasso.Picasso;
import java.util.Locale;
import javax.inject.Inject;

/**
 * Created by zack on 3/26/18.
 */

public class CheckInActivity extends BaseMenuActivity implements CheckInView {
    ActivityCheckInBinding binding;
    @Inject
    CheckInPresenter presenter;
    ApplicationComponent applicationComponent;
    Asset asset;
    Asignee user;
    Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_check_in);
        applicationComponent = ((ArtApplication) getApplication())
                .applicationComponent();
        initializeCheckInComponent();

        bundle = getIntent().getExtras();
        asset = (Asset) bundle.getSerializable("asset");
        user = asset.getAssignee();
        binding.checkInButton.setOnClickListener(v ->
                callCheckin(asset.getSerialNumber(), asset.getCheckinStatus()));

        setSupportActionBar(binding.checkInToolbar);
        binding.checkInToolbar.setTitleTextAppearance(this, R.style.CheckInTitle);
        presenter.attachView(this);
        displayDetails();
    }

    /**
     * Add check in details to views.
     */
    @Override
    public void displayDetails() {
        binding.name.setText(user.getFullName().toUpperCase(Locale.US));
        binding.serialInfo.setText(asset.getSerialNumber());
        binding.emailText.setText(user.getEmail());
        binding.cohortNumber.setText(String.valueOf(user.getCohort()));
        loadResizedImage(user.getPicture());
        showCheckout(asset);
    }

    /**
     * Display checkout button.
     */
    @Override
    public void showCheckout(Asset asset) {

        if (asset.getCheckinStatus() == null || "checked_in".equals(asset.getCheckinStatus())) {
            binding.checkInButton.setBackground(getResources()
                    .getDrawable(R.drawable.checkout_button));
            binding.checkInButton.setText(getResources().getString(R.string.check_out));
        } else if ("checked_out".equals(asset.getCheckinStatus())) {
            binding.checkInButton.setBackground(getResources()
                    .getDrawable(R.drawable.checkin_button));
            binding.checkInButton.setText(getResources().getString(R.string.checkin));
        }

    }

    /**
     * Resize and load image to image view.
     * @param url - image url
     */
    @Override
    public void loadResizedImage(String url) {

        if (url == null) {

            Picasso.with(this)
                    .load(R.drawable.photo)
                    .fit()
                    .centerCrop()
                    .into(binding.ivPhoto);
        } else {
            Picasso.with(this)
                    .load(url)
                    .fit()
                    .centerCrop()
                    .into(binding.ivPhoto);
        }
    }

    /**
     * Go to security dashboard activity.
     */
    @Override
    public void goToCheckSerial() {
        Intent intent = new Intent(CheckInActivity.this,
                SecurityDashboardActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Initialize check in component.
     */
    public void initializeCheckInComponent() {
        DaggerCheckInComponent.builder()
                .applicationComponent(applicationComponent)
                .applicationModule(new ApplicationModule(getApplication()))
                .checkInModule(new CheckInModule())
                .build()
                .inject(this);
    }

    /**
     * Call the check in method in presenter.
     *
     * @param serial - asset serial number
     * @param logType - check in status
     */
    public void callCheckin(String serial, String logType) {
        String status;
        if ("checked_in".equals(logType)) {
            status = "Checkout";
        } else {
            status = "Checkin";
        }
        presenter.checkIn(serial, status);
    }
}
