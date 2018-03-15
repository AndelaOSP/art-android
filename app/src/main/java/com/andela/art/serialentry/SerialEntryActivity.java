package com.andela.art.serialentry;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.andela.art.R;
import com.andela.art.login.LoginActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class SerialEntryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    TextView name, email;
    CircleImageView displayPicture;
    private DrawerLayout drawer;

    private static final String TAG = "NavigationDrawer";

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial_entry);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Intent redirect = new Intent(SerialEntryActivity.this, LoginActivity.class);
                    SerialEntryActivity.this.startActivity(redirect);
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        name = navigationView.getHeaderView(0).findViewById(R.id.display_name);
        email = navigationView.getHeaderView(0).findViewById(R.id.email_address);
        displayPicture = navigationView.getHeaderView(0).findViewById(R.id.display_picture);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(SerialEntryActivity.this);
        String personName = account.getDisplayName();
        String personEmail = account.getEmail();
        String personPhoto = account.getPhotoUrl().toString();

        name.setText(personName);
        email.setText(personEmail);
        Picasso.with(SerialEntryActivity.this).load(personPhoto).into(displayPicture);
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.mn_serial_entry:
                // Handle the serial entry action
                break;
            case R.id.nav_send_report:
                // Handle the report asset action
                break;
            case R.id.nav_logout:
                mAuth.signOut();
                break;

        }

        drawer.closeDrawers();
        return true;
    }
}
