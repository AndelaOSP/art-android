package com.andela.art.lostasset;


import java.util.ArrayList;

public class DataStore implements LostAssetModel {

    @Override
    public ArrayList<String> fetchCohorts() {
        ArrayList<String> cohorts = new ArrayList();
        cohorts.add("Select Cohort");
        // API call will go here and received data added to cohorts.

        return cohorts;
    }

    @Override
    public ArrayList<String> fetchAssets() {
        ArrayList<String> assets = new ArrayList();
        assets.add("Select Lost Asset");
        // API call will go here and received data added to assets.

        return assets;
    }
}
