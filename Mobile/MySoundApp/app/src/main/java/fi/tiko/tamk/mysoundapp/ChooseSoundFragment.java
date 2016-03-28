package fi.tiko.tamk.mysoundapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jani on 28.3.2016.
 */
public class ChooseSoundFragment  extends Fragment{
    private ChosenSound callback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callback = (ChosenSound) getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_left, container, false);
        ListView list = (ListView) view.findViewById(R.id.soundList);
        final SoundItem[] sounds = {new SoundItem(R.raw.cat, "Cat"),
                new SoundItem(R.raw.bark, "Dog"),
                new SoundItem(R.raw.duck, "Duck"),
                new SoundItem(R.raw.bell, "Bell"),};

        ArrayAdapter<SoundItem> adapter = new ArrayAdapter<SoundItem>(getActivity(),
                R.layout.list_item,
                sounds);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callback.itemSelected(sounds[position]);
            }
        });
        return view;
    }
}
