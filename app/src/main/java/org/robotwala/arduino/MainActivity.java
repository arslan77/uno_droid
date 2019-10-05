package org.robotwala.arduino;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.physicaloid.lib.Boards;
import com.physicaloid.lib.Physicaloid;
import com.physicaloid.lib.Physicaloid.UploadCallBack;
import com.physicaloid.lib.programmer.avr.UploadErrors;
import com.physicaloid.lib.usb.driver.uart.ReadLisener;
import com.physicaloid.lib.usb.driver.uart.UartConfig;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    Physicaloid mPhysicaloid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mPhysicaloid = new Physicaloid(this);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                try {
                    mPhysicaloid.upload(Boards.POCKETDUINO, getResources().getAssets().open("SerialEchoback.PocketDuino.hex"), new UploadCallBack() {
                        @Override
                        public void onPreUpload() {
                            Snackbar.make(view, "Start", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }

                        @Override
                        public void onUploading(int value) {
                            Snackbar.make(view, "Uploading", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }

                        @Override
                        public void onPostUpload(boolean success) {
                            if(success) {

                                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            } else {
                                Snackbar.make(view, "Upload failed", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        }

                        @Override
                        public void onCancel() {

                            Snackbar.make(view, "Canceled", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }

                        @Override
                        public void onError(UploadErrors err) {
                            Snackbar.make(view, "Error Occured", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });
                } catch (RuntimeException e) {
//                    Log.e(TAG, e.toString());
                } catch (IOException e) {
//                    Log.e(TAG, e.toString());
                }
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
