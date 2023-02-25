package com.sharkBytesLab.tictactoecoin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;

import java.util.concurrent.TimeUnit;

public class MenuActivity extends AppCompatActivity implements MaxAdListener {

    private CardView share, rate, privacy, terms, feedback, supportUs;
    private MaxInterstitialAd interstitialAd;
    private int retryAttempt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getSupportActionBar().hide();

        share = findViewById(R.id.share);
        rate = findViewById(R.id.rate_us);
        privacy = findViewById(R.id.policy);
        terms = findViewById(R.id.terms);
        feedback = findViewById(R.id.feedback);
        supportUs = findViewById(R.id.supportUs);

        String email = "shashankmangal10@gmail.com";
        String subject = "Feedback of Tic Tac Toe Coin Game.";

        interstitialAd = new MaxInterstitialAd( "dca34227c5952b50", this );
        interstitialAd.setListener( this );
        interstitialAd.loadAd();

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String shareBody = "Hey, I'm playing Tic Tac Toe Coin and its very good, do you want to enjoy playing it." + "\n" +
                        "Download from Play Store\n" + "https://play.google.com/store/apps/details?id=" + getPackageName();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(intent);

            }
        });


        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName());
                Intent i = new Intent(Intent.ACTION_VIEW, uri);

                try {
                    startActivity(i);
                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(), "Error :" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent i = new Intent(Intent.ACTION_SENDTO);
                    i.putExtra(Intent.EXTRA_EMAIL, new String[] {email});
                    i.putExtra(Intent.EXTRA_SUBJECT,subject);
                    i.setData(Uri.parse("mailto:"));
                    startActivity(i);

                    Toast.makeText(getApplicationContext(), "Type your Feedback here.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MenuActivity.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        supportUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ( interstitialAd.isReady() )
                {
                    interstitialAd.showAd();
                }
            }
        });

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MenuActivity.this, PolicyActivity.class));
                finish();

            }
        });

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MenuActivity.this, TermsActivity.class));
                finish();

            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(MenuActivity.this, MainActivity.class));
        finish();

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
}