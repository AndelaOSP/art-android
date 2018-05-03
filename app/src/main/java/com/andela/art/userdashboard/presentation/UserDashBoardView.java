package com.andela.art.userdashboard.presentation;

import android.net.Uri;

/**
 * Andela User dashboard View.
 */
public interface UserDashBoardView {

    /**
     * Load image to the image view from intent url.
     * @param url - image url
     */
    void loadResizedImage(Uri url);

}
