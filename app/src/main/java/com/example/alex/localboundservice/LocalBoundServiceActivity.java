package com.example.alex.localboundservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

public class LocalBoundServiceActivity extends Activity {

    /*
        Phase a) create a ServiceConnection class that contains the callback methods that will be called when this client connect to or deconnect from the Service
                -> method onServiceConnected()
                -> method onServiceDisconnected()
        Phase b) Declare an instance of the Service to which this client will bind
                -> this instance will be initializate in the onServiceConnected() method
                -> the initialization associate the Service to which we connected to this object
        Phase c) Modify the client code to bind to the Service
     */

    /*
        Phase b)
     */
    BoundService mService;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_bound_service);
    }

    @Override
    protected void onStart(){
        super.onStart();
        /*
            Phase c)
         */
        Intent intent = new Intent(this, BoundService.class);
        intent.putExtra("Message", "Activity Message");
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop(){
        super.onStop();
        // Unbind from the Service
        if(mBound){
            unbindService(mConnection);
            mBound = false;
        }
    }

    /*
        Phase a)
     */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            BoundService.MyLocalBinder myLocalBinder = (BoundService.MyLocalBinder) iBinder;
            mService = myLocalBinder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBound = false;
        }
    };

    public void showTime(View view){
        String currentTime = mService.getCurrentTime();
        TextView textView = (TextView) findViewById(R.id.myTextView);
        textView.setText(currentTime);
    }
}
