package com.andela.art.checkin;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.andela.art.R;
import com.andela.art.api.CheckInService;
import com.andela.art.databinding.ActivityCheckInBinding;
import com.andela.art.root.App;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import javax.inject.Inject;

/**
 * Check in activity.
 */
public class CheckInActivity extends AppCompatActivity implements CheckInView {
    ActivityCheckInBinding binding;
    CheckInPresenterImpl presenter;
    @Inject
    CheckInService checkInService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_check_in);
        ((App) getApplication()).getComponent().inject(this);
        presenter = new CheckInPresenterImpl(this, checkInService);
        displayDetails();
        binding.checkinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.checkIn(getIntent().getStringExtra("serial"),
                        getIntent().getStringExtra("name"));
            }
        });
        setSupportActionBar(binding.checkInToolbar);
        binding.checkInToolbar.setTitleTextAppearance(this, R.style.CheckInToolbarText);

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
                .load(getIntent().getStringExtra("image"))
                .fit()
                .centerCrop()
                .into(binding.ivPhoto);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.checkin_menu, menu);
        return true;
    }
}
