package fi.tiko.tamk.mysoundapp;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

    }

    @Override
    protected void onResume() {
        super.onResume();

        String soundName = getIntent().getExtras().getString("sound");
        int soundId = getIntent().getExtras().getInt("soundId");

        TriggerSoundSetupFragment fragment =
                (TriggerSoundSetupFragment)getSupportFragmentManager()
                        .findFragmentById(R.id.fragmentRight);

        fragment.setSound(soundId, soundName);
    }
}
