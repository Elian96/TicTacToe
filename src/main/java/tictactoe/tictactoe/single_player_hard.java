package tictactoe.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class single_player_hard extends AppCompatActivity implements View.OnClickListener {
    private int dimention = 5;
    private Button[][] buttons = new Button[dimention][dimention];


    String check = "";

    private boolean p1Turn = true;
    public int p1TotalWins;
    public int p2TotalWins;

    private int runder;

    private int p1Points;
    private int p2Points;

    private TextView p1Score;
    private TextView p2Score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player_hard);

        p1Score = findViewById(R.id.text_view_p1);
        p2Score = findViewById(R.id.text_view_p2);

        for (int i = 0; i < dimention; i++) {
            for (int j = 0; j < dimention; j++) {

                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Reset();

            }
        });

        Button back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(single_player_hard.this, singleplayerlevels.class));
            }
        });

    }

    private void Reset() {

        p1Points = 0;
        p2Points = 0;
        ScoreText();
        CleanBoard();
    }

    @Override
    public void onClick(View view) {

        if (!((Button) view).getText().toString().equals("")) {
            return;
        }

        if (p1Turn) {
            ((Button) view).setText("X");
            p1Turn = false;
        }
        if (!p1Turn && runder != 12) {
            Random random = new Random();
            int i, j;
            i = random.nextInt(dimention);
            j = random.nextInt(dimention);

            aa:
            for (int k = 0; k < dimention * dimention; k++) {

                while (!(buttons[i][j].getText().toString().equals(""))) {
                    i = random.nextInt(dimention);
                    j = random.nextInt(dimention);
                }
                buttons[i][j].setText("O");
                break aa;
            }
        }

        runder++;

        if (checkForWin()) {
            if (check == "P1") {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        p1Win();
                    }
                }, 1000);
            } else {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        p2Win();
                    }
                }, 1000);
            }
        } else if (runder == 13) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    draw();
                }
            }, 1000);
        } else {
            p1Turn = true;
        }
    }


    private boolean checkForWin() {
        String[][] field = new String[dimention][dimention];

        for (int i = 0; i < dimention; i++){
            for (int j = 0; j < dimention; j++){
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        for (int i = 0; i<dimention;i++){
            if(field[i][0].equals(field[i][1]) &&
                    field[i][0].equals(field[i][2]) &&
                    field[i][0].equals(field[i][3]) &&
                    field[i][0].equals(field[i][4]) &&
                    !field[i][0].equals("")){

                if (field[i][0].equals("X"))
                    check = "P1";
                else
                    check = "AI";
                return true;
            }
        }

        for (int i = 0; i<dimention;i++){
            if(field[0][i].equals(field[1][i]) &&
                    field[0][i].equals(field[2][i]) &&
                    field[0][i].equals(field[3][i]) &&
                    field[0][i].equals(field[4][i]) &&
                    !field[0][i].equals("")){

                if (field[0][i].equals("X"))
                    check = "P1";
                else
                    check = "AI";
                return true;
            }
        }

        if(field[0][0].equals(field[1][1]) &&
                field[0][0].equals(field[2][2]) &&
                field[0][0].equals(field[3][3]) &&
                field[0][0].equals(field[4][4]) &&
                !field[0][0].equals("")){

            if (field[0][0].equals("X"))
                check = "P1";
            else
                check = "AI";
            return true;
        }

        if(field[0][4].equals(field[1][3]) &&
                field[1][3].equals(field[2][2]) &&
                field[2][2].equals(field[3][1]) &&
                field[3][1].equals(field[4][0]) &&
                !field[0][4].equals("")){

            if (field[0][4].equals("X"))
                check = "P1";
            else
                check = "AI";
            return true;
        }

        return false;
    }

    private void p1Win() {

        p1Points++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        p1TotalWins++;
        ScoreText();
        CleanBoard();
    }

    private void p2Win() {

        p2Points++;
        Toast.makeText(this, "CPU wins!", Toast.LENGTH_SHORT).show();
        p2TotalWins++;
        ScoreText();
        CleanBoard();
    }

    private void draw() {

        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        CleanBoard();
    }

    private void ScoreText() {
        Aihighscore();
        p1Score.setText("P1: " + p1Points);
        p2Score.setText("CPU: " + p2Points);

    }

    private void CleanBoard() {
        for (int i = 0; i < dimention; i++) {
            for (int j = 0; j < dimention; j++) {
                buttons[i][j].setText("");
            }
        }
        runder = 0;
        p1Turn = true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("runder", runder);
        outState.putInt("p1points", p1Points);
        outState.putInt("p2points", p2Points);
        outState.putInt("p1TotatlWins", p1TotalWins);
        outState.putInt("p1TotatlWins", p1TotalWins);
        outState.putBoolean("p1Turn", p1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        runder = savedInstanceState.getInt("runder");
        p1Points = savedInstanceState.getInt("p1points");
        p2Points = savedInstanceState.getInt("p2points");
        p1Turn = savedInstanceState.getBoolean("p1Turn");
        p1TotalWins = savedInstanceState.getInt("p1TotalWins");
        p2TotalWins = savedInstanceState.getInt("p2TotalWins");
    }

    public void Aihighscore() {

        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("key", p1Points);
        editor.putInt("Ai", p2Points);
        editor.commit();
    }

}
