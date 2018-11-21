package com.cenet.mobsecure;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class tracker1 extends BroadcastReceiver {
    Context context;
    SmsManager sms;
    TelephonyManager tm;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        String var3 = Common.getPreferenceString(context, "sim", "");
        String var4 = Common.getPreferenceString(context, "regno", "");
        tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        sms = SmsManager.getDefault();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        String var6 = tm.getSimSerialNumber();
        if(var3.equals(var6)) {
            Log.w("Serial",var6);
        }
        else {
            sms.sendTextMessage(var4,(String)null,"Your phone is stolen by this user.",(PendingIntent)null,(PendingIntent)null);
        }
    }
}
