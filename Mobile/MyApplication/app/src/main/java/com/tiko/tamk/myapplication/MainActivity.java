package com.tiko.tamk.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends MyBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Debug.loadDebug(this);

    }

    public void buttonPress(View v) {
        Debug.print(TAG, "buttonPress()", "clicked", 1);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        TextView heightText = (TextView) findViewById(R.id.height);
        TextView weightText = (TextView) findViewById(R.id.weight);
        TextView bmiText = (TextView) findViewById(R.id.bmi);

        if(!TextUtils.isEmpty(heightText.getText()) &&
                !TextUtils.isEmpty(weightText.getText())) {

            Debug.print(TAG, "buttonPress()", "valid options", 2);

            try {
                float height = Float.parseFloat(heightText.getText().toString());
                height = height/100;
                float weight = Float.parseFloat(weightText.getText().toString());

                float bmi = weight / (height * height);
                String result = String.format("%.2f", bmi);
                builder.setMessage("BMI = " + result)
                        .setTitle("BMI Result");
            } catch (NumberFormatException e) {
                builder.setMessage("You didn't enter numeric values!")
                        .setTitle("Error");
                Debug.print(TAG, "ButtonPress()", "no valid numbers", 3);
            }
        } else {
            builder.setMessage("You have given invalid values.")
                    .setTitle("Error");
            Debug.print(TAG, "buttonPress()", "invalid options", 2);
        }

        // Hides keyboard after pressing button.
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(weightText.getWindowToken(),
                    InputMethodManager.RESULT_UNCHANGED_SHOWN);

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
