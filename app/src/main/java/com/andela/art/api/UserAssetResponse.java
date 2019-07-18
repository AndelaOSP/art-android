package com.andela.art.api;

import java.util.List;

import com.andela.art.models.Asset;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * UserAssetResponse pojo.
 */
public class UserAssetResponse {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("next")
    @Expose
    private String next;
    @SerializedName("previous")
    @Expose
    private String previous;
    @SerializedName("results")
    @Expose
    private List<Asset> assets;

    /**
     *
     * @return count - Integer
     */
    public Integer getCount() {
        return count;
    }

    /**
     *
     * @return next - String
     */
    public String getNext() {
        return next;
    }

    /**
     *
     * @return  previous - String
     */
    public String getPrevious() {
        return previous;
    }

    /**
     *
     * @return assets - List
     */
    public List<Asset> getAssets() {
        return assets;
    }

}
