package com.tiko.tamk.myrunapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jani on 4.4.2016.
 */
public class ServerConnection extends AsyncTask<Double, Void, Integer> {

    final private String TAG = "ServerConnection";
    private String key;
    HttpURLConnection conn;
    URL url;

    public ServerConnection(String key) {
        this.key = key;
    }
    @Override
    protected Integer doInBackground(Double... params) {

        try {
            // Creates url string with given parameters.
            String urlString = "http://koti.tamk.fi/~e4jtimon/backend/location_reciever.php?"
                    + "lat=" + params[0]
                    + "&lon=" + params[1]
                    + "&start=" + params[2]
                    + "&key=" + key;

            // Creates new url.
            url = new URL(urlString);

            // Opens new url connection.
            conn = (HttpURLConnection) url
                    .openConnection();

            // Creates new input stream reader.
            InputStream in = conn.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            reader.read();

        } catch (MalformedURLException e) {
            return 400;
        } catch (IOException e) {
            return 500;
        }

        return 200;
    }

    protected void onPostExecute(Integer result) {

        // Checks return code.
        switch (result) {
            case 200:

                break;
            case 400:

                break;
            case 500:

                break;
        }

        // If there is connection, close it.
        if(conn != null) {
            conn.disconnect();
        }
    }
}
