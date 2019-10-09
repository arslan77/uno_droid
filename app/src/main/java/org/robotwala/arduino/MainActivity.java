package org.robotwala.arduino;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.physicaloid.lib.Boards;
import com.physicaloid.lib.Physicaloid;
import com.physicaloid.lib.programmer.avr.UploadErrors;

import java.io.FileOutputStream;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    Physicaloid mPhysicaloid;
    TextView logsText;

    public void addLog(final String message) {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String newMessage = logsText.getText() + "\n" + message;
                    logsText.setText(newMessage);
                }
            });
        } catch (Exception e) {
            int i = 0;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        logsText = (TextView) findViewById(R.id.logs);

        setSupportActionBar(toolbar);
        mPhysicaloid = new Physicaloid(this);
        Uri uri = getIntent().getData();
        if (uri != null) {
            String hex = uri.getQueryParameter("hex");
            if (hex != null) {

                String filename = "sample.hex";
                byte[] fileContents = Base64.decode(hex, Base64.DEFAULT);
                FileOutputStream outputStream;

                try {
                    outputStream = openFileOutput(filename, MODE_PRIVATE);
                    outputStream.write(fileContents);
                    outputStream.close();

                    ImageButton btn = (ImageButton) findViewById(R.id.uploadBtn);
                    onUploadClick(btn);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }


    public void onUploadClick(final View view) {
//        final Context context = this;
        mPhysicaloid.upload(Boards.ARDUINO_UNO, this.getFilesDir() + "/sample.hex", new Physicaloid.UploadCallBack() {
            @Override
            public void onPreUpload() {
                addLog("Start");
            }

            @Override
            public void onUploading(int value) {
                addLog("Uploading - " + value + "%");
            }

            @Override
            public void onPostUpload(boolean success) {
                if (success) {
                    addLog("Success");
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);

                } else {
                    addLog("Failed");
                    addLog("Please Try Again");
                }
            }

            @Override
            public void onCancel() {
                addLog("Canceled");
            }

            @Override
            public void onError(UploadErrors err) {

                addLog("Error Occured - " + err.toString());
                addLog("Please make sure wires are connected properly and try again.");
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
