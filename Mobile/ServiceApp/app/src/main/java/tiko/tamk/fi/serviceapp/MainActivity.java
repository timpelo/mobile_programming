package tiko.tamk.fi.serviceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startService(View view) {
        Intent intent = new Intent(this, MyIntentService.class);
        TextView arraySize = (TextView) findViewById(R.id.arraySizeText);
        TextView valueRange = (TextView) findViewById(R.id.valueRangeText);
        TextView numberToFind = (TextView) findViewById(R.id.numberToFindText);

        intent.putExtra("size", arraySize.getText().toString());
        intent.putExtra("range", valueRange.getText().toString());
        intent.putExtra("find", numberToFind.getText().toString());

        startService(intent);
    }
}
