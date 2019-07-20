package com.segfault.android.breadcrumbs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity {

    private Button mTripButton, mSearchButton;
    TelephonyManager mTelephonyManager;
    MyPhoneStateListener mPhoneStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTripButton = (Button) findViewById(R.id.go_on_trip);
        mSearchButton = (Button) findViewById(R.id.search);

        mTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ConfigureTripActivity.class);
                startActivity(intent);
            }
        });
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchMapActivity.class);
                startActivity(intent);
            }
        });

        //setContentView(R.layout.activity_main);
        /*boolean isConn = isConnected(getApplicationContext());
        isWifiConnected(getApplicationContext());

        TextView textView1;
        textView1 = findViewById(R.id.textview1);
        textView1.setText("Signal: " + valueOf(isConn));

        //mPhoneStateListener = new MyPhoneStateListener();
        //mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //mTelephonyManager.listen(mPhoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);*/
    }
    public static boolean isConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public static boolean isWifiConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return info != null && info.isConnected() && info.isAvailable();
    }
}
