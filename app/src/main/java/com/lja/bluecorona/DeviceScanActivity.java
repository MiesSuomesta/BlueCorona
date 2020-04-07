/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lja.bluecorona;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.lja.bluecorona.cBTRFCommConstants.getBTRFCommLocalUserSicnesslvl;

/**
 * Activity for scanning and displaying available Bluetooth LE devices.
 */
public class DeviceScanActivity extends ListActivity {
    private LeDeviceListAdapter mLeDeviceListAdapter;
    private BluetoothAdapter mBluetoothAdapter;
    private boolean mScanning;
    private boolean mQueriedDiscovery = false;
    private boolean mDiscoverySet = false;
    private Handler mHandler;
    public  int    mLocalUserSickness = -200;
    private LayoutInflater mInflator;

    private static int REQUEST_ENABLE_BT = 1;
    private static int REQUEST_ENABLE_DISC = 2;
    // Stops scanning after 30 seconds.
    private static long SCAN_PERIOD = 10000;
    private btMyAdvertiser mBTAdvert = null;
    private btMyScanner mBTScanner = null;

    private void setupDiscovery() {
        boolean bDiscoveringSetting = mBluetoothAdapter.isDiscovering();

        if (bDiscoveringSetting)
            return;

        if (mDiscoverySet)
            return;

        if (mQueriedDiscovery)
            return;

        mBluetoothAdapter.cancelDiscovery();

        Intent MDisc = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        MDisc.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 60);
        startActivityForResult(MDisc, REQUEST_ENABLE_DISC);
        mQueriedDiscovery = true;
        mDiscoverySet = false;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler = new Handler();

        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }

        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        mInflator = DeviceScanActivity.this.getLayoutInflater();

        mBTAdvert = new btMyAdvertiser();

        mBTAdvert.btMyAdvertenadisa(true, false);

        mBTScanner = new btMyScanner();
        mBTScanner.startScanner();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        if (!mScanning) {
            menu.findItem(R.id.menu_stop).setVisible(false);
            menu.findItem(R.id.menu_scan).setVisible(true);
            menu.findItem(R.id.menu_refresh).setActionView(null);
        } else {
            menu.findItem(R.id.menu_stop).setVisible(true);
            menu.findItem(R.id.menu_scan).setVisible(false);
            menu.findItem(R.id.menu_refresh).setActionView(
                    R.layout.actionbar_indeterminate_progress);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_scan:
                mLeDeviceListAdapter.clear();
                scanLeDevice(true);
                break;
            case R.id.menu_stop:
                scanLeDevice(false);
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Ensures Bluetooth is enabled on the device.  If Bluetooth is not currently enabled,
        // fire an intent to display a dialog asking the user to grant permission to enable it.
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        // Initializes list view adapter.
        mLeDeviceListAdapter = new LeDeviceListAdapter();
        setListAdapter(mLeDeviceListAdapter);
        scanLeDevice(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_CANCELED) {
            mBluetoothAdapter.cancelDiscovery();
            finish();
            return;
        }

        if (requestCode == REQUEST_ENABLE_DISC) {
            mQueriedDiscovery = false;
            mDiscoverySet = true;
            mBluetoothAdapter.cancelDiscovery();
            mBluetoothAdapter.startDiscovery();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBluetoothAdapter.cancelDiscovery();
        scanLeDevice(false);
        mLeDeviceListAdapter.clear();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        final BTdevice device = mLeDeviceListAdapter.getDevice(position);
        if (device == null) return;
        final Intent intent = new Intent(this, DeviceControlActivity.class);
        intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_NAME, device.getAndroidBTDevice().getName());
        intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_ADDRESS, device.getAndroidBTDevice().getAddress());
        // Localuser sickness level: to do
        intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_DATA, "" + this.mLocalUserSickness);
        scanLeDevice(false);
        startActivity(intent);
    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBTScanner.stopScanner();
                    mBTAdvert.btMyAdvertenadisa(false,true);
                    //setupDiscovery();
                    invalidateOptionsMenu();
                }
            }, SCAN_PERIOD);

            mScanning = true;
            if (mBTAdvert != null)
                mBTAdvert.btMyAdvertenadisa(true,true);

            mBTScanner.startScanner();

        } else {
            mScanning = false;
            if (mBTAdvert != null)
                mBTAdvert.btMyAdvertenadisa(false,true);

            mBTScanner.stopScanner();
            setupDiscovery();
        }

        invalidateOptionsMenu();
    }

        // Adapter for holding devices found through scanning.
    public class LeDeviceListAdapter extends BaseAdapter {

        public LeDeviceListAdapter() {
            super();

        }

        public BTdevice getDevice(int position) {
            return mBTScanner.getDevice(position);
        }

        public void clear() {
            mBTScanner.clearScanResults();
        }

        @Override
        public int getCount() {
            return mBTScanner.getCount();
        }

        @Override
        public Object getItem(int i) {
            return mBTScanner.getItem(i);
        }

        @Override
        public long getItemId(int i) {
            return mBTScanner.getItemId(i);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            // General ListView optimization code.
            if (view == null) {
                view = mInflator.inflate(R.layout.listitem_device, null);
                viewHolder = new ViewHolder();
                viewHolder.deviceAddress = (TextView) view.findViewById(R.id.device_address);
                viewHolder.deviceName = (TextView) view.findViewById(R.id.device_name);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            BTdevice device = (BTdevice) getItem(i);
            int sl = 0;
            final String deviceName = device.getAndroidBTDevice().getName();
            String deviceAddress = device.getAndroidBTDevice().getAddress();
            String dname = deviceName;

            boolean bNameOK = (deviceName != null     ) &&
                              (deviceName.length() > 0);

            sl = device.getSicnesslvl();

            if ( !bNameOK ) {
                dname = "Anon device " + (i+1) + " (" + deviceAddress + ")";
            }

            viewHolder.deviceName.setText(dname);

            String sRSSI = cBTRFCommConstants.getSignalStrenghtStr(device.iRSSI);
            viewHolder.deviceAddress.setText(cBTRFCommConstants.getSicnesslvlStr(sl) +
                    ", distance assertion: "+ sRSSI + " ("+ device.iRSSI + " dBm)" );

            return view;
        }
    }

    static class ViewHolder {
        TextView deviceName;
        TextView deviceAddress;
        String   remoteSicnesslevel;
    }
}
