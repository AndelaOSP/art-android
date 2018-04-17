package com.andela.art.securitydashboard.presentation;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;

import com.andela.art.R;
import com.andela.art.checkin.CheckInActivity;
import com.andela.art.databinding.SerialDialogBinding;
import com.andela.art.models.Asignee;
import com.andela.art.models.Asset;

/**
 * Created by zack on 3/27/18.
 */

public class SerialDialog extends AppCompatDialogFragment {

    /**
     * Instance used to access serial dialog.
     * @return serialDialog
     */
    public static SerialDialog newInstance() {
        SerialDialog serialDialog = new SerialDialog();
        serialDialog.setStyle(SerialDialog.STYLE_NO_TITLE, 0);
        return serialDialog;
    }

    /**
     * Create dialog.
     * @param savedInstanceState - state bundle
     * @return Dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        SerialDialogBinding dialogBinding = DataBindingUtil.
                inflate(LayoutInflater.from(getContext()),
                        R.layout.serial_dialog, null, false);

        dialogBinding.submit.setOnClickListener(view -> {
            String serial = dialogBinding.serialEditText.getText().toString();
            ((SecurityDashboardActivity) getActivity()).onConfirmClicked(serial);
            goToCheckIn();
            dismiss();
        });

        dialogBinding.cancel.setOnClickListener(view -> {
            dismiss();
        });

        return new AlertDialog.Builder(getActivity())
                .setView(dialogBinding.getRoot())
                .create();
    }

    /**
     * Get to Check in activity.
     */
    public void goToCheckIn() {
        Intent intent = new Intent(getActivity(), CheckInActivity.class);

        Asignee asignee = new Asignee();
        asignee.setCohort(18);
        asignee.setDateJoined("20-12-2017");
        asignee.setEmail("mudge.fudge@mail.com");
        asignee.setFirstName("mudge");
        asignee.setLastName("Fudge");
        asignee.setFullName("Mudge Fudge");
        asignee.setPicture(null);
        asignee.setId(1);
        asignee.setSlackHandle("");
        asignee.setLastLogin("");
        asignee.setLastModified("");


        Asset asset = new Asset();
        asset.setId(1);
        asset.setAllocationStatus("Checkin");
        asset.setAssignedTo(asignee);
        asset.setCheckinStatus("Checkout");
        asset.setCreatedAt("12-09-2018");
        asset.setItemCode("ER34521");
        asset.setLastModified("");
        asset.setModelNumber("");
        asset.setSerialNumber("CRT5647Y");

        Bundle bundle = new Bundle();
        bundle.putSerializable("asset", asset);
        intent.putExtras(bundle);

        startActivity(intent);
    }
}
