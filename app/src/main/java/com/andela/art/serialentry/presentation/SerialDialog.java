package com.andela.art.serialentry.presentation;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;

import com.andela.art.R;
import com.andela.art.databinding.SerialDialogBinding;

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
            ((SerialEntryActivity) getActivity()).onConfirmClicked(serial);
            dismiss();
        });

        dialogBinding.cancel.setOnClickListener(view -> {
            dismiss();
        });

        return new AlertDialog.Builder(getActivity())
                .setView(dialogBinding.getRoot())
                .create();
    }
}
