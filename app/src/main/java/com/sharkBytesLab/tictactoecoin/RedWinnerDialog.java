package com.sharkBytesLab.tictactoecoin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

public class RedWinnerDialog extends Dialog {

    public RedWinnerDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_winner_dialog);

        final AppCompatButton rate = findViewById(R.id.restartRed);

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dismiss();

            }
        });

    }
}