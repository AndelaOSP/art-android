package com.andela.art.checkin;


/**
 * Check in presenter.
 */

public interface CheckInPresenter {
     /**
      * Check in users with asset serial number.
      * @param serialNumber - asset serial number.
      */
     void checkIn(String serialNumber);

     /**
      * Call method to show checkout button.
      */
     void callShowCheckOut();

}
