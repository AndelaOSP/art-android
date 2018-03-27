
package com.andela.art.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

/**
 * Check in response model.
 */
@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class CheckInResponse {

    @SerializedName("id")
    private String mId;
    @SerializedName("serial_number")
    private String mSerialNumber;

    /**
     * Id getter.
     * @return mId - integer
     */
    public String getId() {
        return mId;
    }

    /**
     * Id getter.
     * @param id - integer
     */
    public void setId(String id) {
        mId = id;
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
