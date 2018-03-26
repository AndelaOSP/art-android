package com.andela.art.serialentry.presentation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.andela.art.R;
import com.andela.art.checkin.CheckInActivity;
import com.andela.art.common.ApplicationComponent;
import com.andela.art.common.ArtApplication;
import com.andela.art.serialentry.data.Asset;
import com.andela.art.serialentry.injection.DaggerSerialEntryComponent;
import com.andela.art.serialentry.injection.SerialEntryModule;
//import com.andela.art.serialentry.injection.DaggerSerialEntryComponent;
//import com.andela.art.serialentry.injection.SerialEntryModule;

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
    @BindView(R.id.itemcode)
    TextView itemView;

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
        editText.setHint(R.string.serial_hint);
        AlertDialog serialDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.enter_text)
                .setView(editText)
                .setPositiveButton("OK", (DialogInterface dialogInterface, int i) -> {
                        String input = editText.getText().toString();
                        onConfirmClicked(input);
                    })
                .setCancelable(true)
                .show();

    }
    @Override
    public void onConfirmClicked(String serial) {
        serialPresenter.getAsset(serial);
}

    @Override
    public void sendIntent(Asset asset) {
        Log.d("ART-2",asset.getItemCode());
        Intent checkInIntent = new Intent(SerialEntryActivity.this, CheckInActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("asset", asset);
        checkInIntent.putExtras(bundle);
        startActivity(checkInIntent);

    }
}
