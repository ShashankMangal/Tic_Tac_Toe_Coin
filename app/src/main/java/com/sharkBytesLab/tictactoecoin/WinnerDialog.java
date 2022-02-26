package com.sharkBytesLab.tictactoecoin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

public class WinnerDialog extends Dialog {

    public WinnerDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner_dialog);

        final AppCompatButton rate = findViewById(R.id.restart);

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            dismiss();

            }
        });

    }
}