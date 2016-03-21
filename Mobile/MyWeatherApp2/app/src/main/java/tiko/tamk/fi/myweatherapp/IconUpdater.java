package tiko.tamk.fi.myweatherapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Jani on 21.3.2016.
 */
public class IconUpdater extends AsyncTask<String, Void, Bitmap> {
    Activity host;

    public IconUpdater(Activity host) {
        this.host = host;
    }
    @Override
    protected Bitmap doInBackground(String... params) {
            String iconId = params[0];
            String imageUrl = "http://openweathermap.org/img/w/"+ iconId + ".png";
            Log.d("TAG", "image url " + imageUrl);


            try {
                ImageView i = (ImageView) host.findViewById(R.id.weathericon);
                Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(imageUrl).getContent());
                return bitmap;
            } catch (MalformedURLException e) {
                Log.d("TAG", "malform url");
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                Log.d("TAG", "io expception");
                e.printStackTrace();
                return null;
            }

    }

    @Override
    protected void onPostExecute(Bitmap result) {
        ImageView i = (ImageView) host.findViewById(R.id.weathericon);
        i.setImageBitmap(result);

    }

}
