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

public class multi_player extends AppCompatActivity implements View.OnClickListener{

    //An int based on the numbers of buttons in each row ord coloum
    private int dimention = 4;
    //Make a new button array to take a 4 x 4 amount of buttons
    private Button[][] buttons = new Button[dimention][dimention];

    //Check if it is the player 1 turn
    private boolean p1Turn = true;
    //The total amount of rounds played
    private int runder;
    //The win score for each side
    private int p1Points;
    private int p2Points;
    //Placeholders for the text vies from the activity
    private TextView p1Score;
    private TextView p2Score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_player);

        //Connect the placeholders to the text vies from the activity
        p1Score = findViewById(R.id.text_view_p1);
        p2Score = findViewById(R.id.text_view_p2);

        //Get the id of each button on the board by counting 4 x 4 and saving them the button array
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

                startActivity(new Intent(multi_player.this, multiplayerlevels.class));
            }
        });
    }

    private void Reset() {

        //Clean the board and set the scores to 0
        p1Points = 0;
        p2Points = 0;
        ScoreText();
        CleanBoard();
    }

    @Override
    public void onClick(View view) {

        //Check if the buttons are not empty and continue if they are
        if (!((Button) view).getText().toString().equals("")){
            return;
        }

        //If it's player 1 turn and the button is clicked, then change the text on the button to X
        if (p1Turn){
            ((Button)view).setText("X");
        }
        //If it's not player 1 turn and the button is clicket, then change the text on the button to O
        else {
            ((Button)view).setText("O");
        }

        //Count the rounds after each click
        runder++;

        //Check if the checkForWin function return a true statment
        if (checkForWin()){
            //If the statment returned is true and it is player 1 turn, then the pause for 1 second and run the p1Win function
            if (p1Turn){

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        p1Win();
                    }
                }, 1000);
            }else{
                //If the statment returned is true and it is not player 1 turn, then the pause for 1 second and run the p2Win function
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        p2Win();
                    }
                }, 1000);
            }
        } else if(runder == 16){
            //If the statment returned is true and the amount of rounds equals the amount of the buttons, then the pause for 1 second and run the draw function
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

        //Set the text from all the buttons in the new string array so they can changed afterwards
        for (int i = 0; i < dimention; i++){
            for (int j = 0; j < dimention; j++){
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        for (int i = 0; i<dimention;i++){
            if(field[i][0].equals(field[i][1]) &&
                    field[i][0].equals(field[i][2]) &&
                    field[i][0].equals(field[i][3]) &&
                    !field[i][0].equals("")){
                return true;
            }
        }

        for (int i = 0; i<dimention;i++){
            if(field[0][i].equals(field[1][i]) &&
                    field[0][i].equals(field[2][i]) &&
                    field[0][i].equals(field[3][i]) &&
                    !field[0][i].equals("")){
                return true;
            }
        }

        if(field[0][0].equals(field[1][1]) &&
                field[0][0].equals(field[2][2]) &&
                field[0][0].equals(field[3][3]) &&
                !field[0][0].equals("")){
            return true;
        }

        if(field[0][3].equals(field[1][2]) &&
                field[1][2].equals(field[2][1]) &&
                field[3][0].equals(field[2][1]) &&
                !field[0][3].equals("")){
            return true;
        }

        return false;
    }

    private void p1Win(){
        //If the player 1 wins then add win points and show a short message, clean the board and run the ScoreText function
        p1Points++;
        Toast.makeText(this,"Player 1 wins!", Toast.LENGTH_SHORT).show();
        ScoreText();
        CleanBoard();
    }
    private void p2Win(){
        //If the player  wins then add win points and show a short message, then clean the board and run the ScoreText function
        p2Points++;
        Toast.makeText(this,"Player 2 wins!", Toast.LENGTH_SHORT).show();
        ScoreText();
        CleanBoard();
    }

    private void draw(){
        //If no one wins then show a short message, and clean the board
        Toast.makeText(this,"Draw!", Toast.LENGTH_SHORT).show();
        CleanBoard();
    }

    private void ScoreText(){
        //Run the highscore function and update the text views
        highscore();
        p1Score.setText("P1: "+ p1Points);
        p2Score.setText("P2: "+ p2Points);

    }

    private void CleanBoard(){
        //Clean the text on every button and reset the rounds, and set player 1 turn to true
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
        //Save the variables så they don't change after rotating the phone
        outState.putInt("runder",runder);
        outState.putInt("p1points",p1Points);
        outState.putInt("p2points",p2Points);
        outState.putBoolean("p1Turn",p1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //Restore old variables that were saved
        runder = savedInstanceState.getInt("runder");
        p1Points = savedInstanceState.getInt("p1points");
        p2Points = savedInstanceState.getInt("p2points");
        p1Turn = savedInstanceState.getBoolean("p1Turn");
    }

    public void highscore(){
        //Save the variables in a shared prefs with the name "myPrefsKey"
        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("key", p1Points);
        editor.putInt("key2", p2Points);
        editor.commit();
    }
}
