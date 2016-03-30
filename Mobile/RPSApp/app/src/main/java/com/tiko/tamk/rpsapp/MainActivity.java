package com.tiko.tamk.rpsapp;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView title = (TextView) findViewById(R.id.title);
        Button playButton = (Button) findViewById(R.id.playButton);
        ImageView titleBg = (ImageView) findViewById(R.id.titleBg);

        Animation fade =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
        Animation pulse =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pulse);


        title.startAnimation(fade);
        playButton.startAnimation(fade);
        titleBg.startAnimation(pulse);
    }

    public void startGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}