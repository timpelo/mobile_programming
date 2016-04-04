package com.tiko.tamk.myrunapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
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

    HttpURLConnection conn;
    Activity host;

    public ServerConnection(Activity host) {
        this.host = host;
    }
    @Override
    protected Integer doInBackground(Double... params) {
        try {
            String urlString = "http://koti.tamk.fi/~e4jtimon/backend/location_reciever.php?"
                    + "lat=" + params[0]
                    + "&lon=" + params[1];

            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Writes data to output stream;
            //OutputStream os = conn.getOutputStream();
            //BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            //writer.write("lat" + params[0] + "lon" + params[1]);

            conn.connect();
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
                Toast.makeText(host, "Connection OK", Toast.LENGTH_SHORT).show();
                break;
            case 400:
                Toast.makeText(host, "Malformed URL", Toast.LENGTH_SHORT).show();
                break;
            case 500:
                Toast.makeText(host, "IO Exception", Toast.LENGTH_SHORT).show();
                break;
        }

        // If there is connection, close it.
        if(conn != null) {
            conn.disconnect();
        }
    }
}
