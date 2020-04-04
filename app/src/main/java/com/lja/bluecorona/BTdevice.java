package com.lja.bluecorona;

import android.bluetooth.*;

public class BTdevice {



    BluetoothDevice mBTDevice = null;
    int iSicknesslevel = -1;
    int iRSSI;

    public BTdevice(BluetoothDevice device, int rssi) {
        mBTDevice = device;
        iRSSI = rssi;
    }

    public BluetoothDevice getAndroidBTDevice() {
        return mBTDevice;
    }


    public int setSicnesslvl(int lvl) {
        int rv = 1;

        if (lvl < cBTRFCommConstants.cBTRFSicknessStateStrings.length) {
            iSicknesslevel = lvl;
            rv = 0;
        }

        return rv;
    }

    public int getSicnesslvl() {
        return iSicknesslevel;
    }

}
