package com.andela.art.securitydashboard.utils;

import android.widget.Toast;

import com.andela.art.api.UserAssetResponse;
import com.andela.art.models.Asset;
import com.andela.art.securitydashboard.presentation.SerialView;

/**
 * Prod SecurityDashboardUtils class.
 */
public class SecurityDashboardUtils {
    UserAssetResponse asset;
    SerialView view;

    /**
     * constructor.
     * @param asset response
     * @param serialView serialview
     */
    public SecurityDashboardUtils(UserAssetResponse asset, SerialView serialView) {
        this.asset = asset;
        this.view = serialView;
    }

    /**
     * Initiate intent to move to Check in activity.
     */
    public void goToCheckin() {
        if (asset.getAssets() == null) {
            view.handleToast("The asset serial number is not available.",
                    Toast.LENGTH_LONG, false);
        } else {
            Asset assetInfo = asset.getAssets().get(0);
            if (assetInfo.getCurrentStatus().equals("Available")) {
                view.handleToast("The asset serial number is not assigned to any user.",
                        Toast.LENGTH_LONG, false);
            } else {
                view.handleCheckinIntent(assetInfo);
            }
        }
    }

}
