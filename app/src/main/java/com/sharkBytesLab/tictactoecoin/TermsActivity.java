package com.sharkBytesLab.tictactoecoin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class TermsActivity extends AppCompatActivity {

    private WebView termsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        getSupportActionBar().hide();
        termsView = findViewById(R.id.termsView);


        try {

            termsView.getSettings().setJavaScriptEnabled(true);
            termsView.loadUrl("file:///android_asset/terms.html");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(TermsActivity.this, MenuActivity.class));
        finish();
    }


}