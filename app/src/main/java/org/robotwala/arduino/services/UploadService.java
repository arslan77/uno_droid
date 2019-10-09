package org.robotwala.arduino.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.physicaloid.lib.Boards;
import com.physicaloid.lib.Physicaloid;
import com.physicaloid.lib.programmer.avr.UploadErrors;

import org.robotwala.arduino.MainActivity;

import java.io.IOException;

import androidx.annotation.Nullable;

public class UploadService extends Service {
    int startMode;       // indicates how to behave if the service is killed
    IBinder binder;      // interface for clients that bind
    boolean allowRebind; // indicates whether onRebind should be used
    private static final String TAG = UploadService.class.getSimpleName();
    Physicaloid mPhysicaloid;


    @Override
    public void onCreate() {
        mPhysicaloid = new Physicaloid(this);
        // The service is being created
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // The service is starting, due to a call to startService()
        try {

        } catch (RuntimeException e) {
            Log.e(TAG, e.toString());
        }
        return Service.START_NOT_STICKY;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // A client is binding to the service with bindService()
        return null;
    }


    @Override
    public void onDestroy() {
        Toast.makeText(this, "Destroyed", Toast.LENGTH_LONG).show();

        // The service is no longer used and is being destroyed
    }
}