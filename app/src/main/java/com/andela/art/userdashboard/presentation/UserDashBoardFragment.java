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

    private static final String ARG_ACCOUNT = "google_account";
    private GoogleSignInAccount mGoogleSignInAccount;
    FragmentUserDashboardBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mGoogleSignInAccount = getArguments()
                .getParcelable(ARG_ACCOUNT);


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

}
