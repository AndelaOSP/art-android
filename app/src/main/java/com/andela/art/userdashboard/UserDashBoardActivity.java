package com.andela.art.userdashboard;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.andela.art.R;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Hosts UserDashboardFragment.
 */
public class UserDashBoardActivity extends AppCompatActivity {
    private static final String EXTRA_ACCOUNT_INFORMATION = "user_account";

    /**
     * creates and configures UserDashboardFragment.
     * @return fragment
     */
    public  Fragment createFragment() {
        GoogleSignInAccount account = getIntent()
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
        setContentView(R.layout.fragment_activity);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
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
}
