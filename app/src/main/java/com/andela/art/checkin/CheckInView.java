package com.andela.art.checkin;

/**
 * Check in view.
 */

public interface CheckInView {
    /**
     * Display check out button.
     */
    void showCheckout();

    /**
     * Pass intent data to the views.
     */
    void displayDetails();

    /**
     * Load image to the image view from intent url.
     */
    void loadResizedImage();
}
