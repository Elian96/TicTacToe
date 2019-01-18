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

public class multi_player_hard extends AppCompatActivity implements View.OnClickListener{

    private int dimention = 5;
    private Button[][] buttons = new Button[dimention][dimention];

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
        setContentView(R.layout.activity_multi_player_hard);

        p1Score = findViewById(R.id.text_view_p1);
        p2Score = findViewById(R.id.text_view_p2);

        for (int i = 0; i < dimention; i++){
            for (int j = 0; j<dimention; j++){

                String buttonID ="button_"+i+j;
                int resID = getResources().getIdentifier(buttonID,"id",getPackageName());
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

                startActivity(new Intent(multi_player_hard.this, multiplayerlevels.class));
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

        if (!((Button) view).getText().toString().equals("")){
            return;
        }

        if (p1Turn){
            ((Button)view).setText("X");
        }else {
            ((Button)view).setText("O");
        }

        runder++;

        if (checkForWin()){
            if (p1Turn){
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        p1Win();
                    }
                }, 1000);
            }else{
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        p2Win();
                    }
                }, 1000);
            }
        } else if(runder == 25){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    draw();
                }
            }, 1000);
        }else{
            p1Turn = !p1Turn;
        }
    }

    private boolean checkForWin(){
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
                return true;
            }
        }

        for (int i = 0; i<dimention;i++){
            if(field[0][i].equals(field[1][i]) &&
                    field[0][i].equals(field[2][i]) &&
                    field[0][i].equals(field[3][i]) &&
                    field[0][i].equals(field[4][i]) &&
                    !field[0][i].equals("")){
                return true;
            }
        }

        if(field[0][0].equals(field[1][1]) &&
                field[0][0].equals(field[2][2]) &&
                field[0][0].equals(field[3][3]) &&
                field[0][0].equals(field[4][4]) &&
                !field[0][0].equals("")){
            return true;
        }

        if(field[0][4].equals(field[1][3]) &&
                field[1][3].equals(field[2][2]) &&
                field[2][2].equals(field[3][1]) &&
                field[3][1].equals(field[4][0]) &&
                !field[0][4].equals("")){
            return true;
        }

        return false;
    }

    private void p1Win(){

        p1Points++;
        Toast.makeText(this,"Player 1 wins!", Toast.LENGTH_SHORT).show();
        p1TotalWins++;
        ScoreText();
        CleanBoard();
    }
    private void p2Win(){

        p2Points++;
        Toast.makeText(this,"Player 2 wins!", Toast.LENGTH_SHORT).show();
        p2TotalWins++;
        ScoreText();
        CleanBoard();
    }

    private void draw(){

        Toast.makeText(this,"Draw!", Toast.LENGTH_SHORT).show();
        CleanBoard();
    }

    private void ScoreText(){
        highscore();
        p1Score.setText("P1: "+ p1Points);
        p2Score.setText("P2: "+ p2Points);

    }

    private void CleanBoard(){
        for (int i=0; i<dimention; i++){
            for (int j=0;j<dimention;j++){
                buttons[i][j].setText("");
            }
        }
        runder = 0;
        p1Turn = true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("runder",runder);
        outState.putInt("p1points",p1Points);
        outState.putInt("p2points",p2Points);
        outState.putBoolean("p1Turn",p1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        runder = savedInstanceState.getInt("runder");
        p1Points = savedInstanceState.getInt("p1points");
        p2Points = savedInstanceState.getInt("p2points");
        p1Turn = savedInstanceState.getBoolean("p1Turn");
    }

    public void highscore(){

        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("key", p1Points);
        editor.putInt("key2", p2Points);
        editor.commit();
    }
}
