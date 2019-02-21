package com.andela.art.securitydashboard.utils;


import android.widget.Toast;

import com.andela.art.api.UserAssetResponse;
import com.andela.art.models.Asset;
import com.andela.art.securitydashboard.presentation.SerialView;

/**
 * Mock SecurityDashboardUtils class.
 */
public class SecurityDashboardUtils {
    private static final String TAG = "Check";
    SerialView view;
    Asset asset;
    UserAssetResponse responseAsset;

    /**
     * constructor.
     * @param asset asset response
     * @param view the view
     */
    public SecurityDashboardUtils(UserAssetResponse asset, SerialView view) {
        this.view = view;
        this.responseAsset = asset;
    }

    /**
     * initiate Checkin intent.
     */
    public void goToCheckin() {
        if (responseAsset.getCount() == 0) {
            view.handleToast("The asset is not available.",
                    Toast.LENGTH_LONG, false);
        } else {
            asset = responseAsset.getAssets().get(0);
            if (asset.getAssignee() == null) {
                view.handleToast("Asset not assigned.",
                        Toast.LENGTH_LONG, false);
            } else {
                view.handleCheckinIntent(asset);
            }
        }
    }

}
