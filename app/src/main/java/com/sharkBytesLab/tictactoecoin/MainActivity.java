package com.sharkBytesLab.tictactoecoin;

import static com.sharkBytesLab.tictactoecoin.R.drawable.*;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.sharkBytesLab.tictactoecoin.R.color;

import java.util.concurrent.TimeUnit;

import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;

public class MainActivity extends AppCompatActivity implements MaxAdListener{


    private int activePlayer = 0;
    public boolean gameActive = true;
    private int[] gameState = {2,2,2,2,2,2,2,2,2};
    private int[][] winningState = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    private ConstraintLayout mainLayout;
    private Button resetBtn;
    private ImageView menu;
    private MaxAdView adView;
    private MaxInterstitialAd interstitialAd;
    private int retryAttempt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        mainLayout = findViewById(R.id.mainLayout);
        resetBtn = findViewById(R.id.resetBtn);
        menu = findViewById(R.id.menu);
        adView = findViewById(R.id.applovinAd);

        AnimationDrawable animationDrawable = (AnimationDrawable) mainLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1500);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

        // Please make sure to set the mediation provider value to "max" to ensure proper functionality
        AppLovinSdk.getInstance( this ).setMediationProvider( "max" );
        AppLovinSdk.initializeSdk( this, new AppLovinSdk.SdkInitializationListener() {
            @Override
            public void onSdkInitialized(final AppLovinSdkConfiguration configuration)
            {

            }
        } );

        adView.loadAd();
        interstitialAd = new MaxInterstitialAd( "0ddea73fe38117c5", this );
        interstitialAd.setListener( this );
        interstitialAd.loadAd();

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

                try {
                    if (interstitialAd.isReady()) {
                        interstitialAd.showAd();
                    }
                } catch (Exception e) {
                   showToast(e.getMessage().toString());
                   Log.v("Reset Error", e.getMessage().toString());
                }

            }
        });


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, MenuActivity.class));
                finish();

            }
        });


    }


    private void showToast(String s) {
        MotionToast.Companion.createColorToast(this,
                s,
                "Click on SKIP TIMER",
                MotionToastStyle.SUCCESS,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(this, R.font.helvetica_regular));

    }

    @Override
    public void onAdLoaded(final MaxAd maxAd)
    {
        // Interstitial ad is ready to be shown. interstitialAd.isReady() will now return 'true'
        // Reset retry attempt
        retryAttempt = 0;
    }

    @Override
    public void onAdLoadFailed(final String adUnitId, final MaxError error)
    {
        // Interstitial ad failed to load
        // AppLovin recommends that you retry with exponentially higher delays up to a maximum delay (in this case 64 seconds)

        retryAttempt++;
        long delayMillis = TimeUnit.SECONDS.toMillis( (long) Math.pow( 2, Math.min( 6, retryAttempt ) ) );

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                interstitialAd.loadAd();
            }
        }, delayMillis );
    }

    @Override
    public void onAdDisplayFailed(final MaxAd maxAd, final MaxError error)
    {
        // Interstitial ad failed to display. AppLovin recommends that you load the next ad.
        interstitialAd.loadAd();
    }

    @Override
    public void onAdDisplayed(final MaxAd maxAd) {}

    @Override
    public void onAdClicked(final MaxAd maxAd) {}

    @Override
    public void onAdHidden(final MaxAd maxAd)
    {
        // Interstitial ad is hidden. Pre-load the next ad
        interstitialAd.loadAd();
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