package com.vice.vicegames;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    RelativeLayout activityMainLayout;
    Button buttonSignIn, buttonRegister;
    EditText emailField, passwordField;

    private void login(String email, String password){
        if(TextUtils.isEmpty(email)){
            Snackbar.make(
                    activityMainLayout, "Email не введен", Snackbar.LENGTH_SHORT
            ).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Snackbar.make(
                    activityMainLayout, "Пароль не введен", Snackbar.LENGTH_SHORT
            ).show();
            return;
        }

        OkHttpClient client = new OkHttpClient();
        // String url = "http://vicegames.ru/integration/userinfo?token=1&email=1&device_name=1";
        String url = "http://vicegames.ru/integration/login?email=" + email +
                "&password=" + password + "&device_name=AndroidApp";

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Snackbar.make(
                        activityMainLayout, "Ошибка авторизации: " + e.getMessage(),
                        Snackbar.LENGTH_SHORT
                ).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    Snackbar.make(
                            activityMainLayout, "Успешная авторизация: " + response.message(),
                            Snackbar.LENGTH_SHORT
                    ).show();
                } else {
                    Snackbar.make(
                            activityMainLayout, "Ошибка авторизации: " + response.message(),
                            Snackbar.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }

    private void showRegisterActivity(){
        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainLayout = findViewById(R.id.activityMainLayout);
        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonRegister = findViewById(R.id.buttonRegister);
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(emailField.getText().toString(), passwordField.getText().toString());
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterActivity();
            }
        });

    }
}