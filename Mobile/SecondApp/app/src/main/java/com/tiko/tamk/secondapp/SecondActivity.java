package com.tiko.tamk.secondapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SecondActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Debug.loadDebug(this);
        Debug.print(TAG, "onCreate()", "created", 1);
        setContentView(R.layout.activity_second);
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
        Debug.print(TAG, "onRestoreInstanceState()", "restored", 1);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        Debug.print(TAG, "onSaveInstanceState()", "saved", 1);
        super.onSaveInstanceState(savedInstanceState);

    }
}
