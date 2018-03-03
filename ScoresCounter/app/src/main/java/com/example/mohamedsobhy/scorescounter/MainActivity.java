package com.example.mohamedsobhy.scorescounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int scoreTeamA , scoreTeamB;
    private TextView scoreViewTeamA , scoreViewTeamB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreViewTeamA = (TextView) findViewById(R.id.score_teamA);
        scoreViewTeamB = (TextView) findViewById(R.id.score_teamB);

    }

    /**
     * increases the score of Team A by 1.
     * @param view carry the view which clicked.
     */
    public void freePointTeamA(View view){
        scoreTeamA += 1;

        setScoreViewTeamA(scoreTeamA);
    }

    /**
     * increases the score of Team B by 1.
     * @param view carry the view which clicked.
     */
    public void freePointTeamB(View view){
        scoreTeamB += 1;

        setScoreViewTeamB(scoreTeamB);
    }

    /**
     * increases the score of team A by 2.
     * @param view carry the view which clicked.
     */
    public void twoPointsTeamA(View view){
        scoreTeamA += 2;
        setScoreViewTeamA(scoreTeamA);
    }

    /**
     * increases the score of team B by 2.
     * @param view carry the view which clicked.
     */
    public void twoPointsTeamB(View view){
        scoreTeamB += 2;
        setScoreViewTeamB(scoreTeamB);
    }

    /**
     * increases the score of team A by 3.
     * @param view carry the view which clicked.
     */
    public void threePointsTeamA(View view){
        scoreTeamA += 3;
        setScoreViewTeamA(scoreTeamA);
    }

    /**
     * increases the score of team B by 3.
     * @param view carry the view which clicked.
     */
    public void threePointsTeamB(View view){
        scoreTeamB += 3;
        setScoreViewTeamB(scoreTeamB);
    }

    /**
     * increases the score of team A by 4.
     * @param view carry the view which clicked.
     */
    public void fourPointsTeamA(View view){
        scoreTeamA += 4;
        setScoreViewTeamA(scoreTeamA);
    }

    /**
     * increases the score of team B by 4.
     * @param view carry the view which clicked.
     */
    public void fourPointsTeamB(View view){
        scoreTeamB += 4;
        setScoreViewTeamB(scoreTeamB);
    }

    /**
     * resets all scores to the default value which equal zero.
     * @param view carry the view which clicked.
     */
    public void resetScores(View view){
        scoreTeamA = scoreTeamB = 0;
        setScoreViewTeamA(scoreTeamA);
        setScoreViewTeamB(scoreTeamB);
    }

    /**
     * update the score of Team A by setting the text of the TextView to the new score.
     */
    private void setScoreViewTeamA(int newScoreTeamA){
        scoreViewTeamA.setText( String.valueOf(newScoreTeamA) );
    }

    /**
     * update the score of Team B by setting the text of the TextView to the new score.
     */
    private void setScoreViewTeamB(int newScoreTeamB){
        scoreViewTeamB.setText(String.valueOf(newScoreTeamB));
    }

}
