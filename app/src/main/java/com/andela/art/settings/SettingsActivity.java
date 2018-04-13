package com.andela.art.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.andela.art.R;
import com.andela.art.root.ApplicationComponent;
import com.andela.art.root.ApplicationModule;
import com.andela.art.root.ArtApplication;
import com.andela.art.databinding.SettingsPageBinding;
import com.andela.art.login.LoginActivity;
import com.andela.art.login.injection.DaggerLoginComponent;
import com.andela.art.login.injection.LoginModule;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

/**
 * This is activity manages a users settings.
 */
public class SettingsActivity extends AppCompatActivity implements Dialog.OnClickListener {
    @Inject GoogleSignInClient googleSignInClient;
    Dialog logoutDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SettingsPageBinding binding = DataBindingUtil.setContentView(this, R.layout.settings_page);
        ApplicationComponent applicationComponent = ((ArtApplication) getApplication())
                .applicationComponent();
        DaggerLoginComponent.builder()
                .applicationComponent(applicationComponent)
                .applicationModule(new ApplicationModule(getApplication()))
                .loginModule(new LoginModule())
                .build()
                .inject(this);
        setSupportActionBar((Toolbar) binding.tbToolBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.settings);
        actionBar.setDisplayHomeAsUpEnabled(true);

        AlertDialog.Builder logoutDialogBuilder = new AlertDialog.Builder(this, R.style.AppDialog);
        logoutDialogBuilder.setTitle(getString(R.string.logout))
                .setMessage(getString(R.string.logout_message))
                .setNegativeButton(R.string.logout_cancel, this)
                .setPositiveButton(R.string.logout_yes, this)
                .setCancelable(false);
        logoutDialog = logoutDialogBuilder.create();

        binding.tvLogOut.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void onClick(DialogInterface dialogInterface, int whichOption) {
        if (whichOption == AlertDialog.BUTTON_POSITIVE) {
            FirebaseAuth.getInstance().signOut();
            googleSignInClient.signOut();
            Intent loginActivity = new Intent(SettingsActivity.this, LoginActivity.class);
            loginActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginActivity);
            finish();
        } else {
            logoutDialog.dismiss();
        }
    }
}
