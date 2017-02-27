package edu.washington.swalden1.arewethereyet;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private boolean started = false;
    public static final String alert = "edu.washington.swalden1.arewethereyet.message";
    public static final String numbar = "edu.washington.swalden1.arewethereyet.number";
    public static final String TAG = "[arewethereyet]";
    private PendingIntent alarmIntent;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AlarmManager am = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);

        final EditText msg = (EditText) findViewById(R.id.message);
        final EditText num = (EditText) findViewById(R.id.phone_number);
        final EditText val = (EditText) findViewById(R.id.interval);
        final Button start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (started) {
                    if (am != null) {
                        am.cancel(alarmIntent);
                        start.setText("Start");
                    }
                    // stop sending messages
                } else {
                    // start sending messages
                    String str_interval = val.getText().toString();
                    int interval;
                    try {
                        interval = Integer.parseInt(str_interval, 10);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, e.getMessage());
                        return;
                    }
                    String message = msg.getText().toString();
                    if (message.length() < 1) {
                        Toast.makeText(MainActivity.this, "Message must be at least one character", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String phone_number = num.getText().toString();
                    try {
                        long num_test = Long.parseLong(phone_number, 10);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (interval < 0) {
                        Toast.makeText(MainActivity.this, "Interval must be a non-negative Integer value", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String formatted_number = formatNumber(phone_number);
                    if (formatted_number == null) {
                        Toast.makeText(MainActivity.this, "Invalid Phone Number: " + phone_number, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String formatted_number = phone_number;

                    i = new Intent(MainActivity.this, alarm_receiver.class);
                    i.putExtra(MainActivity.numbar, phone_number);
                    i.putExtra(MainActivity.alert, message);
                    alarmIntent = PendingIntent.getBroadcast(MainActivity.this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
                    am.setRepeating(am.RTC, System.currentTimeMillis(), 1000 * 60 * interval, alarmIntent);
                    start.setText("Stop");
                }

                // this must go at the end of this method
                started = !started;
            }
        });
    }

    public static String formatNumber(String pNumber) {
        StringBuilder result = new StringBuilder();
        if (pNumber.length() == 10) {
            result.append('(');
            for (int i = 0; i < 3; i++) {
                result.append(pNumber.charAt(i));
            }
            result.append(')');
            result.append(' ');
        }
        if (result.length() > 0 || pNumber.length() == 7) {
            for (int i = 0; i < 7; i++) {
                if (i == 3) {
                    result.append('-');
                }
                result.append(pNumber.charAt(result.length() > 0? i+3 : i));
            }
        } else if (pNumber.length() == 4) { // emulator port
            return pNumber;
        } else {
            return null;
        }
        return result.toString();
    }
}
