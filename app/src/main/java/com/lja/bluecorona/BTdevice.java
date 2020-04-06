package com.lja.bluecorona;

import android.bluetooth.*;

import java.util.List;

public class BTdevice {



    BluetoothDevice          mBTDevice            = null;
    List<btScanRecord>       mScanRedcords        = null;
    int                      iSicknesslevel       = -1;
    int                      iRSSI                = 0;

    public BTdevice(BluetoothDevice device, int rssi, byte[] record) {
        btScanRecordParser parse = new btScanRecordParser();
        mBTDevice = device;
        iRSSI = rssi;
        mScanRedcords = parse.parseScanRecord(record);
    }

    public BluetoothDevice getAndroidBTDevice() {
        return mBTDevice;
    }


    public int setSicnesslvl(int lvl) {
        int rv = 1;

        if ((lvl >= 0 ) && (lvl < cBTRFCommConstants.cBTRFSicknessStateStrings.length)) {
            iSicknesslevel = lvl;
            rv = 0;
        }

        return rv;
    }

    static int i = 0;

    public int getSicnesslvl() {
       return iSicknesslevel;
    }

    public boolean compare(BTdevice pDev) {
        String mymac   = this.getAndroidBTDevice().getAddress();
        String compmac = pDev.getAndroidBTDevice().getAddress();
        boolean rv = false;
//        boolean bRSSI = (this.iRSSI == pDev.iRSSI);
        boolean bMAC  = mymac.equals(compmac);

//        rv |= bRSSI;
        rv |= bMAC;

        return rv;
    }

}
