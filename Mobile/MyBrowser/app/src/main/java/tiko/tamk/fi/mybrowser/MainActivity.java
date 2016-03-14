package tiko.tamk.fi.mybrowser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView myWeb = (WebView) findViewById(R.id.myBrowser);
        myWeb.setWebViewClient(new WebViewClient());
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        Log.d("WebApp", "url " + url);
        myWeb.loadUrl(url);
    }
}
