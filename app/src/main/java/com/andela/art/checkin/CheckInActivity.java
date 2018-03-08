package com.andela.art.checkin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.andela.art.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CheckInActivity extends AppCompatActivity implements CheckInView {
    CheckInPresenter presenter = new CheckInPresenter(this, this);
    @BindView(R.id.ivPhoto)
    ImageView headerImage;
    @BindView(R.id.serial_info)
    TextView serialNumber;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.email_text)
    TextView email;
    @BindView(R.id.checkinButton)
    Button checkIn;
    @BindView(R.id.checkoutButton)
    Button checkOut;

    public CheckInActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);
        ButterKnife.bind(this);
        displayDetails();
        loadResizedImage();
    }

    /**
     * Add onclick functionality to check in button.
     */
    @OnClick(R.id.checkinButton)
    public void callCheckIn() {
        presenter.checkIn(getIntent().getStringExtra("serialNumber"));
    }

    /**
     * Add check in details to views.
     */
    @Override
    public void displayDetails() {
        name.setText(getIntent().getStringExtra("name"));
        serialNumber.setText(getIntent().getStringExtra("serialNumber"));
        email.setText(getIntent().getStringExtra("email"));
    }

    /**
     * Display checkout button.
     */
    @Override
    public void showCheckout() {
        checkIn.setVisibility(View.INVISIBLE);
        showCheckIn();
    }

    private void showCheckIn() {
        checkOut.setVisibility(View.VISIBLE);
    }

    /**
     * Resize and load image to image view
     */
    @Override
    public void loadResizedImage() {
        Picasso.with(this)
                .load(R.drawable.header)
                .resize(500, 360)
                .into(headerImage);
    }

}
