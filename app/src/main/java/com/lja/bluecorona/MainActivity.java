package com.lja.bluecorona;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays.*;

import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback;
import com.google.android.material.snackbar.Snackbar.*;

import static android.content.pm.PackageManager.*;
import android.os.Bundle;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
        implements ActivityCompat.OnRequestPermissionsResultCallback {

    public int                                   mUserSicknessLevel = cBTRFCommConstants.cBTRFCommUserNotSure;
    private BluetoothAdapter                     mBA = null;
    private BluetoothManager                     mBTM = null;
    private oBTRFCommBIDirectionalCommunications mBTRFComm = null;
    private HashMap<String, Integer>             mBTRFCommappPermissions = new HashMap<>(); // permission name, ID nro
    private HashMap<String, Integer>             mBTRFCommFoundStatuses = new HashMap<>(); // Mac, sickness status
    private static final int                     REQUEST_GPS_FINE = 1;
    private static final int                     REQUEST_GPS_COARSE = 2;
    private static final int                     REQUEST_BT = 4;
    private static final int                     REQUEST_BT_ADM = 8;
    private boolean mPermissionsOK =             false;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBTRFCommappPermissions.put(Manifest.permission.ACCESS_FINE_LOCATION,   REQUEST_GPS_FINE);
        mBTRFCommappPermissions.put(Manifest.permission.ACCESS_COARSE_LOCATION, REQUEST_GPS_COARSE);
        mBTRFCommappPermissions.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_BT);
        mBTRFCommappPermissions.put(Manifest.permission.READ_EXTERNAL_STORAGE,  REQUEST_BT_ADM);


        checkAndReqPermissions();

        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        mBTM = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);

        mBA = mBTM.getAdapter();

        if (mBA == null || !(mBA.isEnabled()))
        {
            Intent enaBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            this.startActivityForResult(enaBtIntent, 1);
        }


    }

    public static String[] addStringArray(String[] perms, String pNewPerm) {
        int length = perms.length;
        String[] result = new String[length +1];
        int i;
        for (i = 0; i < length; i++) {
            result[i] = perms[i];
        }
        result[i] = pNewPerm;
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean checkAndReqPermissions()
    {
        boolean rv = true;
        int mIntPermissions = 0;
        String[] perms= {};

        for(Map.Entry<String, Integer> perm : mBTRFCommappPermissions.entrySet())
        {
            String permName = perm.getKey();
            Integer permInt = perm.getValue();
            perms = addStringArray(perms, permName);
            mIntPermissions |= permInt;
        }

        getPermission(perms, mIntPermissions);

        return  rv;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void  getPermission(String[] permissionIDs, int permissionIntID)
    {
        ActivityCompat.requestPermissions(this,
                permissionIDs,
                permissionIntID);

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {


        int checkF  = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        int checkC  = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
        int checkbt = checkSelfPermission(Manifest.permission.BLUETOOTH);
        int checkbta = checkSelfPermission(Manifest.permission.BLUETOOTH_ADMIN);

        boolean bFineOK       = (checkF  == PERMISSION_GRANTED);
        boolean bCoarseOK     = (checkC  == PERMISSION_GRANTED);
        boolean bBTOK         = (checkbt  == PERMISSION_GRANTED);
        boolean bBTAOK        = (checkbta  == PERMISSION_GRANTED);

        mPermissionsOK  = (bFineOK || bCoarseOK);
        mPermissionsOK &= (bBTOK && bBTAOK);

    }


    public void startActivityForResult (Intent intent, int requestCode, Bundle options) {
        if (requestCode != 0)
        {
            mBA.enable();
            mBTRFComm = new oBTRFCommBIDirectionalCommunications(this, mBA);
            mBTRFComm.start();
        }

    }

    public synchronized int getUserSicknessLevel() {
        return mUserSicknessLevel;
    }

    public synchronized int setUserSicknessLevel(int tmp) {
        mUserSicknessLevel = tmp;
        return tmp;
    }

    public synchronized int getRemoteUserSicknessLevel(String pMacAddress) {
        int rv = mBTRFCommFoundStatuses.get(pMacAddress);
        return rv;
    }

    public synchronized int setRemoteUserSicknessLevel(String pMacAddress, int tmp) {
        mBTRFCommFoundStatuses.put(pMacAddress, tmp);
        return tmp;
    }

}
