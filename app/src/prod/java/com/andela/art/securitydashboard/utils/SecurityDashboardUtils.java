package com.andela.art.securitydashboard.utils;

import android.widget.Toast;

import com.andela.art.api.UserAssetResponse;
import com.andela.art.models.Asset;
import com.andela.art.securitydashboard.presentation.SerialView;

/**
 * Prod SecurityDashboardUtils class.
 */
public class SecurityDashboardUtils {
    private static final String TAG = "Check";
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
        if (asset.getCount() == 0) {
            view.handleToast("This asset is not in the system.",
                    Toast.LENGTH_LONG, false);
        } else {
            Asset assetInfo = asset.getAssets().get(0);
            if (assetInfo.getAssignee() == null) {
                view.handleToast("Asset with this serial number is not assigned to anyone.",
                        Toast.LENGTH_LONG, false);
            } else {
                view.handleCheckinIntent(assetInfo);
            }
        }
    }

}
