package com.andela.art.serialentry.presentation;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.andela.art.R;
import com.andela.art.common.ApplicationComponent;
import com.andela.art.common.ArtApplication;
import com.andela.art.serialentry.injection.DaggerSerialEntryComponent;
import com.andela.art.serialentry.injection.SerialEntryModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zack on 3/5/18.
 */

public class SerialEntryActivity extends AppCompatActivity implements SerialView{
    @Inject
    SerialPresenter serialPresenter;

    @BindView(R.id.addSerial)
    FloatingActionButton addSerialButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serial_entry_layout);
        ButterKnife.bind(this);
        ApplicationComponent applicationComponent = ((ArtApplication) getApplication()).applicationComponent();

        DaggerSerialEntryComponent.builder()
                .applicationComponent(applicationComponent)
                .serialEntryModule(new SerialEntryModule())
                .build()
                .inject(this);
        serialPresenter.attachView(this);
    }
    @OnClick(R.id.addSerial)
    public void openDialog(){
        final EditText editText = new EditText(this);
        AlertDialog serialDialog = new AlertDialog.Builder(this)
                .setTitle("Please enter the serial number")
                .setView(editText)
                .setPositiveButton("OK", (DialogInterface dialogInterface, int i) -> {
                        String input = editText.getText().toString();
                        onConfirmClicked(input);
                    })
                .setCancelable(true)
                .show();

    }
    @Override
    public void onDataEntered() {
    }

    @Override
    public void onConfirmClicked(String serial) {
        serialPresenter.getAsset(serial);
    }
}
