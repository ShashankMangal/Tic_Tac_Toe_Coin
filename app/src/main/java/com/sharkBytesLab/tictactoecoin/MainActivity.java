package com.sharkBytesLab.tictactoecoin;

import static com.sharkBytesLab.tictactoecoin.R.drawable.*;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.sharkBytesLab.tictactoecoin.R.color;

public class MainActivity extends AppCompatActivity {


    private int activePlayer = 0;
    public boolean gameActive = true;
    private int[] gameState = {2,2,2,2,2,2,2,2,2};
    private int[][] winningState = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    private ConstraintLayout mainLayout;
    private Button resetBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        mainLayout = findViewById(R.id.mainLayout);
        resetBtn = findViewById(R.id.resetBtn);

        AnimationDrawable animationDrawable = (AnimationDrawable) mainLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1500);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();


        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity.this, "Game Reset.", Toast.LENGTH_SHORT).show();
                activePlayer = 0;
                gameActive = true;
                for(int i=0;i<gameState.length;i++)
                {
                    gameState[i] = 2;
                }
                GridLayout grd = findViewById(R.id.gridLayout);

                for(int i=0;i<9;i++)
                {
                    ImageView counts = (ImageView) grd.getChildAt(i);;
                    counts.setImageDrawable(null);
                }


            }
        });


    }

    public void tapped(View view)
    {

        Log.e("gameActive", String.valueOf(gameActive));
        Log.e("activePlayer", String.valueOf(activePlayer));
        ImageView count = (ImageView) view;
        int tap = Integer.parseInt(count.getTag().toString());

        if(gameActive && gameState[tap] == 2)
        {
            count.setTranslationY(-1500);


            gameState[tap] = activePlayer;

            if(activePlayer == 0)
            {
                count.setImageResource(yellow);
                activePlayer = 1;
            }
            else
            {
                count.setImageResource(red);
                activePlayer = 0;
            }

            count.animate().translationYBy(1500).rotation(3600).setDuration(10);

            for(int[] winningstate : winningState)
            {
                if (gameState[winningstate[0]] == gameState[winningstate[1]] && gameState[winningstate[1]] == gameState[winningstate[2]] && gameState[winningstate[1]]!= 2 )
                {



                    if(activePlayer == 1)
                    {

                        yellowWinnerDialog();
                        activePlayer = 0;
                        gameActive = true;
                        for(int i=0;i<gameState.length;i++)
                        {
                            gameState[i] = 2;
                        }
                        GridLayout grd = findViewById(R.id.gridLayout);

                        for(int i=0;i<9;i++)
                        {
                            ImageView counts = (ImageView) grd.getChildAt(i);;
                            counts.setImageDrawable(null);
                        }



                    }
                    else
                    {

                        redWinnerDialog();
                        activePlayer = 0;
                        gameActive = true;
                        for(int i=0;i<gameState.length;i++)
                        {
                            gameState[i] = 2;
                        }
                        GridLayout grd = findViewById(R.id.gridLayout);

                        for(int i=0;i<9;i++)
                        {
                            ImageView counts = (ImageView) grd.getChildAt(i);;
                            counts.setImageDrawable(null);
                        }

                    }



                }
            }

        }

    }

    private void yellowWinnerDialog()
    {

       WinnerDialog winnerDialog = new WinnerDialog(MainActivity.this);
        winnerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        winnerDialog.setCancelable(false);
        winnerDialog.show();


    }

    private void redWinnerDialog()
    {

        RedWinnerDialog redWinnerDialog = new RedWinnerDialog(MainActivity.this);
        redWinnerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        redWinnerDialog.setCancelable(false);
        redWinnerDialog.show();


    }

    public void gameReset()
    {
        activePlayer = 0;
        gameActive = true;
        for(int i=0;i<gameState.length;i++)
        {
            gameState[i] = 2;
        }
        GridLayout grd = findViewById(R.id.gridLayout);

        for(int i=0;i<9;i++)
        {
            ImageView counts = (ImageView) grd.getChildAt(i);;
            counts.setImageDrawable(null);
        }

        Toast.makeText(this, "Game Reset.", Toast.LENGTH_SHORT).show();



    }

}