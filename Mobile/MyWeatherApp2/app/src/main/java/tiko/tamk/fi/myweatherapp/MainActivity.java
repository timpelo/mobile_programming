package tiko.tamk.fi.myweatherapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Main activity. Contains data of current weather of current location. Handles updating data from
 * openweathermap.org.
 *
 * @author Jani Timonen
 * @version 1.0
 * @since 1.7
 */
public class MainActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener{
    /**
     * String containing curren unit symbol.
     */
    private String unitString = "°C";

    /**
     * String containing name of current city.
     */
    private String city = "";

    /**
     * Temperature as celsius.
     */
    private String temperatureC;

    /**
     * Temperature as fahrenheit.
     */
    private String temperatureF;

    /**
     * Unit id for temperature.
     */
    private int unit = 1;

    /**
     * Description for current weather.
     */
    private String description;

    /**
     * If of current weather description.
     */
    private int descriptionId;

    /**
     * Client for Google API.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Latest location.
     */
    protected Location mLastLocation;

    /**
     * Latitude of current location.
     */
    protected double mLatitude = 0;

    /**
     * Longitude of current location.
     */
    protected double mLongitude = 0;

    private String iconId = "";


    protected boolean initText = true;

    /**
     * Animation for refresh button.
     */
    RotateAnimation r;

    /**
     * Rotation degrees for animation.
     */
    private static final float ROTATE_FROM = 0.0f;

    /**
     * Rotation degrees for animation.
     */
    private static final float ROTATE_TO = -2.5f * 360.0f;

    /**
     * Refresh button.
     */
    ImageView refreshButton;

    /**
     * On create method called when activity is created.
     *
     * @param savedInstanceState bundle containing instance data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buildGoogleApiClient();
    }

    /**
     * Builds Google API Client for getting location.
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    /**
     * Transform json string to json object.
     *
     * @param jsonString json as string.
     * @return json as object.
     */
    public JsonObject transformStringToJson(String jsonString) {
        JsonObject obj = new JsonParser().parse(jsonString).getAsJsonObject();
        return obj;
    }

    /**
     * Finds temperature from json object and stores values to variables.
     *
     * @param obj json object containing info of weather.
     */
    public void getTempertureFromJson(JsonObject obj) {
        JsonElement element = obj.get("main");
        JsonObject tmp = element.getAsJsonObject();
        String temperature = tmp.get("temp").toString();
        float tempFloatC = 0;
        float tempFloatF = 0;

        float tempFloat = Float.parseFloat(temperature);
        tempFloatC = tempFloat - 272.15f;
        tempFloatF =  tempFloatC * 1.8f + 32;
        int tempCelInt = Math.round(tempFloatC);
        temperatureC = "" + tempCelInt;
        int tempFahInt = Math.round(tempFloatF);
        temperatureF = "" + tempFahInt;
    }

    /**
     * Finds weather condition and weather condition id from json object.
     *
     * @param obj json object containing weather condition and condition id.
     */
    public void getWeatherConditionFromJson(JsonObject obj) {
        JsonArray element = obj.getAsJsonArray("weather");
        JsonElement tmp = element.get(0);
        JsonObject obj2 = tmp.getAsJsonObject();

        String description = obj2.get("description").toString();
        int descriptionId = Integer.parseInt(obj2.get("id").toString());
        String iconId = obj2.get("icon").toString();
        description = description.replaceAll("^\"|\"$", "");
        iconId = iconId.replaceAll("^\"|\"$", "");
        this.description = description;
        this.descriptionId = descriptionId;
        this.iconId = iconId;
    }

    /**
     * Finds city name from json object and stores it to variable.
     *
     * @param obj json object containing weather information.
     */
    public void getCityFromJson(JsonObject obj) {
        String city = obj.get("name").toString();
        city = city.replaceAll("^\"|\"$", "");
        this.city =  city;
    }

    /**
     * Called if Google API connection does success.
     *
     * @param connectionHint bundle containing connection data.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }catch(SecurityException e) {

        }

        if (mLastLocation != null) {
            mLatitude = mLastLocation.getLatitude();
            mLongitude = mLastLocation.getLongitude();
            if(initText) {
                updateWeather(getWindow().findViewById(R.id.default_control_frame));
                initText = false;
            }
        } else {

        }
    }

    /**
     * Called if connection to Google API is lost.
     *
     * @param cause id for disconnection cause.
     */
    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        mGoogleApiClient.connect();
    }

    /**
     * Called if connection fails.
     *
     * @param connectionResult result for connection failure.
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    /**
     * Called when activity is started.
     */
    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    /**
     * Called when activity is stopped.
     */
    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Updates weather from openweathermap.org.
     *
     * @param view current view.
     */
    public void updateWeather(View view) {
        TextView temperatureText = (TextView)findViewById(R.id.tempertureText);
        TextView city = (TextView)findViewById(R.id.cityName);
        TextView desc = (TextView) findViewById(R.id.description);
        RestConnectionCurrent rest = new RestConnectionCurrent(this);
        TextView unitText = (TextView) findViewById(R.id.unitText);
        String restResult = null;
        try {
            restResult = rest.execute("" + mLatitude,"" + mLongitude).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        JsonObject obj = transformStringToJson(restResult);
        getTempertureFromJson(obj);
        getWeatherConditionFromJson(obj);
        getCityFromJson(obj);

        desc.setText(description);

        if(unit == 1) {
            temperatureText.setText(temperatureC);
        } else {
            temperatureText.setText(temperatureF);
        }
        unitText.setText(unitString);
        city.setText(this.city);

        IconUpdater updater = new IconUpdater(this);
        updater.execute(iconId);
    }


}
