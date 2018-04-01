package com.andela.art.settings;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.andela.art.R;
import com.andela.art.login.LoginActivity;
import com.andela.art.root.App;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

/**
 * This is activity manages a users settings.
 */
public class SettingsActivity extends AppCompatActivity {
    @Inject GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_page);
        ((App) getApplicationContext()).getComponent().inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.mToolBar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.settings);
        actionBar.setDisplayHomeAsUpEnabled(true);

        final Dialog logoutDialog = new Dialog(this);
        logoutDialog.setContentView(R.layout.logout_dialog);

        TextView tvLogoutCancel = logoutDialog.findViewById(R.id.tvLogoutDialogCancel);
        tvLogoutCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutDialog.dismiss();
            }
        });

        TextView tvLogoutYes = logoutDialog.findViewById(R.id.tvLogoutDialogYes);
        tvLogoutYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                googleSignInClient.signOut();
                Intent loginActivity = new Intent(SettingsActivity.this, LoginActivity.class);
                startActivity(loginActivity);
            }
        });

        TextView tvLogout = findViewById(R.id.tvLogOut);
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutDialog.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return true;
    }
}
