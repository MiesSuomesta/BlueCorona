package com.lja.bluecorona;

import java.util.HashMap;
import java.util.HashMap;
import java.util.UUID;

public class cBTRFCommConstants {

    public static final int cBTRFCommUserNotSet  = 100;
    public static final int cBTRFCommUserOK      = 1;
    public static final int cBTRFCommUserNotSure = 2;
    public static final int cBTRFCommUserSick    = 3;

    public static String[] cBTRFSicknessStateStrings = { "No data, be careful", "Situation OK", "Someone maybe sick, beware", "Someone is sick" };

    private static HashMap<String, String> attributes = new HashMap();
    public static String HEART_RATE_MEASUREMENT = "00002a37-0000-1000-8000-00805f9b34fb";
    public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";

    static {
        // Sample Services.
        attributes.put("0000180d-0000-1000-8000-00805f9b34fb", "Heart Rate Service");
        attributes.put("0000180a-0000-1000-8000-00805f9b34fb", "Device Information Service");
        // Sample Characteristics.
        attributes.put(HEART_RATE_MEASUREMENT, "Heart Rate Measurement");
        attributes.put("00002a29-0000-1000-8000-00805f9b34fb", "Manufacturer Name String");
    }

    public static String lookup(String uuid, String defaultName) {
        String name = attributes.get(uuid);
        return name == null ? defaultName : name;
    }

    public static String getSicnesslvlStr(int lvl) {
        if ((lvl >= 0) && (lvl < cBTRFSicknessStateStrings.length))
            return cBTRFSicknessStateStrings[lvl];

        return cBTRFSicknessStateStrings[0];
    }


}
