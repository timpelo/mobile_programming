package tiko.tamk.fi.myweatherapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by juhis5 on 18.2.2016.
 */
public class RestConnectionCurrent extends AsyncTask <String, Void, String>{

    private Activity host;
    public RestConnectionCurrent(Activity host) {
        this.host = host;
    }

    @Override
    protected String doInBackground(String... params) {
        String lat = params[0];
        String lon = params[1];
        String appId = "b5c37673c9f4331c72f5b9671325156b";
        URI uri = null;
        final DefaultHttpClient httpClient = new DefaultHttpClient();

        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("api.openweathermap.org")
                    .setPath("/data/2.5/weather")
                    .setParameter("lat", lat)
                    .setParameter("lon", lon)
                    .setParameter("appid", appId)
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        HttpGet httpGet = new HttpGet(uri);
        InputStream stream = null;
        try {
            HttpResponse response = httpClient.execute(httpGet);
            stream = response.getEntity().getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result = convertStreamToString(stream);
        return result;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
