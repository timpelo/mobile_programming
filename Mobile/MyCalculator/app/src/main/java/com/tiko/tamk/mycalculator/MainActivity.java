package com.tiko.tamk.mycalculator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Resettable {

    ArrayList<Float> numberList;
    ArrayList<String> operatorList;
    public static boolean reset = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numberList = new ArrayList<>();
        operatorList = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.reset:
                showDialog();
                return true;
            case R.id.info:
                Toast.makeText(this, "©Jani Timonen", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return true;
        }
    }

    public void resetEqualsText() {
        TextView t = (TextView) findViewById(R.id.equalsText);
        t.setText("");
    }

    @Override
    public void hardReset() {
        TextView t = (TextView) findViewById(R.id.equalsText);
        TextView operatorText = (TextView) findViewById(R.id.operatorText);
        operatorText.setText("");
        t.setText("");
        numberList.clear();
        operatorList.clear();
    }

    public void showDialog() {
        MyResetDialog d = MyResetDialog.newInstance("Do you really want to reset?");
        d.show(getFragmentManager(), "dialog");
    }

    public void sendKey(View view) {
        TextView equalsText = (TextView) findViewById(R.id.equalsText);
        TextView operatorText = (TextView) findViewById(R.id.operatorText);
        String currentText = "" + equalsText.getText().toString();

        switch (view.getId()) {
            case R.id.bequals:
                addNumber(currentText);
                calculate();
                break;
            case R.id.bdivide:
                if(!currentText.equals("")) {
                    Log.d("DEBUG", currentText);
                    addOperator("/");
                    addNumber(currentText);
                    resetEqualsText();
                    operatorText.setText(currentText + " /");
                }
                break;
            case R.id.bminus:
                if(!currentText.equals("")) {
                    Log.d("DEBUG", currentText);
                    addOperator("-");
                    addNumber(currentText);
                    resetEqualsText();
                    operatorText.setText(currentText + " -");
                }
                break;
            case R.id.bsum:
                if(!currentText.equals("")) {
                    Log.d("DEBUG", currentText);
                    addOperator("+");
                    addNumber(currentText);
                    resetEqualsText();
                    operatorText.setText(currentText + " +");
                }
                break;
            case R.id.btimes:
                if(!currentText.equals("")) {
                    Log.d("DEBUG", currentText);
                    addOperator("*");
                    addNumber(currentText);
                    operatorText.setText(currentText + " *");
                    resetEqualsText();

                }
                break;
            default:
                Button btn = (Button) view;
                equalsText.append(btn.getText().toString());
        }
    }

    public void addNumber(String number) {
        Float n = Float.parseFloat(number);
        numberList.add(n);
    }
    public void addOperator(String operator) {
        operatorList.add(operator);
    }

    public void calculate() {
        Float result = null;

        for(int i = 0; i < operatorList.size(); i++) {
            String operator = operatorList.get(i);

            switch (operator){
                case "+":
                    if(result == null) {
                        result = numberList.get(i) + numberList.get(i + 1);
                    } else {
                        result = result + numberList.get(i+1);
                    }
                    break;
                case "-":
                    if(result == null) {
                        result = numberList.get(i) - numberList.get(i + 1);
                    } else {
                        result = result - numberList.get(i+1);
                    }
                    break;
                case "*":
                    if(result == null) {
                        result = numberList.get(i) * numberList.get(i + 1);
                    } else {
                        result = result * numberList.get(i+1);
                    }
                    break;
                case "/":
                    if(result == null) {
                        result = numberList.get(i) / numberList.get(i + 1);
                    } else {
                        result = result / numberList.get(i+1);
                    }
                    break;
            }
        }

        TextView equalsText = (TextView) findViewById(R.id.equalsText);
        DecimalFormat f = new DecimalFormat("#.##");
        f.setRoundingMode(RoundingMode.CEILING);
        hardReset();
        equalsText.setText("" + f.format(result));
    }

    public static class MyResetDialog extends DialogFragment {

        public static MyResetDialog newInstance(String text) {
            MyResetDialog d = new MyResetDialog();
            Bundle args = new Bundle();
            args.putString("text", text);
            d.setArguments(args);
            return d;

        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstance){

            String text = getArguments().getString("text");
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(text)
                    .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((Resettable)getActivity()).hardReset();
                            Toast.makeText(getActivity(), "Calculator reset!", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            reset = false;
                        }
                    });

            return builder.create();
        }
    }
}
