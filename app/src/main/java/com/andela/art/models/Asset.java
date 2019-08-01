
package com.andela.art.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Asset model.
 */
public class Asset implements Serializable {

    @SerializedName("allocation_status")
    private String mAllocationStatus;
    @SerializedName("assigned_to")
    private Asignee mAsignee;
    @SerializedName("checkin_status")
    private String mCheckinStatus;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("id")
    private int mId;
    @SerializedName("asset_code")
    private String mItemCode;
    @SerializedName("last_modified")
    private String mLastModified;
    @SerializedName("model_number")
    private String mModelNumber;
    @SerializedName("serial_number")
    private String mSerialNumber;
    @SerializedName("asset_type")
    private String mAssetType;
    @SerializedName("current_status")
    private String mCurrentStatus;

    /**
     * Allocation status getter.
     * @return mAllocationStatus - String
     */
    public String getAllocationStatus() {
        return mAllocationStatus;
    }

    /**
     * Allocation status setter.
     * @param allocationStatus - string
     */
    public void setAllocationStatus(String allocationStatus) {
        mAllocationStatus = allocationStatus;
    }
    /**
     * Current status getter.
     * @return mCurrentStatus - String
     */
    public String getCurrentStatus() {
        return String.valueOf(mCurrentStatus);
    }


    /**
     * Assigned to getter.
     * @return mAsignee - Asignee
     */
    public Asignee getAssignee() {
        return mAsignee;
    }

    /**
     * Assigned to setter.
     * @param asignee - user the asset is assigned to
     */
    public void setAssignedTo(Asignee asignee) {
        mAsignee = asignee;
    }

    /**
     * Check in status getter.
     * @return mCheckinStatus - String
     */
    public String getCheckinStatus() {
        return String.valueOf(mCheckinStatus);
    }

    /**
     * Check in status setter.
     * @param checkinStatus - String
     */
    public void setCheckinStatus(String checkinStatus) {
        mCheckinStatus = checkinStatus;
    }

    /**
     * Date created at getter.
     * @return mCreatedAt - string
     */
    public String getCreatedAt() {
        return mCreatedAt;
    }

    /**
     * Date created setter.
     * @param createdAt - String
     */
    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    /**
     * Asset Id getter.
     * @return mId - int
     */
    public int getId() {
        return mId;
    }

    /**
     * Asset id setter.
     * @param id - int
     */
    public void setId(int id) {
        mId = id;
    }

    /**
     * Item code getter.
     * @return mItemCode - String
     */
    public String getItemCode() {
        return String.valueOf(mItemCode);
    }

    /**
     * Item code setter.
     * @param itemCode - String
     */
    public void setItemCode(String itemCode) {
        mItemCode = itemCode;
    }

    /**
     * Date lastly modified getter.
     * @return mLastModified - String
     */
    public String getLastModified() {
        return mLastModified;
    }

    /**
     * Date lastly modified setter.
     * @param lastModified - String
     */
    public void setLastModified(String lastModified) {
        mLastModified = lastModified;
    }

    /**
     * Asset Model number getter.
     * @return mModelNumber - string
     */
    public String getModelNumber() {
        return mModelNumber;
    }

    /**
     * Asset Model number setter.
     * @param modelNumber - String
     */
    public void setModelNumber(String modelNumber) {
        mModelNumber = modelNumber;
    }

    /**
     * Asset serial number getter.
     * @return mSerialNumber - String
     */
    public String getSerialNumber() {
        return String.valueOf(mSerialNumber);
    }

    /**
     * Asset serial number setter.
     * @param serialNumber - String
     */
    public void setSerialNumber(String serialNumber) {
        mSerialNumber = serialNumber;
    }

    /**
     * Asset type getter.
     * @return mAssetType - String
     */
    public String getAssetType() {
        return String.valueOf(mAssetType);
    }

    /**
     * Asset type setter.
     * @param assetType - String
     */
    public void setAssetType(String assetType) {
        mAssetType = assetType;
    }
}
