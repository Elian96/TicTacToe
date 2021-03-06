package tictactoe.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class multiplayerlevels extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayerlevels);

        Button normal = findViewById(R.id.normal);

        Button easy = findViewById(R.id.easy);

        Button hard = findViewById(R.id.hard);

        Button mainmenu = findViewById(R.id.mainMenu);

        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(multiplayerlevels.this, multi_player.class));
            }
        });

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(multiplayerlevels.this, multi_player_easy.class));
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(multiplayerlevels.this, multi_player_hard.class));
            }
        });

        mainmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(multiplayerlevels.this, StartPage.class));
            }
        });
    }
}
