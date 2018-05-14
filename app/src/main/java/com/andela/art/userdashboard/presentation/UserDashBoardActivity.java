package com.andela.art.userdashboard.presentation;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.andela.art.R;
import com.andela.art.databinding.FragmentActivityBinding;
import com.andela.art.models.Asset;
import com.andela.art.root.ApplicationComponent;
import com.andela.art.root.ApplicationModule;
import com.andela.art.root.ArtApplication;
import com.andela.art.root.BaseMenuActivity;
import com.andela.art.userdashboard.injection.DaggerUserDashBoardComponent;
import com.andela.art.userdashboard.injection.UserDashBoardModule;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import javax.inject.Inject;


/**
 * Hosts UserDashboardFragment.
 */
public class UserDashBoardActivity extends BaseMenuActivity implements SliderView {

    private static final String EXTRA_ACCOUNT_INFORMATION = "user_account";
    private PagerAdapter pagerAdapter;
    GoogleSignInAccount account;
    FragmentActivityBinding binding;
    String name, email;
    Uri photoUrl;
    boolean backButtonToExitPressedTwice;
    FragmentManager fragmentManager;


    @Inject
    AssetsPresenter assetsPresenter;

    ApplicationComponent applicationComponent;


    /**
     * creates and configures UserDashboardFragment.
     * @return fragment
     */
    public  Fragment profileFragment() {
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
        initializeUserDashBoardComponent();
        assetsPresenter.getAssets();



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                name = user.getDisplayName();
                email = user.getEmail();
                photoUrl = user.getPhotoUrl();
            }
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("email", email);
        bundle.putString("photoUrl", photoUrl.toString());

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment profileFragment = profileFragment();
        profileFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.profile_container, profileFragment);
        fragmentTransaction.commit();


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

    /**
     * Initialize userDashBoardComponent.
     */
    private void initializeUserDashBoardComponent() {
        applicationComponent = ((ArtApplication) this
                .getApplication()).applicationComponent();

        DaggerUserDashBoardComponent.builder()
                .applicationComponent(applicationComponent)
                .applicationModule(new ApplicationModule(getApplication()))
                .userDashBoardModule(new UserDashBoardModule())
                .build()
                .inject(this);
        assetsPresenter.attachView(this);
    }


    /**
     * Handle error message.
     *
     * @param error error.
     */
    @Override
    public void onDisplayErrorMessage(Throwable error) {
        Toast.makeText(this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
    }

    /**
     * gets all the assets for a particular user.
     *
     * @param assets List of assets
     */
    @Override
    public void onGetAssets(List<Asset> assets) {
        pagerAdapter = new PagerAdapter(fragmentManager, assets);
        binding.pager.setAdapter(pagerAdapter);
        binding.tabDots.setupWithViewPager(binding.pager, true);
    }
}
