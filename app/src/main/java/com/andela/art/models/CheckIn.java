package com.andela.art.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MADGE on 2/26/18.
 */

public class CheckIn {
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("serialNumber")
    @Expose
    private String serialNumber;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("chortNumber")
    @Expose
    private Integer chortNumber;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getChortNumber() {
        return chortNumber;
    }

    public void setChortNumber(Integer chortNumber) {
        this.chortNumber = chortNumber;
    }

}
