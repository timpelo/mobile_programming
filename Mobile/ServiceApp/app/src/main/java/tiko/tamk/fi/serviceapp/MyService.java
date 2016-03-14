package tiko.tamk.fi.serviceapp;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;

public class MyService extends Service implements Runnable {
    private String TAG = "MyService";
    private int arraySize;
    private int valueRange;
    private int findNumber;
    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStart()");
        Bundle extras = intent.getExtras();
        arraySize = Integer.parseInt(extras.getString("size"));
        valueRange = Integer.parseInt(extras.getString("range"));
        findNumber = Integer.parseInt(extras.getString("find"));
        return  START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate()");
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void run() {
        Random rand = new Random();
        int[] valueArray = new int[arraySize];
        String result = findNumber + " is not found!";

        for (int i = 0; i < valueArray.length; i++) {
            int tmp = rand.nextInt(valueRange);
            valueArray[i] = tmp;
        }

        valueArray = doSelectionSort(valueArray);

        for (int i = 0; i < valueArray.length; i++) {
            if(valueArray[i] == findNumber) {
                result = findNumber + " is found!";
            }
        }

        Log.d(TAG, result);
    }

    public static int[] doSelectionSort(int[] arr){

        for (int i = 0; i < arr.length - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < arr.length; j++)
                if (arr[j] < arr[index])
                    index = j;

            int smallerNumber = arr[index];
            arr[index] = arr[i];
            arr[i] = smallerNumber;
        }
        return arr;
    }
}
