package com.andela.art.userdashboard.presentation;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import com.andela.art.R;
import com.andela.art.databinding.FragmentActivityBinding;
import com.andela.art.root.ApplicationComponent;
import com.andela.art.root.ApplicationModule;
import com.andela.art.root.ArtApplication;
import com.andela.art.root.BaseMenuActivity;
import com.andela.art.userdashboard.injection.DaggerUserDashBoardComponent;
import com.andela.art.userdashboard.injection.UserDashBoardModule;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;


/**
 * Hosts UserDashboardFragment.
 */
public class UserDashBoardActivity extends BaseMenuActivity {
    private static final String EXTRA_ACCOUNT_INFORMATION = "user_account";
    ApplicationComponent mApplicationComponent;
    GoogleSignInAccount mAccount;
    FragmentActivityBinding mBinding;

    /**
     * creates and configures UserDashboardFragment.
     * @return fragment
     */
    public  Fragment createFragment() {
        mAccount = getIntent()
                .getParcelableExtra(EXTRA_ACCOUNT_INFORMATION);
        return UserDashBoardFragment.newInstance(mAccount);
    }

    /**
     * Launch the activity.
     *
     * @param savedInstanceState - The save instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.fragment_activity);

        mApplicationComponent = ((ArtApplication) getApplication()).applicationComponent();
        initializeUserDashBoardComponent();
        setSupportActionBar(mBinding.userdashboardInToolbar);

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
     * Initialize userDashBoardComponent.
     */
    private void initializeUserDashBoardComponent() {
        DaggerUserDashBoardComponent.builder()
                .applicationModule(new ApplicationModule(getApplication()))
                .userDashBoardModule(new UserDashBoardModule())
                .build()
                .inject(this);
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
