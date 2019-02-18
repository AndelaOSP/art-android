package com.andela.art.securitydashboard.presentation.threading;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.util.Log;

import com.andela.art.securitydashboard.presentation.NfcView;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Tag reader async task.
 */
public class NdefReaderTask extends AsyncTask<Tag, Void, String> {

    private static final String TAG = "NdefReaderTask";
    NfcView mView;

    /**
     * constructor.
     * @param view the view.
     */
    public NdefReaderTask(NfcView view) {
        this.mView = view;
    }

    @Override
    protected String doInBackground(Tag... params) {
        Tag tag = params[0];

        Ndef ndef = Ndef.get(tag);
        if (ndef == null) {
            return null;
        }

        NdefMessage ndefMessage = ndef.getCachedNdefMessage();

        NdefRecord[] records = ndefMessage.getRecords();
        for (NdefRecord ndefRecord : records) {
            if (ndefRecord.getTnf() == NdefRecord
                    .TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(),
                    NdefRecord.RTD_TEXT)) {
                try {
                    return readText(ndefRecord);
                } catch (UnsupportedEncodingException e) {
                    Log.e(TAG, "Unsupported Encoding", e);
                }
            }
        }

        return null;
    }

    /**
     * Read ndef data.
     * @param record read a record in tag.
     * @return string
     * @throws UnsupportedEncodingException exception.
     */
    private String readText(NdefRecord record) throws UnsupportedEncodingException {

        byte[] payload = record.getPayload();

        String textEncoding;
        if ((payload[0] & 128) == 0) {
            textEncoding = "UTF-8";
        } else {
            textEncoding = "UTF-16";
        }

        int languageCodeLength = payload[0] & 0063; //NOPMD

        return new String(payload,
                languageCodeLength + 1,
                payload.length - languageCodeLength - 1,
                textEncoding);
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            mView.updateSerial(result);
        }
    }
}
