package com.example.android.tic_tac_toe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }



    public void singlePlayer(View view) {
        Intent intent = new Intent(MainActivity.this, SinglePlayerGameModeActivity.class);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
    }

    public void twoPlayers(View view) {
        Intent intent = new Intent(MainActivity.this, TwoPlayerGameModeActivity.class);
        if(intent.resolveActivity(getPackageManager())!=null) {
            startActivity(intent);
        }

    }

}




