package com.tiko.tamk.rpsapp;

import java.util.Comparator;

/**
 * Created by Jani on 30.3.2016.
 */
public class HighScoreItem implements Comparable<HighScoreItem> {

    String name;
    int score;

    public HighScoreItem(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public int compareTo(HighScoreItem another) {
        if(another.getScore() == score) {
            return 0;
        }
        else if(another.getScore() > score) {
            return 1;
        } else {
            return -1;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return name + "::" + score;
    }
}
