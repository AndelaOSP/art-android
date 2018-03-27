package com.andela.art.checkin;


/**
 * Check in presenter.
 */

public interface CheckInPresenter {
     /**
      * Check in users with asset serial number.
      * @param serialNumber - asset serial number.
      * @param securityUser - security user name.
      */
     void checkIn(String serialNumber, String securityUser);

     /**
      * Call method to show checkout button.
      */
     void callShowCheckOut();

}
