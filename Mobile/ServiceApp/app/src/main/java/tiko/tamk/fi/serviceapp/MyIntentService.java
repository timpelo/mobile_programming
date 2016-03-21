package tiko.tamk.fi.serviceapp;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.util.Random;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {
    private String TAG = "MyIntentService";
    private int arraySize;
    private int valueRange;
    private int findNumber;

    public MyIntentService() {
        super("MyIntentService");
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onStart()");
        Bundle extras = intent.getExtras();
        arraySize = Integer.parseInt(extras.getString("size"));
        valueRange = Integer.parseInt(extras.getString("range"));
        findNumber = Integer.parseInt(extras.getString("find"));

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
