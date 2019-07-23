package com.andela.art.userdashboard.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andela.art.R;
import com.andela.art.incidentreport.presentation.IncidentReportActivity;
import com.andela.art.models.Asset;

import java.util.List;

/**
 * Created by PromasterGuru 29/07/2019.
 * This adapter managers views for the assets recycler view.
 */
public class AssetAdapter extends RecyclerView.Adapter<AssetAdapter.MyViewHolder> {
    private final List<Asset> assets;
    private Context context;

    /**
     * @param assets - list of assets fetched.
     * @param context - Application Context.
     */
    public AssetAdapter(Context context, List<Asset> assets) {
        this.context = context;
        this.assets = assets;
    }

    @Override
    public AssetAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.fragment_asset_slider, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(AssetAdapter.MyViewHolder holder, int position) {
        /**
         * display asset's information on recycler view holder
         */
        holder.type.setText(assets.get(position).getAssetType());
        holder.serial.setText(assets.get(position).getSerialNumber());
        holder.tag.setText(assets.get(position).getItemCode());
    }

    @Override
    public int getItemCount() {
        return assets.size();
    }
    /**
     * Extends RecyclerView.ViewHolder.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView type, serial, tag;

        /**
         * @param view - Adapter view
         */
        public MyViewHolder(View view) {
            super(view);
            type = view.findViewById(R.id.asset_type);
            serial = view.findViewById(R.id.serial);
            tag = view.findViewById(R.id.tag);

            /**
             * Set an OnClickListener to the ViewHolder
             * Launch the IncidentReportActivity when an asset item is clicked
             */

            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (!assets.isEmpty()) {
                        Asset asset = assets.get(getAdapterPosition());
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("asset", asset);
                        Intent intent = new Intent(context, IncidentReportActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                }
            });

        }
    }
}
