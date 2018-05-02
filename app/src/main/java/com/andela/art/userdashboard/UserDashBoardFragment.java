package com.andela.art.userdashboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andela.art.R;


/**
 * Created by lewismbogo on 02/05/2018.
 */

/**
 * Displays the user dashboard screen.
 */
public class UserDashBoardFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_dashboard,
                container, false);
    }
}
