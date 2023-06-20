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
import okhttp3.ResponseBody;

public class RegisterActivity extends AppCompatActivity {

    RelativeLayout registerLayout;
    Button buttonSignIn, buttonRegister;
    EditText nameField, emailField, passwordField, confirmPasswordField;

    private void register(String name, String email, String password, String  confirmPassword){
        if(TextUtils.isEmpty(email)){
            Snackbar.make(
                    registerLayout, "Email не введен", Snackbar.LENGTH_SHORT
            ).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Snackbar.make(
                    registerLayout, "Пароль не введен", Snackbar.LENGTH_SHORT
            ).show();
            return;
        }

        OkHttpClient client = new OkHttpClient();
        String url = "http://vicegames.ru/integration/register?name=" + name +
                "&email=" + email +
                "&password=" + password + "&confirm_password=" + confirmPassword;

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Snackbar.make(
                        registerLayout, "Ошибка регистрации: " + e.getMessage(),
                        Snackbar.LENGTH_SHORT
                ).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    ResponseBody body = response.body();
                    if (body != null) {
                        String token = body.string();
                    } else {
                        Snackbar.make(
                                registerLayout, "Ошибка на сервере",
                                Snackbar.LENGTH_SHORT
                        ).show();
                        return;
                    }

                    Snackbar.make(
                            registerLayout, "Успешная регистрация: " + response.message(),
                            Snackbar.LENGTH_SHORT
                    ).show();

                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                } else {
                    Snackbar.make(
                            registerLayout, "Ошибка регистрации: " + response.message(),
                            Snackbar.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }

    private void showMainActivity(){
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerLayout = findViewById(R.id.activityRegisterLayout);
        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonRegister = findViewById(R.id.buttonRegister);
        nameField = findViewById(R.id.nameField);
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        confirmPasswordField = findViewById(R.id.confirmPasswordField);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(
                        nameField.getText().toString(),
                        emailField.getText().toString(),
                        passwordField.getText().toString(),
                        confirmPasswordField.getText().toString()
                );
            }
        });

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMainActivity();
            }
        });
    }
}
