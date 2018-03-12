package com.andela.art.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.andela.art.R;
import com.andela.art.application.ArtApplication;
import com.andela.art.checkin.CheckInActivity;
import com.andela.art.injection.component.ApplicationComponent;
import com.andela.art.injection.component.DaggerMainComponent;
import com.andela.art.injection.modules.MainModule;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView {
    @Inject MainPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initializeMainComponent();
    }

    /**
     * Open checkin activity.
     */
    @OnClick(R.id.buttonCheckIn)
    public void goToCheckInActivity() {
        Intent intent = new Intent(MainActivity.this, CheckInActivity.class);
        intent.putExtra("name", "Mudge Fudge");
        intent.putExtra("serialNumber", "CDRT45TG34");
        intent.putExtra("email", "mudge.fudge@mail.com");
        startActivity(intent);
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((ArtApplication) getApplication()).getApplicationComponent();
    }

    /**
     * Initialize checkin Component
     */
    public void initializeMainComponent() {
        DaggerMainComponent.builder()
                .applicationComponent(getApplicationComponent())
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }
}
