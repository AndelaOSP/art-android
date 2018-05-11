package com.andela.art.userdashboard.presentation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.andela.art.models.Asset;

import java.util.List;

/**
 * Created by zack on 5/11/18.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    private List<Asset> assets;
    private Asset asset;

    public PagerAdapter(FragmentManager fm, List<Asset> assets) {

        super(fm);
        this.assets = assets;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        AssetSliderFragment fragment = new AssetSliderFragment();
        asset = assets.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("type", asset.getAssetType());
        bundle.putString("serial", asset.getSerialNumber());
        bundle.putString("tag", asset.getItemCode());
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        Log.d("count",String.valueOf(assets.size()));
        return assets.size();
    }

    /**
     * Called when the host view is attempting to determine if an item's position
     * has changed. Returns {@link #POSITION_UNCHANGED} if the position of the given
     * item has not changed or {@link #POSITION_NONE} if the item is no longer present
     * in the adapter.
     * <p>
     * <p>The default implementation assumes that items will never
     * change position and always returns {@link #POSITION_UNCHANGED}.
     *
     * @param object Object representing an item, previously returned by a call.
     * @return object's new position index from [0, {@link #getCount()}),
     * {@link #POSITION_UNCHANGED} if the object's position has not changed,
     * or {@link #POSITION_NONE} if the item is no longer present.
     */
    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
