package com.markelmendizabal.dualactivity.recivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TelephonyReceiver extends BroadcastReceiver {
    private String RECEIVER="RECEIVER";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        if(intent.getAction()==Intent.ACTION_AIRPLANE_MODE_CHANGED){
            Log.d(RECEIVER, "ACTION:" + intent.getAction());

        }
    }
}
