
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
    private AssignedTo mAssignedTo;
    @SerializedName("checkin_status")
    private String mCheckinStatus;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("id")
    private int mId;
    @SerializedName("item_code")
    private String mItemCode;
    @SerializedName("last_modified")
    private String mLastModified;
    @SerializedName("model_number")
    private String mModelNumber;
    @SerializedName("serial_number")
    private String mSerialNumber;

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
     * Assigned to getter.
     * @return mAssignedTo - AssignedTo
     */
    public AssignedTo getAssignedTo() {
        return mAssignedTo;
    }

    /**
     * Assigned to setter.
     * @param assignedTo - user the asset is assigned to
     */
    public void setAssignedTo(AssignedTo assignedTo) {
        mAssignedTo = assignedTo;
    }

    /**
     * Check in status getter.
     * @return mCheckinStatus - String
     */
    public String getCheckinStatus() {
        return mCheckinStatus;
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
        return mItemCode;
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
        return mSerialNumber;
    }

    /**
     * Asset serial number setter.
     * @param serialNumber - String
     */
    public void setSerialNumber(String serialNumber) {
        mSerialNumber = serialNumber;
    }

}
