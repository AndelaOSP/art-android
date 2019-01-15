package com.andela.art.securitydashboard.presentation;

import com.andela.art.models.Asset;
import com.andela.art.root.View;

/**
 * Created by zack on 3/5/18.
 */

public interface SerialView extends View {
    /**
     *
     * @param serial serial entered by dialog.
     * @param assetCode asset code.
     */
    void onConfirmClicked(String serial, String assetCode);

    /**
     *
     * @param asset asset that is passed from presenter.
     */
    void sendIntent(Asset asset);

    /**
     * redirect user if they are logged out.
     */
    void redirectLoggedOutUser();

    /**
     *
     * @param email email
     * @param name name
     * @param photo photo
     */
    void setAccountDetails(String email, String name, String photo);

    /**
     *
     * @param error error
     */
    void displayErrorMessage(Throwable error);
}
