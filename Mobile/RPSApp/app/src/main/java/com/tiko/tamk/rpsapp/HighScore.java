package com.tiko.tamk.rpsapp;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Jani on 30.3.2016.
 */
public class HighScore {

    private List<HighScoreItem> scoreList;
    private Activity host;
    final private String FILENAME = "highscore.txt";

    public HighScore(Activity host) {
        this.host = host;
        scoreList = new ArrayList<>();
    }

    private void sortItems() {
        Collections.sort(scoreList);
    }

    public void saveToFile() {
        try {
            PrintWriter writer = new PrintWriter(host.openFileOutput(FILENAME, host.MODE_PRIVATE));

            for(HighScoreItem item: scoreList) {
                writer.println(item.toString());
            }

            writer.close();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile() {

        try {
            FileInputStream ins = host.openFileInput(FILENAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
            ArrayList<String> lines = new ArrayList<>();
            String line;
            while((line = reader.readLine()) != null) {
                lines.add(line);
            }

            convertToItems(lines);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addToList(HighScoreItem item) {
        scoreList.add(item);
        sortItems();
    }

    public List<HighScoreItem> getScoreList() {
        return scoreList;
    }

    private void convertToItems(List<String> lines) {

        for(String line: lines) {
            String[] valueArray = line.split("::");

            String name = valueArray[0];
            int score = Integer.parseInt(valueArray[1]);

            HighScoreItem item = new HighScoreItem(name, score);
            addToList(item);
        }

        HighScoreItem best = scoreList.get(0);

        if(best != null) {
            String bestPlayerText = "Best player "
                    + best.getName()
                    + " with score "
                    + best.getScore();

            Toast.makeText(host, bestPlayerText, Toast.LENGTH_SHORT).show();
        }
    }
}
