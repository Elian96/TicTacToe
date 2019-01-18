package tictactoe.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        Button multiPlayer = findViewById(R.id.multiPlayer);

        multiPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(StartPage.this, multiplayerlevels.class));
            }
        });

        Button singlePlayer = findViewById(R.id.singlePlayer);

        singlePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(StartPage.this, singleplayerlevels.class));
            }
        });

        Button highscore = findViewById(R.id.highscore);

        highscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(StartPage.this, highscores.class));
            }
        });
    }
}
