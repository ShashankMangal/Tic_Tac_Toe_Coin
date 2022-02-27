package com.sharkBytesLab.tictactoecoin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class PolicyActivity extends AppCompatActivity {

    private WebView policyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);

        getSupportActionBar().hide();
        policyView = findViewById(R.id.policyView);


        try {

            policyView.getSettings().setJavaScriptEnabled(true);
            policyView.loadUrl("file:///android_asset/policy.html");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(PolicyActivity.this, MenuActivity.class));
        finish();
    }
}