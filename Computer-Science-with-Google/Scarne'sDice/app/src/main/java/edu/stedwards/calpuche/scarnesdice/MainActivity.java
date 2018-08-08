/*
 * Copyright (c) 2017. This Code Belongs soley to Carlos Alpuche. Any permission to use must require word from Carlos Alpuche.
 *
 */

package edu.stedwards.calpuche.scarnesdice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public int userOverallScore;
    public int userTurnScore;
    public int computerOverallScore;
    public int computerTurnScore;
    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void rollDice(View view) {
        int newDiceNumber = rand.nextInt(6) + 1;
        int newDiceNumberTwo = rand.nextInt(6) + 1;
        int turnScore = newDiceNumber + newDiceNumberTwo;
        setDiceFace(newDiceNumber, 1);
        setDiceFace(newDiceNumberTwo, 2);

        if ((newDiceNumber == 1 && newDiceNumberTwo > 0) || (newDiceNumberTwo == 1 && newDiceNumber > 1)) {
            userTurnScore = 0;
            TextView text = (TextView) findViewById(R.id.currentGameScore);
            text.setText(Integer.toString(userTurnScore));
            computerTurn();
        } else if (turnScore == 2) {
            userOverallScore = 0;
            userTurnScore = 0;
            TextView text = (TextView) findViewById(R.id.currentGameScore);
            text.setText(Integer.toString(userTurnScore));
            TextView text2 = (TextView) findViewById(R.id.userTotalPoints);
            text2.setText(Integer.toString(userOverallScore));
            isGameOver();
            if (userOverallScore < 99) {
                computerTurn();
            }
        } else {
            userTurnScore += turnScore;
            TextView text = (TextView) findViewById(R.id.currentGameScore);
            text.setText(Integer.toString(userTurnScore));
        }

    }

    public void holdDice(View view) {
        userOverallScore += userTurnScore;
        userTurnScore = 0;
        TextView userOS = (TextView) findViewById(R.id.userTotalPoints);
        userOS.setText(Integer.toString(userOverallScore));
        TextView userTS = (TextView) findViewById(R.id.currentGameScore);
        userTS.setText("0");
        computerTurn();
    }

    public void resetGame(View view) {
        Button roll = (Button) findViewById(R.id.rollButton);
        Button hold = (Button) findViewById(R.id.holdButton);
        roll.setEnabled(true);
        hold.setEnabled(true);
        TextView gameWinner = (TextView) findViewById(R.id.gameWins);
        gameWinner.setText("Current Turn Points:");
        TextView userOS = (TextView) findViewById(R.id.userTotalPoints);
        userOS.setText("0");
        TextView userTS = (TextView) findViewById(R.id.currentGameScore);
        userTS.setText("0");
        TextView computerOS = (TextView) findViewById(R.id.computerTotalPoints);
        computerOS.setText("0");
        userTurnScore = 0;
        userOverallScore = 0;
        computerOverallScore = 0;
        computerTurnScore = 0;


    }

    public void setDiceFace(int diceValue, int die) {
        if (die == 1) {
            ImageView one = (ImageView) findViewById(R.id.diceOne);
            switch (diceValue) {
                case 1:
                    one.setImageResource(R.drawable.dice1);
                    break;
                case 2:
                    one.setImageResource(R.drawable.dice2);
                    break;
                case 3:
                    one.setImageResource(R.drawable.dice3);
                    break;
                case 4:
                    one.setImageResource(R.drawable.dice4);
                    break;
                case 5:
                    one.setImageResource(R.drawable.dice5);
                    break;
                case 6:
                    one.setImageResource(R.drawable.dice6);
                    break;
            }
        } else if (die == 2) {
            ImageView two = (ImageView) findViewById(R.id.diceTwo);
            switch (diceValue) {
                case 1:
                    two.setImageResource(R.drawable.dice1);
                    break;
                case 2:
                    two.setImageResource(R.drawable.dice2);
                    break;
                case 3:
                    two.setImageResource(R.drawable.dice3);
                    break;
                case 4:
                    two.setImageResource(R.drawable.dice4);
                    break;
                case 5:
                    two.setImageResource(R.drawable.dice5);
                    break;
                case 6:
                    two.setImageResource(R.drawable.dice6);
                    break;
            }
        }
    }

    public void computerTurn() {
        Button roll = (Button) findViewById(R.id.rollButton);
        Button hold = (Button) findViewById(R.id.holdButton);
        roll.setEnabled(false);
        hold.setEnabled(false);
        int newDiceNumber = 0;
        int newDiceNumberTwo = 0;
        int turnScore = 0;
        while (computerTurnScore < 15) {
            newDiceNumber = rand.nextInt(6) + 1;
            newDiceNumberTwo = rand.nextInt(6) + 1;
            turnScore = newDiceNumber + newDiceNumberTwo;
            if ((newDiceNumber == 1 && newDiceNumberTwo > 0) || (newDiceNumberTwo == 1 && newDiceNumber > 1)) {
                computerTurnScore = 0;
                break;
            } else if (turnScore == 2) {
                computerTurnScore = 0;
                computerOverallScore = 0;
                break;
            } else {
                computerTurnScore += turnScore;

            }
        }
        computerOverallScore += computerTurnScore;
        TextView computerOS = (TextView) findViewById(R.id.computerTotalPoints);
        computerOS.setText(Integer.toString(computerOverallScore));
        if (computerOverallScore > 99) {
            isGameOver();
            return;
        }
        roll.setEnabled(true);
        hold.setEnabled(true);


    }

    public void isGameOver() {
        if (computerOverallScore > 99 || userOverallScore > 99) {
            Button roll = (Button) findViewById(R.id.rollButton);
            Button hold = (Button) findViewById(R.id.holdButton);
            roll.setEnabled(false);
            hold.setEnabled(false);
            TextView score = (TextView) findViewById(R.id.currentGameScore);
            TextView gameWinner = (TextView) findViewById(R.id.gameWins);
            score.setText("");
            if (computerOverallScore > 99) {
                gameWinner.setText("Computer Wins!");
                gameWinner.setElegantTextHeight(true);
            } else {
                gameWinner.setText("You Win!");
                gameWinner.setElegantTextHeight(true);
            }
        }
    }


}


