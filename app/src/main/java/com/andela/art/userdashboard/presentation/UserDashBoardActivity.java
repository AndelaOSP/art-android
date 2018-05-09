package com.andela.art.userdashboard.presentation;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.widget.Toast;

import com.andela.art.R;
import com.andela.art.databinding.FragmentActivityBinding;
import com.andela.art.root.BaseMenuActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * Hosts UserDashboardFragment.
 */
public class UserDashBoardActivity extends BaseMenuActivity {

    private static final String EXTRA_ACCOUNT_INFORMATION = "user_account";
    GoogleSignInAccount account;
    FragmentActivityBinding binding;
    String name, email;
    Uri photoUrl;
    boolean backButtonToExitPressedTwice;

    /**
     * creates and configures UserDashboardFragment.
     * @return fragment
     */
    public  Fragment createFragment() {
        account = getIntent()
                .getParcelableExtra(EXTRA_ACCOUNT_INFORMATION);
        return UserDashBoardFragment.newInstance(account);
    }

    /**
     * Launch the activity.
     *
     * @param savedInstanceState - The save instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_activity);

        setSupportActionBar(binding.userdashboardInToolbar);

       FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                name = user.getDisplayName();
                email = user.getEmail();
                photoUrl = user.getPhotoUrl();
            }

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("email", email);
        bundle.putString("photoUrl", photoUrl.toString());

        if (fragment == null) {
            fragment = createFragment();
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    /**
     * Create a well configured intent object.
     *
     * @param packageContext - context.
     * @param account        - A google account
     * @return an intent.
     */
    public static Intent newIntent(Context packageContext, GoogleSignInAccount account) {
        Intent intent = new Intent(packageContext, UserDashBoardActivity.class);
        intent.putExtra(EXTRA_ACCOUNT_INFORMATION, account);
        return intent;
    }

    @Override
    public void onBackPressed() {
        if (backButtonToExitPressedTwice) {
            super.onBackPressed();
            finish();
            moveTaskToBack(true);
        } else {
            Toast.makeText(this.getApplicationContext(), "Press again to exit.",
                    Toast.LENGTH_SHORT).show();
        }

        this.backButtonToExitPressedTwice = true;

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                backButtonToExitPressedTwice = false;
            }

        }, 2000);
    }

}
