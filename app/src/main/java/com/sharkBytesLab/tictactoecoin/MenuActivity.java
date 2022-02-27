package com.sharkBytesLab.tictactoecoin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    private CardView share, rate, privacy, terms, feedback;

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

        String email = "shashankmangal10@gmail.com";
        String subject = "Feedback of Tic Tac Toe Coin Game.";

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
}