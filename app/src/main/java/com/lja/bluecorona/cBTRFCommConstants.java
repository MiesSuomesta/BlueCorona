package com.lja.bluecorona;

import java.util.UUID;

public interface cBTRFCommConstants {
    public static final String cBTRFCommUIDString =
                new String("BluetoothRFCommCoronaAlarm");
    public static final UUID cBTRFCommUID =
                UUID.fromString(cBTRFCommUIDString);

    public static final int cBTRFCommUserNotSet  = 0;
    public static final int cBTRFCommUserSick    = 1;
    public static final int cBTRFCommUserOK      = 2;
    public static final int cBTRFCommUserNotSure = 3;

}
