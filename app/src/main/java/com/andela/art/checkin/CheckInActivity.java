package com.andela.art.checkin;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.andela.art.R;
import com.andela.art.checkin.injection.CheckInModule;
import com.andela.art.checkin.injection.DaggerCheckInComponent;
import com.andela.art.databinding.ActivityCheckInBinding;
import com.andela.art.root.ApplicationComponent;
import com.andela.art.root.ArtApplication;
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
        displayDetails();
        binding.checkinButton.setOnClickListener(v -> presenter
                .checkIn(getIntent().getStringExtra("serial"),
                getIntent().getStringExtra("name")));
        setSupportActionBar(binding.checkInToolbar);
        binding.checkInToolbar.setTitleTextAppearance(this, R.style.CheckInTitle);
        presenter.attachView(this);
    }

    /**
     * Add check in details to views.
     */
    @Override
    public void displayDetails() {
        binding.name.setText(getIntent().getStringExtra("name").toUpperCase(Locale.US));
        binding.serialInfo.setText(getIntent().getStringExtra("serial"));
        binding.emailText.setText(getIntent().getStringExtra("email"));
        binding.cohortNumber.setText(getIntent().getStringExtra("cohort"));
        loadResizedImage();
    }

    /**
     * Display checkout button.
     */
    @Override
    public void showCheckout() {
        binding.checkinButton.setVisibility(View.INVISIBLE);
        binding.checkoutButton.setVisibility(View.VISIBLE);
    }

    /**
     * Resize and load image to image view.
     */
    @Override
    public void loadResizedImage() {
        Picasso.with(this)
                .load(R.drawable.photo)
                .fit()
                .centerCrop()
                .into(binding.ivPhoto);
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
                .checkInModule(new CheckInModule())
                .build()
                .inject(this);
    }
}
