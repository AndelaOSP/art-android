package com.andela.art.securitydashboard.injection;

import com.andela.art.api.UserAssetResponse;
import com.andela.art.securitydashboard.presentation.SerialView;
import com.andela.art.securitydashboard.utils.SecurityDashboardUtils;

/**
 * Created by kalela on 2/19/19.
 */
public final class SecurityDashboardUtilsInjector {
    /**
     * constructor.
     */
    private SecurityDashboardUtilsInjector() {
        //Intentional
    }

    /**
     * Provide SecurityDashboardUtils.
     * @param asset asset response.
     * @param serialView view.
     * @return SecurityDashboardUtils
     */
    public static SecurityDashboardUtils provideSecurityDashboardUtils(UserAssetResponse asset,
                                                                           SerialView serialView) {

        return new SecurityDashboardUtils(asset, serialView);

    }

}
