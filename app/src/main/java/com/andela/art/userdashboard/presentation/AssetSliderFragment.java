package com.andela.art.userdashboard.presentation;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andela.art.R;
import com.andela.art.databinding.FragmentAssetSliderBinding;

/**
 * Created by zack on 5/10/18.
 */

public class AssetSliderFragment extends Fragment {


    FragmentAssetSliderBinding binding;
    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not
     *                           add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_asset_slider,
                container, false);
        setDetails();
        return binding.getRoot();
    }

    /**
     * Set asset details on card.
     */
    public void setDetails() {
        if (!getArguments().isEmpty()) {
            binding.assetType.setText(getArguments().getString("type"));
            binding.serial.setText(getArguments().getString("serial"));
            binding.tag.setText(getArguments().getString("tag"));
        } else {
            binding.serial.setText(R.string.unassigned);
        }
    }
}
