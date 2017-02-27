package edu.washington.swalden1.arewethereyet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class alarm_receiver extends BroadcastReceiver {
    public alarm_receiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving an Intent broadcast.

        Toast.makeText(context, intent.getStringExtra(MainActivity.alert), Toast.LENGTH_SHORT).show();
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}
