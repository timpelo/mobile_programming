package fi.tiko.tamk.mysoundapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by Jani on 28.3.2016.
 */
public class TriggerSoundSetupFragment extends Fragment {
    SoundItem selectedSound = null;
    MyBroadCastReceiver receiver;
    int timer = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        receiver = new MyBroadCastReceiver();
        IntentFilter myIntentFilter = new IntentFilter();
        getActivity().registerReceiver(receiver, myIntentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right, container, false);

        final SeekBar timerBar = (SeekBar) view.findViewById(R.id.timerBar);
        timerBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("DEBUG", "" + timerBar.getProgress());
                timer = timerBar.getProgress();
                updateTimer();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        final Button playButton = (Button) view.findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSound();
            }
        });

        return view;
    }

    public void setSound(int soundID, String soundName) {

        selectedSound = new SoundItem(soundID, soundName);
        TextView soundNameText = (TextView) getView().findViewById(R.id.soundName);
        soundNameText.setText(soundName);

    }

    public void startSound() {
        Intent intent = new Intent(getActivity(), MediaPlayerService.class);
        intent.putExtra("soundId", selectedSound.getSoundId());
        intent.putExtra("timer", timer);
        getActivity().startService(intent);
    }

    public void updateTimer() {
        TextView timerTExt = (TextView) getView().findViewById(R.id.timerText);
        timerTExt.setText("" + timer);
    }

    public class MyBroadCastReceiver extends BroadcastReceiver {

        public  MyBroadCastReceiver() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("DEBUG", "received");
            TextView timerText = (TextView) getView().findViewById(R.id.timerText);
            int timeLeft = intent.getExtras().getInt("timeLeft");
            timerText.setText(timeLeft);
        }
    }


}
