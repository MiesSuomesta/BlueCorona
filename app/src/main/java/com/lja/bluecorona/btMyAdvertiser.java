package com.lja.bluecorona;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.os.ParcelUuid;
import android.util.Log;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class btMyAdvertiser {
    private final static String TAG = btMyAdvertiser.class.getSimpleName();

    private BluetoothLeScanner mBluetoothLeScanner = null;
    public  BluetoothLeAdvertiser mBAdvert = null;
    private AdvertiseSettings mAdvertiseSettings = null;
    private AdvertiseData mAdvertiseData = null;
    public  UUID mAdvertOfServiceUID = null;


    /* Callback to handle things */
    private AdvertiseCallback mBLEAdvertiseCallback = null;
    public  ScanCallback      mBLEScanCallback      = null;
    ;

    private ScanCallback mBLEAdScanCallback = null;

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

        if (mBLEScanCallback == null) {
            mBLEScanCallback = new ScanCallback() {
                @Override
                public void onScanResult(int callbackType, ScanResult result) {
                    super.onScanResult(callbackType, result);

                    if (
                            (result == null) ||
                            ((result != null) && (result.getDevice() == null))
                       )
                    {
                        return;
                    }

                    Log.e("BLE", "Discovery onScanResult: " + result);
                }

                ;

                @Override
                public void onBatchScanResults(List<ScanResult> results) {
                    super.onBatchScanResults(results);
                }

                @Override
                public void onScanFailed(int errorCode) {
                    Log.i("BLE", "Discovery onScanFailed: " + errorCode);
                    super.onScanFailed(errorCode);
                }
            };
        }

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
                    .setConnectable(true)
                    .setTimeout(0)
                    .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_LOW)
                    .build();
        }

        if (mAdvertiseData == null) {
            ParcelUuid foo = ParcelUuid.fromString(cBTRFCommConstants.USER_CHARACTERISTIC);

            mAdvertiseData = new AdvertiseData.Builder()
                    .setIncludeDeviceName(true)
                    .setIncludeTxPowerLevel(true)
                    .addServiceUuid(foo)
                    .addServiceData(foo, buildTmpPacket())
                    .build();
        }

        mBAdvert.startAdvertising(mAdvertiseSettings, mAdvertiseData, mBLEAdvertiseCallback);
        mBluetoothLeScanner.startScan(mBLEScanCallback);
        return true;
    }

    private boolean btMyAdvertiser_stop() {

        if (mBAdvert != null)
            mBAdvert.stopAdvertising(mBLEAdvertiseCallback);

        if (mBluetoothLeScanner != null)
            mBluetoothLeScanner.stopScan(mBLEScanCallback);

        return true;
    }

    private boolean btMyAdvertiser_cleanup() {

        this.btMyAdvertiser_stop();

        mAdvertiseSettings = null;
        mAdvertiseData = null;
        mBLEAdvertiseCallback = null;

        return true;
    }



    public btMyAdvertiser() {

        if (!BluetoothAdapter.getDefaultAdapter().isMultipleAdvertisementSupported()) {
            return;
        }

        mAdvertOfServiceUID = UUID.fromString(cBTRFCommConstants.USER_CHARACTERISTIC);

        mBAdvert = BluetoothAdapter.getDefaultAdapter().getBluetoothLeAdvertiser();

        mBluetoothLeScanner = BluetoothAdapter
                .getDefaultAdapter()
                .getBluetoothLeScanner();

        btMyAdvertiser_start();

    }

    public void btMyAdvertRestart() {
        btMyAdvertenadisa(false, false);
        btMyAdvertenadisa(true, true);
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