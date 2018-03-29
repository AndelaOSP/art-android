package com.andela.art.securitydashboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.andela.art.R;
import com.andela.art.settings.SettingsActivity;

/**
 * This is the security dashboard that provides an interface for the security officers to perform
 * several tasks.
 */
public class SecurityDashboardActivity extends AppCompatActivity {

    Toolbar toolbar;

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
}
