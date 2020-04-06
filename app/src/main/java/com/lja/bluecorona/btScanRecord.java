package com.lja.bluecorona;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.lja.bluecorona.cBTRFCommConstants.hmEBTLEDescriptions;

public class btScanRecord {

    private int    mlen           = 0;
    private int    mtype          = 0;
    private String mDecodedRecord = null;

    public btScanRecord(int length, int type, byte[] data) {
        try {
            mDecodedRecord = new String(data,"UTF-8");
            mlen = length;
            mtype = type;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String stype = (type > -1) ? hmEBTLEDescriptions.get(type) : "Nada";
        Log.d("DEBUG", "Length: " + length + " Type : " + type + "(" + stype + ") Data : " + mDecodedRecord);

    }
}
