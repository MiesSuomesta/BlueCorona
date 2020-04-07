package com.lja.bluecorona;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.os.ParcelUuid;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class btMyScanner
{
    private final static String TAG = btMyScanner.class.getSimpleName();

    private BluetoothLeScanner mBluetoothLeScanner = null;
    public  btMyScannerCallback mBLEScanCallback    = null;
    private ArrayList<BTdevice> mBLeDevices        = null;

    public btMyScanner() {

        mBLEScanCallback = new btMyScannerCallback(this);

        mBluetoothLeScanner = BluetoothAdapter
                .getDefaultAdapter()
                .getBluetoothLeScanner();

        mBLeDevices = new ArrayList<BTdevice>();

    }

    public void addBTDevice(BTdevice device) {

        for (BTdevice ldev : mBLeDevices) {
            boolean exists_already = ldev.compare(device);

            if (exists_already) {
                // Update RSSI
                ldev.iRSSI = device.iRSSI;
                return;
            }
        }
        mBLeDevices.add(device);
    }

    public void startScanner() {
        mBluetoothLeScanner.startScan(mBLEScanCallback);
    }

    public void stopScanner() {
        mBluetoothLeScanner.stopScan(mBLEScanCallback);
    }

    public ArrayList<BTdevice> getBTLEDeviceScanResults() {
        if (mBLeDevices == null)
            return null;
        return mBLeDevices;
    }

    public void clearScanResults() {
        if (mBLeDevices == null)
            return;

        mBLeDevices.clear();
    }

    public BTdevice getDevice(int position) {
        if (mBLeDevices == null)
            return null;
        return mBLeDevices.get(position);
    }

    public void clear() {
        clearScanResults();
    }

    public int getCount() {
        if (mBLeDevices == null)
            return -1;
        return mBLeDevices.size();
    }

    public Object getItem(int i) {
        if (mBLeDevices == null)
            return null;
        return mBLeDevices.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

}
