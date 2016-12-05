package com.example.alex.localboundservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class BoundService extends Service {

    /*
        Phase a) Create the Binder for the communication
            -> It will be passed to the client to communicate with it
        Phase b) In the onBind method we must return a reference to the binder object

     */

     /*
        a.2) Create and initialize a Binder Object
     */
    private final IBinder myBinder = new MyLocalBinder();

    @Override
    public IBinder onBind(Intent intent) {

        Log.i(TAG, intent.getStringExtra("Message"));

        /*
            b.1) return the binder object to clients which bind to this service
         */
        return myBinder;
    }

    /*
        a.1) Create a Binder subclass within the current bound service instance
            -> In most cases the implementation consists only on creating a method that returns the instance of the bound service instance
            -> The defined methods can be called by clients
     */
    public class MyLocalBinder extends Binder{
        BoundService getService(){
            return BoundService.this;
        }
    }

    public String getCurrentTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss MM/dd/yyyy", Locale.ITALY);
        return dateFormat.format(new Date());
    }
}
