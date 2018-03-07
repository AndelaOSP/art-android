package com.andela.art.lostasset;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.andela.art.R;

import java.util.ArrayList;

public class LostAsset extends AppCompatActivity implements LostAssetView {

    LostAssetPresenter presenter;
    Spinner sCohort, sLostAsset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_asset);

        DataStore model = new DataStore();
        presenter = new LostAssetPresenter(this, model);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        presenter.getCohorts();
        presenter.getAssets();

    }

    private ArrayAdapter<String> createArrayAdapter(ArrayList<String> items) {
        ArrayAdapter<String> cohortAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, items){
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                if(position == 0) {
                    ((TextView) view).setTextColor(Color.GRAY);
                }
                return view;
            }

            @Override
            public boolean isEnabled(int position) {
                if(position == 0) {
                    return false;
                }
                return true;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                if(position == 0) {
                    ((TextView) view).setTextColor(Color.GRAY);
                }
                return view;
            }
        };

        return cohortAdapter;
    }

    @Override
    public void showCohorts(ArrayList<String> cohorts) {
        sCohort = findViewById(R.id.sCohort);
        sCohort.setAdapter(createArrayAdapter(cohorts));
    }

    @Override
    public void showAssets(ArrayList<String> assets) {
        sLostAsset = findViewById(R.id.sLostItem);
        sLostAsset.setAdapter(createArrayAdapter(assets));
    }
}
