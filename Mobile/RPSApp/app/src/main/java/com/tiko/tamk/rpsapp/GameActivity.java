package com.tiko.tamk.rpsapp;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Random;

public class GameActivity extends AppCompatActivity{

    private HashMap<Integer, String> values;
    private String player = "";
    private String ai = "";
    private String resultText = "";

    private int playerScore = 0;
    private int aiScore = 0;
    private int winner;

    final private String SCISSORS = "SCISSORS";
    final private String ROCK = "ROCK";
    final private String PAPER = "PAPER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        values = new HashMap<>();

        // Set player values to hashmap.
        values.put(R.id.paper, PAPER);
        values.put(R.id.rock, ROCK);
        values.put(R.id.scissors, SCISSORS);

        // Sets ai values to hashmap.
        values.put(0, PAPER);
        values.put(1, ROCK);
        values.put(2, SCISSORS);


    }

    public void selectValue(View view) {
        player = values.get(view.getId());
        Button b1 = (Button) findViewById(R.id.paper);
        Button b2 = (Button) findViewById(R.id.rock);
        Button b3 = (Button) findViewById(R.id.scissors);
        b1.setVisibility(View.INVISIBLE);
        b2.setVisibility(View.INVISIBLE);
        b3.setVisibility(View.INVISIBLE);

        playRound();
    }

    public void playRound() {
        Random rand = new Random();
        int randomInt = rand.nextInt(2-0);
        ai = values.get(randomInt);
        winner = checkPlayerWin();

        switch (winner) {
            case 1:
                resultText = "PLAYER WON!";
                playerScore++;
                break;
            case -1:
                resultText = "AI WON!";
                aiScore++;
                break;
            case 0:
                resultText = "TIE!";
                break;
        }

        updateUi();
    }

    public int checkPlayerWin() {
        int result = 0;

        // Check if player has won.
        if(player == PAPER && ai == ROCK) {
            result = 1;
        }
        else if(player == SCISSORS && ai == PAPER) {
            result = 1;
        }
        else if(player == ROCK && ai == SCISSORS) {
            result = 1;
        }

        // Check if ai has won.
        else if(player == PAPER && ai == SCISSORS) {
            result = -1;
        }
        else if(player == SCISSORS && ai == ROCK) {
            result = -1;
        }
        else if(player == ROCK && ai == PAPER) {
            result = -1;
        }

        else if(player == ROCK && ai == ROCK) {
            result = 0;
        }
        else if(player == PAPER && ai == PAPER) {
            result = 0;
        }
        else if(player == SCISSORS && ai == SCISSORS) {
            result = 0;
        }

        return result;
    }

    public void updateUi() {
        TextView result = (TextView) findViewById(R.id.resultText);
        TextView player = (TextView) findViewById(R.id.playerScore);
        TextView ai = (TextView) findViewById(R.id.aiScore);

        Animation fade = AnimationUtils.loadAnimation(this, R.anim.fadein);

        result.setText(resultText);
        result.startAnimation(fade);

        player.setText("PLAYER: " + playerScore);
        ai.setText("AI: " + aiScore);
    }

    public void newGame() {
        TextView result = (TextView) findViewById(R.id.resultText);
        result.setText("");

        // Set selection buttons back to visible.
        Button b1 = (Button) findViewById(R.id.paper);
        Button b2 = (Button) findViewById(R.id.rock);
        Button b3 = (Button) findViewById(R.id.scissors);
        b1.setVisibility(View.VISIBLE);
        b2.setVisibility(View.VISIBLE);
        b3.setVisibility(View.VISIBLE);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.default_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.reset:
                newGame();
                return true;
            case R.id.save:
                saveScore();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDestroy() {
        saveScore();
        super.onDestroy();
    }

    public void saveScore() {
        Toast.makeText(this, "Saving current score", Toast.LENGTH_SHORT).show();
        HighScoreItem score = new HighScoreItem("Player", playerScore);
        HighScore scoreSave = new HighScore(this);
        scoreSave.addToList(score);
        scoreSave.saveToFile();
        Toast.makeText(this, "Save successful!", Toast.LENGTH_SHORT).show();

        scoreSave.loadFromFile();
    }
}
