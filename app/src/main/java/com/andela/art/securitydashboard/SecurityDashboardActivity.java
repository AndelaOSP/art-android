package com.andela.art.securitydashboard;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.andela.art.R;
import com.andela.art.settings.SettingsActivity;

/**
 * This is the security dashboard that provides an interface for the security officers to perform
 * several tasks.
 */
public class SecurityDashboardActivity extends AppCompatActivity {

    Toolbar toolbar;
    boolean backButtonToExitPressedTwice = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.security_dashboard);

        toolbar = (Toolbar) findViewById(R.id.mToolBar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent settings = new Intent(SecurityDashboardActivity.this,
                SettingsActivity.class);
        startActivity(settings);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (backButtonToExitPressedTwice) {
            super.onBackPressed();
            finish();
            moveTaskToBack(true);
        }

        this.backButtonToExitPressedTwice = true;
        Toast.makeText(this, "Press BACK BUTTON again to exit.",
                Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                backButtonToExitPressedTwice = false;
            }
        }, 2000);
    }
}
