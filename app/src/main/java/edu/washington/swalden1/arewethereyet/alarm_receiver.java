package edu.washington.swalden1.arewethereyet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

public class alarm_receiver extends BroadcastReceiver {
    public alarm_receiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String phoneNumber = intent.getStringExtra(MainActivity.numbar);
        String msg = intent.getStringExtra(MainActivity.alert);

        Toast.makeText(context, "Sending message to " + MainActivity.formatNumber(phoneNumber), Toast.LENGTH_SHORT).show();
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, msg, null, null);
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}
