
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
    private Long mId;

    /**
     * Id getter.
     * @return mId
     */
    public Long getId() {
        return mId;
    }

    /**
     * Id setter.
     * @param id - check in id
     */
    public void setId(Long id) {
        mId = id;
    }

}
