package payal.Security_Evaluation;

import android.app.KeyguardManager;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.Context;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;

/**
 *  Author:  Payal Kothari
 */

public class CheckSetting {
    Context context;
    public CheckSetting(Context context){
        this.context = context;
    }


    public boolean isBluetoothOn(){
        BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
        if (bluetooth.isEnabled()) {
            return true;
        }else {
            return false;
        }
    }

    public boolean isWifiOn() {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled()) {
            return true;
        }else {
            return false;
        }
    }

    public boolean isPasswordSet(){
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        ContentResolver contentResolver = context.getContentResolver();
        boolean passwordSet =  keyguardManager.isKeyguardSecure();
        try
        {
            int v = Settings.Secure.getInt(contentResolver, Settings.Secure.LOCK_PATTERN_ENABLED);
            if(v == 1){
                passwordSet = true;
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return passwordSet;
    }

    public boolean OSVersion(){
        int OS_SDK_No = Build.VERSION.SDK_INT;
        if(OS_SDK_No > Build.VERSION_CODES.JELLY_BEAN) {  // Jelly Bean Version: 4.1 - 4.3.1
            return true;
        }else {
            return false;
        }
    }

    public boolean isGPSOn(){
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE );
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER )) {
            return true;
        }else {
            return false;
        }
    }

    public boolean isThirdPartyAppAllowed(){

        try {
            boolean isThirdPartyAppAllowed = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.INSTALL_NON_MARKET_APPS) == 1;
            if (isThirdPartyAppAllowed) {
                return true;
            } else {
                return false;
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
