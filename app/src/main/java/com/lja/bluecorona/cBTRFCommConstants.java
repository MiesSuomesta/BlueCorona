package com.lja.bluecorona;

import android.os.ParcelUuid;

import java.util.HashMap;

public class cBTRFCommConstants {

    public static final int cBTRFCommNA          = -100;
    public static final int cBTRFCommUserNotSet  = 0;
    public static final int cBTRFCommUserOK      = 1;
    public static final int cBTRFCommUserNotSure = 2;
    public static final int cBTRFCommUserSick    = 3;

    public static       int cBTRFCommLocalUser   = cBTRFCommNA;

    public static String[] cBTRFSicknessStateStrings =
            { "No data, be careful", "Situation OK", "Someone maybe sick, beware", "Someone is sick" };

    public static String[]  cBTRFSignalStrengthStrings =
            { "Too close, beware", "Bit too close", "Okay", "This is nice", "Very Good" };
    public static Integer[] cBTRFSignalStrengthBorders =
            { -30,       -67,         -70,    -80,           -90 };



    public static HashMap<Integer , String> hmEBTLEDescriptions = new HashMap();

    public static final Integer EBLE_FLAGS           = 0x01;//«Flags»	Bluetooth Core Specification:
    public static final Integer EBLE_16BitUUIDInc    = 0x02;//«Incomplete List of 16-bit Service Class UUIDs»	Bluetooth Core Specification:
    public static final Integer EBLE_16BitUUIDCom    = 0x03;//«Complete List of 16-bit Service Class UUIDs»	Bluetooth Core Specification:
    public static final Integer EBLE_32BitUUIDInc    = 0x04;//«Incomplete List of 32-bit Service Class UUIDs»	Bluetooth Core Specification:
    public static final Integer EBLE_32BitUUIDCom    = 0x05;//«Complete List of 32-bit Service Class UUIDs»	Bluetooth Core Specification:
    public static final Integer EBLE_128BitUUIDInc   = 0x06;//«Incomplete List of 128-bit Service Class UUIDs»	Bluetooth Core Specification:
    public static final Integer EBLE_128BitUUIDCom   = 0x07;//«Complete List of 128-bit Service Class UUIDs»	Bluetooth Core Specification:
    public static final Integer EBLE_SHORTNAME       = 0x08;//«Shortened Local Name»	Bluetooth Core Specification:
    public static final Integer EBLE_LOCALNAME       = 0x09;//«Complete Local Name»	Bluetooth Core Specification:
    public static final Integer EBLE_TXPOWERLEVEL    = 0x0A;//«Tx Power Level»	Bluetooth Core Specification:
    public static final Integer EBLE_DEVICECLASS     = 0x0D;//«Class of Device»	Bluetooth Core Specification:
    public static final Integer EBLE_SIMPLEPAIRHASH  = 0x0E;//«Simple Pairing Hash C»	Bluetooth Core Specification:​«Simple Pairing Hash C-192»	​Core Specification Supplement, Part A, section 1.6
    public static final Integer EBLE_SIMPLEPAIRRAND  = 0x0F;//«Simple Pairing Randomizer R»	Bluetooth Core Specification:​«Simple Pairing Randomizer R-192»	​Core Specification Supplement, Part A, section 1.6
    public static final Integer EBLE_DEVICEID        = 0x10;//«Device ID»	Device ID Profile v1.3 or later,«Security Manager TK Value»	Bluetooth Core Specification:
    public static final Integer EBLE_SECURITYMANAGER = 0x11;//«Security Manager Out of Band Flags»	Bluetooth Core Specification:
    public static final Integer EBLE_SLAVEINTERVALRA = 0x12;//«Slave Connection Interval Range»	Bluetooth Core Specification:
    public static final Integer EBLE_16BitSSUUID     = 0x14;//«List of 16-bit Service Solicitation UUIDs»	Bluetooth Core Specification:
    public static final Integer EBLE_128BitSSUUID    = 0x15;//«List of 128-bit Service Solicitation UUIDs»	Bluetooth Core Specification:
    public static final Integer EBLE_SERVICEDATA     = 0x16;//«Service Data»	Bluetooth Core Specification:​«Service Data - 16-bit UUID»	​Core Specification Supplement, Part A, section 1.11
    public static final Integer EBLE_PTADDRESS       = 0x17;//«Public Target Address»	Bluetooth Core Specification:
    public static final Integer EBLE_RTADDRESS       = 0x18;;//«Random Target Address»	Bluetooth Core Specification:
    public static final Integer EBLE_APPEARANCE      = 0x19;//«Appearance»	Bluetooth Core Specification:
    public static final Integer EBLE_DEVADDRESS      = 0x1B;//«​LE Bluetooth Device Address»	​Core Specification Supplement, Part A, section 1.16
    public static final Integer EBLE_LEROLE          = 0x1C;//«​LE Role»	​Core Specification Supplement, Part A, section 1.17
    public static final Integer EBLE_PAIRINGHASH     = 0x1D;//«​Simple Pairing Hash C-256»	​Core Specification Supplement, Part A, section 1.6
    public static final Integer EBLE_PAIRINGRAND     = 0x1E;//«​Simple Pairing Randomizer R-256»	​Core Specification Supplement, Part A, section 1.6
    public static final Integer EBLE_32BitSSUUID     = 0x1F;//​«List of 32-bit Service Solicitation UUIDs»	​Core Specification Supplement, Part A, section 1.10
    public static final Integer EBLE_32BitSERDATA    = 0x20;//​«Service Data - 32-bit UUID»	​Core Specification Supplement, Part A, section 1.11
    public static final Integer EBLE_128BitSERDATA   = 0x21;//​«Service Data - 128-bit UUID»	​Core Specification Supplement, Part A, section 1.11
    public static final Integer EBLE_SECCONCONF      = 0x22;//​«​LE Secure Connections Confirmation Value»	​Core Specification Supplement Part A, Section 1.6
    public static final Integer EBLE_SECCONRAND      = 0x23;//​​«​LE Secure Connections Random Value»	​Core Specification Supplement Part A, Section 1.6​
    public static final Integer EBLE_3DINFDATA       = 0x3D;//​​«3D Information Data»	​3D Synchronization Profile, v1.0 or later
    public static final Integer EBLE_MANDATA         = 0xFF;//«Manufacturer Specific Data»	Bluetooth Core Specification:

    static {
        hmEBTLEDescriptions.put(EBLE_FLAGS, "«Flags»     Bluetooth Core Specification:");
        hmEBTLEDescriptions.put(EBLE_16BitUUIDInc, "«Incomplete List of 16-bit Service Class UUIDs»      Bluetooth Core Specification:");
        hmEBTLEDescriptions.put(EBLE_16BitUUIDCom, "«Complete List of 16-bit Service Class UUIDs»        Bluetooth Core Specification:");
        hmEBTLEDescriptions.put(EBLE_32BitUUIDInc, "«Incomplete List of 32-bit Service Class UUIDs»      Bluetooth Core Specification:");
        hmEBTLEDescriptions.put(EBLE_32BitUUIDCom, "«Complete List of 32-bit Service Class UUIDs»        Bluetooth Core Specification:");
        hmEBTLEDescriptions.put(EBLE_128BitUUIDInc, "«Incomplete List of 128-bit Service Class UUIDs»    Bluetooth Core Specification:");
        hmEBTLEDescriptions.put(EBLE_128BitUUIDCom, "«Complete List of 128-bit Service Class UUIDs»      Bluetooth Core Specification:");
        hmEBTLEDescriptions.put(EBLE_SHORTNAME, "«Shortened Local Name»  Bluetooth Core Specification:");
        hmEBTLEDescriptions.put(EBLE_LOCALNAME, "«Complete Local Name»   Bluetooth Core Specification:");
        hmEBTLEDescriptions.put(EBLE_TXPOWERLEVEL, "«Tx Power Level»     Bluetooth Core Specification:");
        hmEBTLEDescriptions.put(EBLE_DEVICECLASS, "«Class of Device»     Bluetooth Core Specification:");
        hmEBTLEDescriptions.put(EBLE_SIMPLEPAIRHASH, "«Simple Pairing Hash C»    Bluetooth Core Specification:«Simple Pairing Hash C-192»        Core Specification Supplement, Part A, section 1.6");
        hmEBTLEDescriptions.put(EBLE_SIMPLEPAIRRAND, "«Simple Pairing Randomizer R»      Bluetooth Core Specification:«Simple Pairing Randomizer R-192»  Core Specification Supplement, Part A, section 1.6");
        hmEBTLEDescriptions.put(EBLE_DEVICEID, "«Device ID»      Device ID Profile v1.3 or later,«Security Manager TK Value»     Bluetooth Core Specification:");
        hmEBTLEDescriptions.put(EBLE_SECURITYMANAGER, "«Security Manager Out of Band Flags»      Bluetooth Core Specification:");
        hmEBTLEDescriptions.put(EBLE_SLAVEINTERVALRA, "«Slave Connection Interval Range» Bluetooth Core Specification:");
        hmEBTLEDescriptions.put(EBLE_16BitSSUUID, "«List of 16-bit Service Solicitation UUIDs»   Bluetooth Core Specification:");
        hmEBTLEDescriptions.put(EBLE_128BitSSUUID, "«List of 128-bit Service Solicitation UUIDs» Bluetooth Core Specification:");
        hmEBTLEDescriptions.put(EBLE_SERVICEDATA, "«Service Data»        Bluetooth Core Specification:«Service Data - 16-bit UUID»       Core Specification Supplement, Part A, section 1.11");
        hmEBTLEDescriptions.put(EBLE_PTADDRESS, "«Public Target Address» Bluetooth Core Specification:");
        hmEBTLEDescriptions.put(EBLE_RTADDRESS, "«Random Target Address»        Bluetooth Core Specification:");
        hmEBTLEDescriptions.put(EBLE_APPEARANCE, "«Appearance»   Bluetooth Core Specification:");
        hmEBTLEDescriptions.put(EBLE_DEVADDRESS, "«LE Bluetooth Device Address»  Core Specification Supplement, Part A, section 1.16");
        hmEBTLEDescriptions.put(EBLE_LEROLE, "«LE Role»  Core Specification Supplement, Part A, section 1.17");
        hmEBTLEDescriptions.put(EBLE_PAIRINGHASH, "«Simple Pairing Hash C-256»   Core Specification Supplement, Part A, section 1.6");
        hmEBTLEDescriptions.put(EBLE_PAIRINGRAND, "«Simple Pairing Randomizer R-256»     Core Specification Supplement, Part A, section 1.6");
        hmEBTLEDescriptions.put(EBLE_32BitSSUUID, "«List of 32-bit Service Solicitation UUIDs»   Core Specification Supplement, Part A, section 1.10");
        hmEBTLEDescriptions.put(EBLE_32BitSERDATA, "«Service Data - 32-bit UUID» Core Specification Supplement, Part A, section 1.11");
        hmEBTLEDescriptions.put(EBLE_128BitSERDATA, "«Service Data - 128-bit UUID»       Core Specification Supplement, Part A, section 1.11");
        hmEBTLEDescriptions.put(EBLE_SECCONCONF, "«LE Secure Connections Confirmation Value»     Core Specification Supplement Part A, Section 1.6");
        hmEBTLEDescriptions.put(EBLE_SECCONRAND, "«LE Secure Connections Random Value»   Core Specification Supplement Part A, Section 1.6");
        hmEBTLEDescriptions.put(EBLE_3DINFDATA, "«3D Information Data»   3D Synchronization Profile, v1.0 or later");
        hmEBTLEDescriptions.put(EBLE_MANDATA, "«Manufacturer Specific Data»      Bluetooth Core Specification:");
    };

    private static HashMap<String, String> attributes = new HashMap();
    public static String APP_CHARACTERISTIC = "4a32d535-e198-4128-9656-a78c54d25072";
    public static String USER_CHARACTERISTIC = "a18e5a7b-a086-4fc2-a789-92308302204c";

    public static ParcelUuid BTC_UUID_OF_SERVICE =
            ParcelUuid.fromString(cBTRFCommConstants.APP_CHARACTERISTIC);
    public static ParcelUuid BTC_UUID_OF_DATA    =
            ParcelUuid.fromString(cBTRFCommConstants.USER_CHARACTERISTIC);


    static {
        // Sample Services.
        attributes.put(APP_CHARACTERISTIC,  "User Healt Service");
        attributes.put(USER_CHARACTERISTIC, "User Healt value");
        // Sample Characteristics.
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

    public static int setBTRFCommLocalUserSicnesslvl(int lvl) {
        if ((lvl >= 0) && (lvl < cBTRFSicknessStateStrings.length))
            cBTRFCommLocalUser = lvl;

        return cBTRFCommLocalUser;
    }

    public static byte[] getBTRFCommLocalUserSicnesslvl() {
        byte[] foo = { (byte)cBTRFCommLocalUser };
        return foo;
    }

    private static String getSignalStrenghtStrAt(int lvl) {
        if ((lvl >= 0) && (lvl < cBTRFSignalStrengthStrings.length))
            return cBTRFSignalStrengthStrings[lvl];

        return cBTRFSignalStrengthStrings[0];
    }

    private static int getSignalStrenghtBorderAt(int lvl) {
        if ((lvl >= 0) && (lvl < cBTRFSignalStrengthBorders.length))
            return cBTRFSignalStrengthBorders[lvl];

        return cBTRFSignalStrengthBorders[ cBTRFSignalStrengthBorders.length-1 ];
    }

    public static String getSignalStrenghtStr(int pRSSI) {
        int i = cBTRFSignalStrengthBorders.length - 1;
        int iRSSI = 0;
        while(i>=0)
        {
            iRSSI = getSignalStrenghtBorderAt(i);
            if (pRSSI < iRSSI)
                break;
            i--;
        }
        return getSignalStrenghtStrAt(i);
    }

}
