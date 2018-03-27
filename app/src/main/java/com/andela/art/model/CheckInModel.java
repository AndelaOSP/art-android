
package com.andela.art.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

/**
 * Check in model.
 */
@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class CheckInModel {

    @SerializedName("action")
    private String mAction;
    @SerializedName("security_user")
    private String mSecurityUser;
    @SerializedName("serial_number")
    private String mSerialNumber;

    /**
     * Action getter.
     * @return mAction - String
     */
    public String getAction() {
        return mAction;
    }

    /**
     * Action setter.
     * @param action - string
     */
    public void setAction(String action) {
        mAction = action;
    }

    /**
     * Security user Getter.
     * @return mSecurityUser - string
     */
    public String getSecurityUser() {
        return mSecurityUser;
    }

    /**
     * Security user setter.
     * @param securityUser - String security user name
     */
    public void setSecurityUser(String securityUser) {
        mSecurityUser = securityUser;
    }

    /**
     * Serial number getter.
     * @return mSerialNumber - String
     */
    public String getSerialNumber() {
        return mSerialNumber;
    }

    /**
     * Serial number setter.
     * @param serialNumber - String
     */
    public void setSerialNumber(String serialNumber) {
        mSerialNumber = serialNumber;
    }

}
