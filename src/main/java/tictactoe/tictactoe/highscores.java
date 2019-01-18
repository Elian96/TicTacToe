package tictactoe.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class highscores extends AppCompatActivity {

    //Place holders for the text views from the activity
    private TextView p1wins;
    private TextView p2wins;
    private TextView Aiwins;

    //New variables that will save the score
    int newScore1;
    int newScore2;
    int newAiscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        //Connect the placholders to the text views from the activity
        p1wins = findViewById(R.id.p1wins);
        p2wins = findViewById(R.id.p2wins);
        Aiwins = findViewById(R.id.aiwins);

        Button mainMenu = findViewById(R.id.mainMenu);
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(highscores.this, StartPage.class));
            }
        });

        Button clean = findViewById(R.id.clean);
        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clean();
            }
        });

        newScore1 = getIntent().getIntExtra("point", newScore1);
        newScore2 = getIntent().getIntExtra("point2", newScore2);
        newAiscore = getIntent().getIntExtra("point3", newAiscore);
        p1wins.setText("Player 1 Wins: "+ newScore1);
        p2wins.setText("Player 2 Wins: "+ newScore2);
        Aiwins.setText("CPU: "+ newAiscore);

        //Get the variables that were saved inside the shared pref with the name "myPrefsKey"
        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        int score1 = prefs.getInt("key", newScore1);
        int score2 = prefs.getInt("key2", newScore2);
        int Aiscore = prefs.getInt("Ai", newAiscore);



        //Check if the new scores are bigger that the already saved variables and then update the text views
        if (score1 > newScore1){

            p1wins.setText("Player 1 Wins: "+ Integer.toString(score1));
        }else{
            score1 = newScore1;
            p1wins.setText("Player 1 Wins: "+ Integer.toString(newScore1));
            prefs.edit().putInt("point",score1).apply();
        }

        if (score2 > newScore2){

            p2wins.setText("Player 2 Wins: "+ Integer.toString(score2));

        }else {
            score2 = newScore2;
            p2wins.setText("Player 2 Wins: "+ Integer.toString(newScore2));
            prefs.edit().putInt("point2",score2).apply();
        }

        if (Aiscore > newAiscore){

            Aiwins.setText("CPU Wins: "+ Integer.toString(Aiscore));

        }else{
            Aiscore = newAiscore;
            Aiwins.setText("CPU Wins: "+ Integer.toString(newAiscore));
            prefs.edit().putInt("point3",Aiscore).apply();
        }





    }

    private void clean() {
        //Set the scores to 0 and save them
        newScore1 = 0;
        newScore2 = 0;
        newAiscore = 0;
        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("Ai",newAiscore);
        editor.putInt("key",newScore1);
        editor.putInt("key2",newScore2);
        editor.commit();

        //Clean the text views on the board
        p1wins.setText("Player 1 Wins: "+ 0);
        p2wins.setText("Player 2 Wins: "+ 0);
        Aiwins.setText("CPU Wins: "+ 0);

    }
}
