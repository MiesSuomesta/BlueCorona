package com.lja.bluecorona;

import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class btMyScannerCallback extends ScanCallback {
    public btMyScanner         mScanner          =null;

    public btMyScannerCallback(btMyScanner pScanner) {
        super();

        mScanner = pScanner;
    }

    @Override
    public void onScanResult(int callbackType, ScanResult result) {
        super.onScanResult(callbackType, result);

        if (
                (result == null) ||
                        ((result != null) && (result.getDevice() == null))
        ) {
            return;
        }

        Log.e("BLE", "Discovery onScanResult: " + result);

        int iRSSi = result.getRssi();
        ScanRecord rec = result.getScanRecord();

        byte[] mServiceData = rec.getServiceData(cBTRFCommConstants.BTC_UUID_OF_DATA);

        if ((mServiceData == null) ||
                ((mServiceData != null) && (mServiceData.length == 0))
        ) {
            Log.d(TAG, "Got no service data");
            mServiceData = null;
        }

        BTdevice tmp = new BTdevice(result.getDevice(), iRSSi, mServiceData);

        mScanner.addBTDevice(tmp);
    }

    @Override
    public void onBatchScanResults(List<ScanResult> results) {
        super.onBatchScanResults(results);
    }

    @Override
    public void onScanFailed(int errorCode) {
        Log.i("BLE", "Discovery onScanFailed: " + errorCode);
        super.onScanFailed(errorCode);
    }
}
