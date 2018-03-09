package com.andela.art.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.andela.art.R;
import com.andela.art.checkin.CheckInActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonCheckIn)
    public void goToCheckInActivity() {
        Intent intent = new Intent(MainActivity.this, CheckInActivity.class);
        intent.putExtra("name", "Mudge Fudge");
        intent.putExtra("serialNumber", "CDRT45TG34");
        intent.putExtra("email", "mudge.fudge@mail.com");
        startActivity(intent);
    }
}
