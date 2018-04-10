package com.example.android.tic_tac_toe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SinglePlayerGameModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player_game_mode);
    }

    public void threeMode(View view) {
        Intent intent = new Intent(SinglePlayerGameModeActivity.this, SingleThreeByThreeNameOfPlayer.class);
        if(intent.resolveActivity(getPackageManager())!=null) {
            startActivity(intent);
        }

    }

    public void fiveMode(View view) {
        Intent intent = new Intent(SinglePlayerGameModeActivity.this, SingleFiveByFiveNameOfPlayerActivity.class);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
    }
}
