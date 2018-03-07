package com.andela.art.serialentry.data;

/**
 * Created by zack on 3/5/18.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Asset {

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

}
