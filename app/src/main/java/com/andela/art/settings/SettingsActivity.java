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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

/**
 * This is activity manages a users settings.
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_page);

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
                GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                        .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .requestProfile()
                        .build();
                GoogleSignIn.getClient(SettingsActivity.this, googleSignInOptions).signOut();
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
