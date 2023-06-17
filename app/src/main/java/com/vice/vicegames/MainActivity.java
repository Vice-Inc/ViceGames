package com.vice.vicegames;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    RelativeLayout activityMainLayout;
    Button buttonSignIn;
    Button buttonRegister;

    private void showSignInWindow(){

    }

    private void showRegisterWindow(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainLayout = findViewById(R.id.activityMainLayout);
        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonRegister = findViewById(R.id.buttonRegister);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignInWindow();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterWindow();
            }
        });

    }
}