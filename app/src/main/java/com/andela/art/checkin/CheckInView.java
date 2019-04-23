package com.andela.art.checkin;

import com.andela.art.models.Asset;

/**
 * Check in view.
 */

public interface CheckInView {
    /**
     * Display check out button.
     * @param asset - Asset instance
     */
    void showCheckout(Asset asset);

    /**
     * Pass intent data to the views.
     */
    void displayDetails();

    /**
     * Load image to the image view from intent url.
     * @param url - image url
     */
    void loadResizedImage(String  url);

    /**
     * Get back to security dashboard activity.
     */
    void goToCheckSerial();

    /**
     * show user when an error occurs.
     * @param logType - The current log status
     */
    void displayError(String logType);
}
