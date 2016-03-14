package com.tiko.tamk.secondapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.Calendar;

public class MainActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Debug.loadDebug(this);
        Debug.print(TAG, "onCreate()", "created", 1);
        setContentView(R.layout.activity_main);

        Calendar c = Calendar.getInstance();
        String time = c.getTime().toString();

        TextView date = (TextView) findViewById(R.id.dateText);
        date.setText(time);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Debug.print(TAG, "onStart()", "started", 1);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Debug.print(TAG, "onRestart()", "restarted", 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Debug.print(TAG, "onResume()", "resumed", 1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Debug.print(TAG, "onPause()", "paused", 1);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Debug.print(TAG, "onStop()", "stopped", 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Debug.print(TAG, "onDestroy()", "destroyed", 1);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        TextView dateTemp = (TextView) findViewById(R.id.dateText);
        String date = savedInstanceState.getString("time");
        dateTemp.setText(date);
        Debug.print(TAG, "onRestoreInstanceState()", "restored", 1);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        Debug.print(TAG, "onSaveInstanceState()", "saved", 1);
        TextView dateTemp = (TextView) findViewById(R.id.dateText);
        String date = dateTemp.getText().toString();
        savedInstanceState.putString("time", date);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void goToSecondActivity(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}
