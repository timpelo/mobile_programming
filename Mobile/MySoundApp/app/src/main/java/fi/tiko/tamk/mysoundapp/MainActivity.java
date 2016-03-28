package fi.tiko.tamk.mysoundapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements ChosenSound{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void itemSelected(SoundItem sound) {
        Fragment rightFragment =
                getSupportFragmentManager().findFragmentById(R.id.fragmentRight);

        if(rightFragment != null) {
            TriggerSoundSetupFragment f = (TriggerSoundSetupFragment) rightFragment;
            f.setSound(sound.getSoundId(), sound.getName());
        } else {
            changeActivity(sound);
        }
    }

    private void changeActivity(SoundItem sound) {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("sound", sound.getName());
        intent.putExtra("soundId", sound.getSoundId());
        Log.d("DEBUG", "changing activity");
        startActivity(intent);
    }
}
