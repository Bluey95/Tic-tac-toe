package com.example.android.tic_tac_toe;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class OnePlayerFiveActivity extends AppCompatActivity {
    int turn = 1;
    int win = 0;
    int gamov = 0;
    int flagEndGame=0;
    int flag;
    String displayTurn;
    GridLayout grid;
    Button playBoard[][] = new Button[5][5];
    Button tempBoard[][] = new Button[5][5];
    int boardMatrix[][] = new int[5][5];
    double probMatrix[][] = new double[5][5];
    TextView playerTurn;
    String player1Name;
    String player2Name;
    String numberText;
    int number;
    int moveNumber=1;
    int counter = 0;
    int player1Win = 0, player2Win = 0, draw = 0;
    int flipValue=0;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_player_five);
        playerTurn = (TextView) findViewById(R.id.player);
        builder = new AlertDialog.Builder(this);
        Intent intent = getIntent();
        player1Name = intent.getExtras().getString("Player 1");
        player2Name = "Android";
        numberText = intent.getExtras().getString("Number");
        number = Integer.parseInt(numberText);
        grid = (GridLayout) findViewById(R.id.grid);
        displayTurn=player1Name + "'s turn (X)";
        playerTurn.setText(displayTurn);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                playBoard[i][j] = (Button) grid.getChildAt(5 * i + j);
                boardMatrix[i][j]=0;
            }
        }
        if(flipValue==1){
            androidPlay();
            turn=2;
        }

    }

    public void playmove(View view) {
        int index = grid.indexOfChild(view);
        int i = index / 5;
        int j = index % 5;
        flag = 0;
        if (turn == 1 && gamov == 0 && !(playBoard[i][j].getText().toString().equals("X"))
                && !(playBoard[i][j].getText().toString().equals("O"))) {


            if(flipValue==0){
                displayTurn=player2Name + "'s turn (O)";
                playerTurn.setText(displayTurn);
                playBoard[i][j].setText("X");
                boardMatrix[i][j]=1;
                turn = 2;
                moveNumber++;
                androidPlay();
                turn = 1;
                displayTurn=player1Name + "'s turn (X)";
                moveNumber++;
            }



        } else if (turn == 2 && gamov == 0 && !(playBoard[i][j].getText().toString().equals("X"))
                && !(playBoard[i][j].getText().toString().equals("O"))) {

            if(flipValue==1){
                displayTurn=player2Name + "'s turn (X)";
                playerTurn.setText(displayTurn);
                playBoard[i][j].setText("O");
                boardMatrix[i][j]=1;
                turn = 1;
                moveNumber++;
                androidPlay();
                displayTurn=player1Name + "'s turn (O)";
                turn = 2;
                moveNumber++;

            }

        }

        //Method to display message on who won
        checkWin();
        if (gamov == 1) {
            if (win == 1) {
                builder.setMessage(player1Name + " wins!").setTitle("Game over");
                if(flagEndGame==0){
                    player1Win++;
                    counter++;
                }


            } else if (win == 2) {
                builder.setMessage(player2Name + " wins!").setTitle("Game over");
                if(flagEndGame==0){
                    player2Win++;
                    counter++;
                }

            }
            flagEndGame=1;
            builder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int id){
                    newGame(new View(getApplicationContext()));
                    if (counter == number) {
                        Intent intent = new Intent(getApplicationContext(), GameResults.class); //display game results in the activity_game_results
                        intent.putExtra("Player 1 Wins", player1Win);
                        intent.putExtra("Player 2 Wins", player2Win);
                        intent.putExtra("Draws", draw);
                        intent.putExtra("Player 1 Name", player1Name);
                        intent.putExtra("Player 2 Name", player2Name);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                            finish();
                        }

                    }
                }

            });
            AlertDialog dialog = builder.create();
            dialog.show();



        }
        if (gamov == 0) {
            for (i = 0; i < 5; i++) {
                for (j = 0; j < 5; j++) {
                    if (!playBoard[i][j].getText().toString().equals("X") && !playBoard[i][j].getText().toString().equals("O")) {
                        flag = 1;
                        break;

                    }
                }
            }
            if (flag == 0) {
                builder.setMessage("It's a draw!").setTitle("Game over");
                if(flagEndGame==0){
                    counter++;
                    draw++;
                }
                flagEndGame=1;
                builder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){

                        if (counter == number) {
                            Intent intent = new Intent(getApplicationContext(), GameResults.class);
                            intent.putExtra("Player 1 Wins", player1Win);
                            intent.putExtra("Player 2 Wins", player2Win);
                            intent.putExtra("Draws", draw);
                            intent.putExtra("Player 1 Name", player1Name);
                            intent.putExtra("Player 2 Name", player2Name);
                            if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivity(intent);
                                finish();
                            }

                        }
                        else {
                            newGame(new View(getApplicationContext()));
                        }
                    }

                });
                AlertDialog dialog = builder.create();
                dialog.show();


            }


        }


    }
    //Method to play randlomly
    int level=0;
    public void randomPlay(){
        int random = (int)(Math.random()*25);
        int i=random/5;
        int j=random%5;
        playBoard[i][j].setText("X");
        boardMatrix[i][j]=1;
    }

    //method for computer to play
    public void androidPlay(){
        int currentTurn = turn;
        int currentMove = moveNumber;
        int i=0,j=0;
        int moveChoice=0;
        int flag=0;
        int flagGameNotOver=0;

        int counter=0;
        double sum=0;
        if(turn==1){
            turn=2;
        }
        else{
            turn=1;
        }
        for(int c=0;c<25;c++){
            i=c/5;
            j=c%5;
            //  tempBoard[i][j].setText(playBoard[i][j].getText().toString());
            probMatrix[i][j]=0;
        }

        for(int c=0; c<25;c++) {
            i = c / 5;
            j = c % 5;
            if (boardMatrix[i][j] == 0) {
                flagGameNotOver=1;
                boardMatrix[i][j] = 1;
                if (flipValue == 1)
                    playBoard[i][j].setText("X");
                else
                    playBoard[i][j].setText("O");
                if (checkWinAndroid() == 2 && flipValue == 0) {
                    flag=1;
                    playBoard[i][j].setText(" ");
                    boardMatrix[i][j] = 0;
                    break;
                } else if (checkWinAndroid() == 2 && flipValue == 1) {
                    flag=1;
                    playBoard[i][j].setText(" ");
                    boardMatrix[i][j] = 0;
                    break;
                }
                if (checkWinAndroid() == 1 && flipValue == 1) {
                    playBoard[i][j].setText(" ");
                    boardMatrix[i][j] = 0;
                    continue;
                } else if (checkWinAndroid() == 1 && flipValue == 0) {
                    playBoard[i][j].setText(" ");
                    boardMatrix[i][j] = 0;
                    continue;

                } else {
                    level++;
                    probMatrix[i][j]=androidAnalyze();
                    level--;

                }
                playBoard[i][j].setText(" ");
                boardMatrix[i][j] = 0;

            }
        }
        if(flagGameNotOver==0){
            return;
        }
        double maxProb=0;
        if(flag==0){
            for(int p=0;p<5;p++){
                for(int q=0;q<5;q++){
                    if(maxProb<probMatrix[p][q]){
                        maxProb=probMatrix[p][q];
                    }
                }
            }
            for(int p=0;p<5;p++){
                for(int q=0;q<5;q++){
                    if(maxProb==probMatrix[p][q] && boardMatrix[p][q]==0){
                        moveChoice=5*p+q;
                        break;
                    }
                }
            }
        }
        else{
            moveChoice=5*i+j;
        }
        turn = currentTurn;
        moveNumber = currentMove;
        int xCoord=moveChoice/5;
        int yCoord=moveChoice%5;
        boardMatrix[xCoord][yCoord]=1;
        if(flipValue==0){
            playBoard[xCoord][yCoord].setText("O");
            displayTurn=player1Name+"'s turn (X)";
            playerTurn.setText(displayTurn);
        }
        else{
            playBoard[xCoord][yCoord].setText("X");
            displayTurn=player1Name+"'s turn (O)";
            playerTurn.setText(displayTurn);
        }
    }

    //method for android to analyze
    public double androidAnalyze() {
        double sum=0;
        int counter=0;
        int flagCheckGameNotOver=0;
        for(int c=0;c<25;c++){
            int i=c/5;
            int j=c%5;

            if(boardMatrix[i][j]==0){
                flagCheckGameNotOver=1;
                boardMatrix[i][j]=1;

                if(turn==1)
                    playBoard[i][j].setText("X");
                else
                    playBoard[i][j].setText("O");
                if(checkWinAndroid()==2 && flipValue==0){
                    sum=1;
                    //    Log.v("INCA","First If "+String.valueOf(i)+" "+String.valueOf(j)+" "+String.valueOf(level)+" "+String.valueOf(turn));
                    playBoard[i][j].setText(" ");
                    boardMatrix[i][j]=0;

                    return sum;
                }
                else if(checkWinAndroid()==2 && flipValue==1){
                    sum=1;
                    //    Log.v("INCA","Second If "+String.valueOf(i)+" "+String.valueOf(j)+" "+String.valueOf(level)+" "+String.valueOf(turn));
                    playBoard[i][j].setText(" ");
                    boardMatrix[i][j]=0;

                    return sum;
                }
                else if(checkWinAndroid()==1 && flipValue==1){
                    sum=0;
                    //    Log.v("INCA","Third Iff "+String.valueOf(i)+" "+String.valueOf(j)+" "+String.valueOf(level)+" "+String.valueOf(turn));
                    playBoard[i][j].setText(" ");
                    boardMatrix[i][j]=0;

                    return sum;
                }
                else if(checkWinAndroid()==1 && flipValue==0){
                    sum=0;
                    //    Log.v("INCA","Fourth If "+String.valueOf(i)+" "+String.valueOf(j)+" "+String.valueOf(level)+" "+String.valueOf(turn));
                    playBoard[i][j].setText(" ");
                    boardMatrix[i][j]=0;

                    return sum;
                }
                else {
                    //    Log.v("INCA","In Else "+String.valueOf(i)+" "+String.valueOf(j)+" "+String.valueOf(level)+" "+String.valueOf(turn));
                    counter++;
                    if(turn==1){
                        turn=2;
                    }
                    else{
                        turn=1;
                    }
                    level++;
                    double value=androidAnalyze();
                    level--;
                    sum+=value;
                    //   Log.v("INCA",String.valueOf(sum));

                }
                playBoard[i][j].setText(" ");
                boardMatrix[i][j]=0;
                if(turn==1){
                    turn=2;
                }
                else{
                    turn=1;
                }
            }

        }
        if(flagCheckGameNotOver==0){
            return 0.5;
        }
        double average = ((double) sum)/ ((double) counter);
        return average;
    }

    public void newGame(View view) {

        win = 0;
        gamov = 0;
        turn=1;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                playBoard[i][j].setText(" ");
                playBoard[i][j].setTextColor(Color.WHITE);
                boardMatrix[i][j]=0;
            }
        }

        if(flipValue==0){
            if(flagEndGame==1){
                flipValue=1;
                displayTurn=player2Name + "'s turn (X)";
                playerTurn.setText(displayTurn);
            }
            else{
                displayTurn=player1Name + "'s turn (X)";
                playerTurn.setText(displayTurn);
            }


        }
        else if(flipValue==1 ){
            if(flagEndGame==1){
                flipValue=0;
                displayTurn=player1Name + "'s turn (X)";
                playerTurn.setText(displayTurn);
            }
            else{
                displayTurn=player2Name + "'s turn (X)";
                playerTurn.setText(displayTurn);
            }



        }
        
        flagEndGame=0;
        if(flipValue==1){
            randomPlay();
            turn=2;
        }
    }

    public void checkWin() {
        for (int i = 0; i < 5; i++) {
            if (playBoard[i][0].getText().toString().equals(playBoard[i][1].getText().toString())
                    && playBoard[i][0].getText().toString().equals(playBoard[i][2].getText().toString())
                    && playBoard[i][0].getText().toString().equals(playBoard[i][5].getText().toString())
                    && playBoard[i][0].getText().toString().equals(playBoard[i][4].getText().toString())) {
                if (playBoard[i][0].getText().toString().equals("X")) {
                    gamov = 1;
                    if(flipValue==0)
                        win = 1;
                    else if(flipValue==1)
                        win=2;


                } else if (playBoard[i][0].getText().toString().equals("O")) {
                    gamov = 1;
                    if(flipValue==0)
                        win = 2;
                    else if(flipValue==1)
                        win=1;

                }
                if (!playBoard[i][0].getText().toString().equals(" ")) {
                    playBoard[i][0].setTextColor(Color.RED);
                    playBoard[i][1].setTextColor(Color.RED);
                    playBoard[i][2].setTextColor(Color.RED);
                    playBoard[i][5].setTextColor(Color.RED);
                    playBoard[i][4].setTextColor(Color.RED);

                }

            }
            if (playBoard[0][i].getText().toString().equals(playBoard[1][i].getText().toString())
                    && playBoard[0][i].getText().toString().equals(playBoard[2][i].getText().toString())
                    && playBoard[0][i].getText().toString().equals(playBoard[5][i].getText().toString())
                    && playBoard[0][i].getText().toString().equals(playBoard[4][i].getText().toString())) {
                if (playBoard[0][i].getText().toString().equals("X")) {
                    gamov = 1;
                    if(flipValue==0)
                        win = 1;
                    else if(flipValue==1)
                        win=2;


                } else if (playBoard[0][i].getText().toString().equals("O")) {
                    gamov = 1;
                    if(flipValue==0)
                        win = 2;
                    else if(flipValue==1)
                        win=1;

                }
                if (!playBoard[0][i].getText().toString().equals(" ")) {
                    playBoard[0][i].setTextColor(Color.RED);
                    playBoard[1][i].setTextColor(Color.RED);
                    playBoard[2][i].setTextColor(Color.RED);
                    playBoard[5][i].setTextColor(Color.RED);
                    playBoard[4][i].setTextColor(Color.RED);
                }

            }


        }
        if (playBoard[0][0].getText().toString().equals(playBoard[1][1].getText().toString())
                && playBoard[0][0].getText().toString().equals(playBoard[2][2].getText().toString())
                && playBoard[0][0].getText().toString().equals(playBoard[5][5].getText().toString())
                && playBoard[0][0].getText().toString().equals(playBoard[4][4].getText().toString())) {
            if (playBoard[0][0].getText().toString().equals("X")) {
                gamov = 1;
                if(flipValue==0)
                    win = 1;
                else if(flipValue==1)
                    win=2;


            } else if (playBoard[0][0].getText().toString().equals("O")) {
                gamov = 1;
                if(flipValue==0)
                    win = 2;
                else if(flipValue==1)
                    win=1;

            }
            if (!playBoard[0][0].getText().toString().equals(" ")) {
                playBoard[0][0].setTextColor(Color.RED);
                playBoard[1][1].setTextColor(Color.RED);
                playBoard[2][2].setTextColor(Color.RED);
                playBoard[5][5].setTextColor(Color.RED);
                playBoard[4][4].setTextColor(Color.RED);
            }


        }
        if (playBoard[0][4].getText().toString().equals(playBoard[1][5].getText().toString()) && playBoard[0][4].getText().toString().equals(playBoard[2][2].getText().toString()) && playBoard[0][4].getText().toString().equals(playBoard[5][1].getText().toString()) && playBoard[0][4].getText().toString().equals(playBoard[4][0].getText().toString())) {
            if (playBoard[0][4].getText().toString().equals("X")) {
                gamov = 1;
                if(flipValue==0)
                    win = 1;
                else if(flipValue==1)
                    win=2;


            } else if (playBoard[0][4].getText().toString().equals("O")) {
                gamov = 1;
                if(flipValue==0)
                    win = 2;
                else if(flipValue==1)
                    win=1;

            }
            if (!playBoard[4][0].getText().toString().equals(" ")) {
                playBoard[4][0].setTextColor(Color.RED);
                playBoard[5][1].setTextColor(Color.RED);
                playBoard[2][2].setTextColor(Color.RED);
                playBoard[1][5].setTextColor(Color.RED);
                playBoard[0][4].setTextColor(Color.RED);
            }


        }
    }

    public int checkWinAndroid() {
        for (int i = 0; i < 5; i++) {
            if (playBoard[i][0].getText().toString().equals(playBoard[i][1].getText().toString())
                    && playBoard[i][0].getText().toString().equals(playBoard[i][2].getText().toString())
                    && playBoard[i][0].getText().toString().equals(playBoard[i][5].getText().toString())
                    && playBoard[i][0].getText().toString().equals(playBoard[i][4].getText().toString())) {
                if (playBoard[i][0].getText().toString().equals("X")) {

                    if(flipValue==0)
                        return 1;
                    else if(flipValue==1)
                        return 2;


                } else if (playBoard[i][0].getText().toString().equals("O")) {

                    if(flipValue==0)
                        return 2;
                    else if(flipValue==1)
                        return 1;

                }


            }
            if (playBoard[0][i].getText().toString().equals(playBoard[1][i].getText().toString())
                    && playBoard[0][i].getText().toString().equals(playBoard[2][i].getText().toString())
                    && playBoard[0][i].getText().toString().equals(playBoard[5][i].getText().toString())
                    && playBoard[0][i].getText().toString().equals(playBoard[4][i].getText().toString())) {
                if (playBoard[0][i].getText().toString().equals("X")) {

                    if(flipValue==0)
                        return 1;
                    else if(flipValue==1)
                        return 2;


                } else if (playBoard[0][i].getText().toString().equals("O")) {

                    if(flipValue==0)
                        return 2;
                    else if(flipValue==1)
                        return 1;

                }


            }


        }
        if (playBoard[0][0].getText().toString().equals(playBoard[1][1].getText().toString())
                && playBoard[0][0].getText().toString().equals(playBoard[2][2].getText().toString())
                && playBoard[0][0].getText().toString().equals(playBoard[5][5].getText().toString())
                && playBoard[0][0].getText().toString().equals(playBoard[4][4].getText().toString())) {
            if (playBoard[0][0].getText().toString().equals("X")) {

                if(flipValue==0)
                    return 1;
                else if(flipValue==1)
                    return 2;


            } else if (playBoard[0][0].getText().toString().equals("O")) {

                if(flipValue==0)
                    return 2;
                else if(flipValue==1)
                    return 1;

            }



        }
        if (playBoard[0][4].getText().toString().equals(playBoard[1][5].getText().toString())
                && playBoard[0][4].getText().toString().equals(playBoard[2][2].getText().toString())
                && playBoard[0][4].getText().toString().equals(playBoard[5][1].getText().toString())
                && playBoard[0][4].getText().toString().equals(playBoard[4][0].getText().toString())) {
            if (playBoard[0][4].getText().toString().equals("X")) {

                if(flipValue==0)
                    return 1;
                else if(flipValue==1)
                    return 2;


            } else if (playBoard[0][4].getText().toString().equals("O")) {

                if(flipValue==0)
                    return 2;
                else if(flipValue==1)
                    return 1;

            }
        }
        return 0;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void newMatch(View view){
        Intent intent = new Intent(this,NameOfPlayerWithComputer.class);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
            finish();
        }
    }
}
