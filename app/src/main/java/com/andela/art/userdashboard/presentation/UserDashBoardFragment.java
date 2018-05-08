package com.andela.art.userdashboard.presentation;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.andela.art.R;
import com.andela.art.databinding.FragmentUserDashboardBinding;
import com.andela.art.models.Asset;
import com.andela.art.root.ApplicationComponent;
import com.andela.art.root.ApplicationModule;
import com.andela.art.root.ArtApplication;
import com.andela.art.userdashboard.injection.DaggerUserDashBoardComponent;
import com.andela.art.userdashboard.injection.UserDashBoardModule;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

/**
 * Displays the user dashboard screen.
 */
public class UserDashBoardFragment extends Fragment implements UserDashBoardView {

    @Inject
    AssetsPresenter assetsPresenter;

    ApplicationComponent applicationComponent;
    private static final String ARG_ACCOUNT = "google_account";
    private GoogleSignInAccount mGoogleSignInAccount;
    FragmentUserDashboardBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        initializeUserDashBoardComponent();

        mGoogleSignInAccount = getArguments()
                .getParcelable(ARG_ACCOUNT);
        assetsPresenter.getAssets();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_user_dashboard, container, false);

        binding.name.setText(getArguments().getString("name"));
        binding.emailAddress.setText(getArguments().getString("email"));
        onLoadResizedImage(getArguments().getString("photoUrl"));

        return binding.getRoot();
    }

    /**
     * Instances of this fragment to have an account object be passed.
     *
     * @param account - GoogleSignInAccount
     * @return fragment
     */
    public static UserDashBoardFragment newInstance(GoogleSignInAccount account) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARG_ACCOUNT, account);

        UserDashBoardFragment fragment = new UserDashBoardFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    /**
     * Initialize userDashBoardComponent.
     */
    private void initializeUserDashBoardComponent() {
        applicationComponent = ((ArtApplication) getActivity()
                .getApplication()).applicationComponent();

        DaggerUserDashBoardComponent.builder()
                .applicationComponent(applicationComponent)
                .applicationModule(new ApplicationModule(getActivity().getApplication()))
                .userDashBoardModule(new UserDashBoardModule())
                .build()
                .inject(this);
        assetsPresenter.attachView(this);
    }

    @Override
    public void onLoadResizedImage(String url) {

        if (url == null) {

            Picasso.with(getActivity())
                    .load(R.drawable.photo)
                    .fit()
                    .centerCrop()
                    .into(binding.ivPhoto);
        } else {
            Picasso.with(getActivity())
                    .load(url)
                    .fit()
                    .centerCrop()
                    .into(binding.ivPhoto);
        }
    }

    @Override
    public void onDisplayErrorMessage(Throwable error) {
        Toast.makeText(getActivity(), "Could not fetch assets", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetAssets(List<Asset> assets) {
        if (!assets.isEmpty()) {
            Asset asset = assets.get(0);
            binding.Asset.setText(getString(R.string.Asset));
            binding.AssetName.setText(asset.getModelNumber());
            binding.Serial.setText("Serial: " + asset.getSerialNumber());
            Drawable drawable = getResources().getDrawable(R.drawable.ic_computer_black_24dp);
            binding.mac.setImageDrawable(drawable);
            binding.Tag.setText("Tag: " + asset.getItemCode());
        } else {
            binding.Serial.setText(R.string.unassigned);
        }
    }
}
