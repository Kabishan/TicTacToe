package com.example.kabishan.tictactoe;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    MediaPlayer clearPlayer;
    MediaPlayer dropInPlayer;
    MediaPlayer winPlayer;

    int activePlayer = 0; // 0 red, 1 yellow
    boolean gameDone = false;

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {{0, 1, 2},
                                {3, 4, 5},
                                {6, 7, 8},
                                {0, 3, 6},
                                {1, 4, 7},
                                {2, 5, 8},
                                {0, 4, 8},
                                {2, 4, 6}};

    protected void dropIn(View view) {
        ImageView counter = (ImageView) view;
        TextView winnerTextView = findViewById(R.id.winnerTextView);
        Button playAgainButton = findViewById(R.id.playAgainButton);
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        dropInPlayer.start();

        if (gameState[tappedCounter] == 2 && !gameDone) {
            counter.setTranslationY(-1500);
            gameState[tappedCounter] = activePlayer;

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.red);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 0;
            }

            counter.animate().translationYBy(1500).rotation(360).setDuration(500);

            if (playerWon()) {
                winPlayer.start();

                winnerTextView.setText((activePlayer == 1 ? "Red" : "Yellow") + " has won");
                winnerTextView.setVisibility(View.VISIBLE);
                playAgainButton.setVisibility(View.VISIBLE);
                gameDone = true;
            }
            else {
                if (isBoardFilled()) {
                    winnerTextView.setText("Stalemate");
                    winnerTextView.setVisibility(View.VISIBLE);
                    playAgainButton.setVisibility(View.VISIBLE);
                    gameDone = true;
                }
            }
        }
    }

    protected boolean playerWon() {
        boolean playerWon = false;

        for (int[] winningPosition : winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {
                playerWon = true;
                break;
            }
        }

        return playerWon;
    }

    protected boolean isBoardFilled() {
        boolean filled = true;

        for (int i = 0; i < gameState.length; i++) {
            if (gameState[i] == 2) {
                filled = false;
                break;
            }
        }

        return filled;
    }

    protected void playAgain(View view) {
        TextView winnerTextView = findViewById(R.id.winnerTextView);
        Button playAgainButton = findViewById(R.id.playAgainButton);
        GridLayout grid = findViewById(R.id.board);

        clearPlayer.start();
        winnerTextView.setVisibility(View.INVISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);
        gameDone = false;
        activePlayer = 0;

        for (int i = 0; i < gameState.length; i++) {
            ImageView counter = (ImageView) grid.getChildAt(i);
            counter.setImageDrawable(null);

            gameState[i] = 2;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dropInPlayer = MediaPlayer.create(this, R.raw.pop);
        clearPlayer = MediaPlayer.create(this, R.raw.clear);
        winPlayer = MediaPlayer.create(this, R.raw.applause);
    }
}
