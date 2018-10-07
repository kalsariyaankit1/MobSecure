package com.cenet.mobsecure;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ANKIT on 8/31/2018.
 */

public class SMSReceiver extends BroadcastReceiver {
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SMSBroadcastReceiver";
    private static final int REQUEST_ENABLE_BT = 0;

    //lock Screen
    DevicePolicyManager deviceManger;
    ActivityManager activityManager;
    ComponentName compName;

    AudioManager audioManager;
    WifiManager wifiManager;
    TelephonyManager telephonyManager;
    BluetoothAdapter bluetoothAdapter;

    String nor,sil,loc,won,woff,rout,lock,don,doff,bon,boff;


    @Override
    public void onReceive(Context context, Intent intent) {
        //-Toast.makeText(context,,Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Intent recieved: " + intent.getAction());

        audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        deviceManger = (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        compName = new ComponentName(context, MyAdmin.class);

        if (intent.getAction().equals(SMS_RECEIVED)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[])bundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                }
                String sms = messages[0].getMessageBody();

                nor = Common.getPreferenceString(context,"norm","");
                sil = Common.getPreferenceString(context,"sil","");
                loc = Common.getPreferenceString(context,"loc","");
                won = Common.getPreferenceString(context,"wifion","");
                woff = Common.getPreferenceString(context,"wifioff","");
                rout = Common.getPreferenceString(context,"rout","");
                lock = Common.getPreferenceString(context,"lock","");
                don = Common.getPreferenceString(context,"don","");
                doff = Common.getPreferenceString(context,"doff","");
                bon = Common.getPreferenceString(context,"bon","");
                boff = Common.getPreferenceString(context,"boff","");
                if(sms.equalsIgnoreCase(nor)) {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }
                if(sms.equalsIgnoreCase(sil)) {
                    audioManager.setRingerMode(1);
                }
                if(sms.equalsIgnoreCase(lock)) {
                    boolean active = deviceManger.isAdminActive(compName);
                    if (active) {
                        deviceManger.lockNow();
                    }
                }
                if(sms.equalsIgnoreCase(won)) {
                    wifiManager.setWifiEnabled(true);
                }
                if(sms.equalsIgnoreCase(woff)) {
                    wifiManager.setWifiEnabled(false);
                }
                if(sms.equalsIgnoreCase(rout)) {
                    //Ring out loud
                }
                if(sms.equalsIgnoreCase(don)) {

                }
                if(sms.equalsIgnoreCase(doff)) {

                }
                if(sms.equalsIgnoreCase(bon)) {
                    if (!bluetoothAdapter.isEnabled()) {
                        bluetoothAdapter.enable();
                    }
                }
                if(sms.equalsIgnoreCase(boff)) {
                    bluetoothAdapter.disable();
                }
            }
        }
    }
}
