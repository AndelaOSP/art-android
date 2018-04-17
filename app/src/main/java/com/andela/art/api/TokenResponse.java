package com.andela.art.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Token response pojo.
 */
public class TokenResponse {

    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("expires_in")
    @Expose
    private Integer expiresIn;
    @SerializedName("token_type")
    @Expose
    private String tokenType;
    @SerializedName("scope")
    @Expose
    private String scope;

    /**
     *
     * @return accessToken - String
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     *
     * @return expiresIn - Integer
     */
    public Integer getExpiresIn() {
        return expiresIn;
    }

    /**
     *
     * @return tokenType - String
     */
    public String getTokenType() {
        return tokenType;
    }

}
