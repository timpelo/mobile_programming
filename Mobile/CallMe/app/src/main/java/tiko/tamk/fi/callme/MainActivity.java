package tiko.tamk.fi.callme;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_CODE = 10;
    private String phoneNumber = "0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void callMe(View view) {
        String tel = "tel:" + phoneNumber;
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(tel));
        startActivity(intent);
    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("prefix", "+358");
        startActivityForResult(intent, REQUEST_CODE);
        Log.d("PhoneApp", "toSettings");
    }

    public void openBrowser(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int  resultCode, Intent data) {
        Log.d("PhoneApp", "result arrived");
        if(requestCode == REQUEST_CODE) {
            Log.d("PhoneApp", "request code ok");
            if(resultCode == RESULT_OK) {
                Log.d("PhoneApp", "result code ok");
                String number = data.getExtras().getString("number");
                phoneNumber = number;
            }
        }
    }
}
