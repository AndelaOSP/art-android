package com.andela.art.serialentry.data;

/**
 * Created by zack on 3/5/18.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Asset POJO class.
 */
public class Asset implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("item_code")
    @Expose
    private String itemCode;
    @SerializedName("serial_number")
    @Expose
    private String serialNumber;

    /**
     * Return id.
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set asset id.
     *
     * @param id asset id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Return userid.
     *
     * @return userid
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Set asset userId.
     *
     * @param userId id for asset owner
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Return itemcode.
     * @return itemcode
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * Set asset itemCode.
     *
     * @param itemCode asset item code
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * Return serial number.
     * @return id
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Set serial number.
     *
     * @param serialNumber asset item code
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

}
