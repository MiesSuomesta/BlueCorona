package com.lja.bluecorona;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.media.session.MediaSession;
import android.os.ParcelUuid;
import android.util.Log;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class btMyAdvertiser {
    private final static String TAG = btMyAdvertiser.class.getSimpleName();

    public  BluetoothLeAdvertiser mBAdvert = null;
    private AdvertiseSettings mAdvertiseSettings = null;
    private AdvertiseData mAdvertiseData = null;
    public  ParcelUuid mAdvertDataUID = null;
    public  ParcelUuid mAdvertOfServiceUID = null;


    /* Callback to handle things */
    private AdvertiseCallback mBLEAdvertiseCallback = null;

    private byte[] buildTmpPacket() {
        int value;
        try {
            value = (int) cBTRFCommConstants.getBTRFCommLocalUserSicnesslvl()[0];
        } catch (NumberFormatException e) {
            value = cBTRFCommConstants.cBTRFCommNA;
        }

        return new byte[]{(byte) value, 0x00};
    }


    private boolean btMyAdvertiser_start() {

        if (mBLEAdvertiseCallback == null) {
            mBLEAdvertiseCallback = new AdvertiseCallback() {
                @Override
                public void onStartSuccess(AdvertiseSettings settingsInEffect) {
                    Log.i(TAG, "LE Advertise Started.");
                }

                @Override
                public void onStartFailure(int errorCode) {
                    Log.w(TAG, "LE Advertise Failed: " + errorCode);
                }
            };
        }

        if (mAdvertiseSettings == null) {
            mAdvertiseSettings = new AdvertiseSettings.Builder()
                    .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_POWER)
                    .setConnectable(false)
                    .setTimeout(0)
                    .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_LOW)
                    .build();
        }

        if (mAdvertiseData == null) {
            byte[] fooData = buildTmpPacket();

            mAdvertiseData = new AdvertiseData.Builder()
                    .setIncludeDeviceName(true)
                    .setIncludeTxPowerLevel(true)
                    .addServiceUuid(cBTRFCommConstants.BTC_UUID_OF_DATA)
                    .addServiceData(cBTRFCommConstants.BTC_UUID_OF_DATA, fooData)
                    .build();
        }

        mBAdvert.startAdvertising(mAdvertiseSettings, mAdvertiseData, mBLEAdvertiseCallback);
        return true;
    }

    private boolean btMyAdvertiser_stop() {

        if ((mBAdvert != null) && (mBLEAdvertiseCallback !=null))
            mBAdvert.stopAdvertising(mBLEAdvertiseCallback);

        return true;
    }

    private boolean btMyAdvertiser_cleanup() {

        this.btMyAdvertiser_stop();

        mAdvertiseSettings = null;
        mAdvertiseData = null;

        return true;
    }



    public btMyAdvertiser() {

        if (!BluetoothAdapter.getDefaultAdapter().isMultipleAdvertisementSupported()) {
            return;
        }

        mAdvertDataUID = ParcelUuid.fromString(cBTRFCommConstants.USER_CHARACTERISTIC);
        mAdvertOfServiceUID = ParcelUuid.fromString(cBTRFCommConstants.APP_CHARACTERISTIC);

        mBAdvert = BluetoothAdapter.getDefaultAdapter().getBluetoothLeAdvertiser();

        btMyAdvertiser_start();

    }

    public void btMyAdvert_restart() {
        btMyAdvertenadisa(false, true);
        btMyAdvertenadisa(true, false);
    }


    public void btMyAdvertenadisa(boolean start, boolean cleanup) {

        if (start) {

            if (cleanup) {
                btMyAdvertiser_cleanup();
            }

            btMyAdvertiser_start();
        } else {

            btMyAdvertiser_stop();

            if (cleanup) {
                btMyAdvertiser_cleanup();
            }
        }

    }

}