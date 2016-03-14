package tiko.tamk.fi.callme;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.MissingFormatArgumentException;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        String prefix = getIntent().getExtras().getString("prefix");
        TextView phoneNumber = (TextView) findViewById(R.id.editText);
        phoneNumber.setText(prefix);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        TextView phoneNumber = (TextView) findViewById(R.id.editText);
        intent.putExtra("number", phoneNumber.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
