
package com.andela.art.checkin.models;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
public class CheckinResponse {

    @SerializedName("id")
    private Long mId;
    @SerializedName("serial")
    private String mSerial;

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getSerial() {
        return mSerial;
    }

    public void setSerial(String serial) {
        mSerial = serial;
    }

}
