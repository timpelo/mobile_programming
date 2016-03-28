package fi.tiko.tamk.mysoundapp;

/**
 * Created by Jani on 28.3.2016.
 */
public class SoundItem {

    private int soundId;
    private String name;

    public SoundItem(int soundId, String name) {
        this.soundId = soundId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSoundId() {
        return soundId;
    }

    public void setSoundId(int soundId) {
        this.soundId = soundId;
    }

    @Override
    public String toString() {
        return name;
    }
}
