package com.andela.art.securitydashboard.utils;


import com.andela.art.api.UserAssetResponse;
import com.andela.art.models.Asignee;
import com.andela.art.models.Asset;
import com.andela.art.securitydashboard.presentation.SerialView;

/**
 * Mock SecurityDashboardUtils class.
 */
public class SecurityDashboardUtils {
    SerialView view;
    Asset asset;
    Asignee asignee;

    /**
     * constructor.
     * @param asset asset response
     * @param view the view
     */
    public SecurityDashboardUtils(UserAssetResponse asset, SerialView view) {
        this.view = view;
        this.asset = new Asset();
    }

    /**
     * initiate Checkin intent.
     */
    public void goToCheckin() {
        setDummyData();
        view.handleCheckinIntent(asset);
    }

    /**
     * Set asset dummy data.
     *
     */
    private void setDummyData() {
        //Asignee
        asignee = new Asignee();
        asignee.setCohort(18);
        asignee.setSlackHandle(null);
        asignee.setDateJoined("2018-04-19T08:48:54.859461Z");
        asignee.setFirstName("Margaret");
        asignee.setFullName("Margaret Kinyanjui");
        asignee.setEmail("margaret.kinyanjui@andela.com");
        asignee.setId(21);
        asignee.setLastLogin(null);
        asignee.setLastModified("2018-04-19T08:48:55.061424Z");
        asignee.setLastName("Kinyanjui");
        asignee.setPhoneNumber(null);
        asignee.setPicture(null);


        //Asset
        asset.setSerialNumber("serial1");
        asset.setCheckinStatus("checked_in");
        asset.setAssignedTo(asignee);
        asset.setAllocationStatus("Available");
        asset.setAssetType("Extension Cord");
        asset.setCreatedAt("2018-03-22T14:47:26.977310Z");
        asset.setId(3);
        asset.setItemCode(null);
        asset.setLastModified("2018-05-10T09:43:09.950324Z");
        asset.setModelNumber(null);

    }
}
