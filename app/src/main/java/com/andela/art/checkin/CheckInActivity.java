package com.andela.art.checkin;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;

import com.andela.art.R;
import com.andela.art.checkin.injection.CheckInModule;
import com.andela.art.checkin.injection.DaggerCheckInComponent;
import com.andela.art.databinding.ActivityCheckInBinding;
import com.andela.art.models.Asset;
import com.andela.art.models.Asignee;
import com.andela.art.root.ApplicationComponent;
import com.andela.art.root.ApplicationModule;
import com.andela.art.root.ArtApplication;
import com.andela.art.securitydashboard.presentation.SecurityDashboardActivity;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import javax.inject.Inject;

/**
 * Created by zack on 3/26/18.
 */

public class CheckInActivity extends AppCompatActivity implements CheckInView {
    ActivityCheckInBinding binding;
    @Inject
    CheckInPresenter presenter;
    ApplicationComponent applicationComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_check_in);
        applicationComponent = ((ArtApplication) getApplication())
                .applicationComponent();
        initializeCheckInComponent();
        binding.checkInButton.setOnClickListener(v -> presenter
                .checkIn(getIntent().getStringExtra("serial")));
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
        Bundle bundle = getIntent().getExtras();
        Asset asset = (Asset) bundle.getSerializable("asset");
        Asignee user = asset.getAssignee();
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
        if (asset.getCheckinStatus().equals("Checkin")) {
            binding.checkInButton.setBackground(getDrawable(R.drawable.checkout_button));
            binding.checkInButton.setText(getResources().getString(R.string.check_out));
        } else {
            binding.checkInButton.setBackground(getDrawable(R.drawable.checkin_button));
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
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
}
