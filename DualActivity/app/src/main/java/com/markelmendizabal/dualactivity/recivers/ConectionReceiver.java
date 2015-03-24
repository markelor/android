package com.markelmendizabal.dualactivity.recivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

public class ConectionReceiver extends BroadcastReceiver {
    private String RECEIVER="RECEIVER";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if(intent.getAction()==Intent.ACTION_AIRPLANE_MODE_CHANGED){
            Log.d(RECEIVER,"ACTION:"+intent.getAction());

        }else if(intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)){
            Log.d(RECEIVER,"ACTION:"+intent.getAction());

        }
        Log.d(RECEIVER,"ConectionReceiver onReactive()");



    }
}
