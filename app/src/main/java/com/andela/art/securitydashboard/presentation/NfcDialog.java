package com.andela.art.securitydashboard.presentation;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;

import com.andela.art.R;
import com.andela.art.databinding.NfcDialogBinding;

/**
 * Created by kalela on 20/11/18.
 */

public class NfcDialog extends AppCompatDialogFragment {
    /**
     * Instance used to access nfc dialog.
     * @return nfcDialog
     */
    public static NfcDialog newInstance() {
        NfcDialog nfcDialog = new NfcDialog();
        nfcDialog.setStyle(NfcDialog.STYLE_NO_TITLE, 0);
        return nfcDialog;
    }

    /**
     * Create dialog.
     * @param savedInstanceState - state bundle
     * @return Dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        NfcDialogBinding nfcDialogBinding = DataBindingUtil.
                inflate(LayoutInflater.from(getContext()),
                        R.layout.nfc_dialog, null, false);

        nfcDialogBinding.submit.setOnClickListener(view -> {
            String serial = ((NfcSecurityDashboardActivity) getActivity()).nfcSerial;
            if (serial != null) {
                ((NfcSecurityDashboardActivity) getActivity()).onConfirmClicked(serial,
                        "Asset code place-holder");
                dismiss();
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(nfcDialogBinding.getRoot())
                .create();
    }
}
