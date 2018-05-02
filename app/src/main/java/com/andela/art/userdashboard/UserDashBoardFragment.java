package com.andela.art.userdashboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andela.art.R;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;


/**
 * Displays the user dashboard screen.
 */
public class UserDashBoardFragment extends Fragment {
    private static final String ARG_ACCOUNT = "google_account";
    private GoogleSignInAccount mGoogleSignInAccount;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mGoogleSignInAccount = getArguments()
                .getParcelable(ARG_ACCOUNT);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_dashboard,
                container, false);
    }

    /**
     * Instances of this fragment to have an account object be passed.
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
}
