package com.vice.vicegames;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.vice.vicegames.models.User;
import com.vice.vicegames.services.TokenService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class AccountActivity extends AppCompatActivity {

    RelativeLayout accountLayout;
    Button buttonEdit;
    TextView nameField, emailField;
    TextView vkField, telegramField, instagramField;

    private void loadUserData(){
        TokenService tokenService = TokenService.getTokenService();

        OkHttpClient client = new OkHttpClient();
        String url = "http://vicegames.ru/integration/userinfo?token=" + tokenService.getToken() +
                "&email=" + tokenService.getEmail() + "&device_name=AndroidApp";

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Snackbar.make(
                        accountLayout, "Ошибка авторизации: " + e.getMessage(),
                        Snackbar.LENGTH_SHORT
                ).show();
                startActivity(new Intent(AccountActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    ResponseBody body = response.body();
                    if (body != null) {
                        String userData = body.string();
                        try {
                            JSONObject userDataJson = new JSONObject(userData);
                            User user = new User(userDataJson);
                            displayUserData(user);
                        } catch (JSONException e) {
                            Snackbar.make(
                                    accountLayout, "Ошибка на сервере",
                                    Snackbar.LENGTH_SHORT
                            ).show();
                            startActivity(new Intent(AccountActivity.this, MainActivity.class));
                            finish();
                            return;
                        }
                    } else {
                        Snackbar.make(
                                accountLayout, "Ошибка на сервере",
                                Snackbar.LENGTH_SHORT
                        ).show();
                        startActivity(new Intent(AccountActivity.this, MainActivity.class));
                        finish();
                        return;
                    }

                    Snackbar.make(
                            accountLayout, "Успешная авторизация: " + response.message(),
                            Snackbar.LENGTH_SHORT
                    ).show();
                } else {
                    Snackbar.make(
                            accountLayout, "Ошибка авторизации: " + response.message(),
                            Snackbar.LENGTH_SHORT
                    ).show();
                    startActivity(new Intent(AccountActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }

    private void displayUserData(User user){

        AccountActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                nameField.setText(user.getName());
                emailField.append(user.getEmail());

                vkField.append(user.getVkURL());
                telegramField.append(user.getTelegramURL());
                instagramField.append(user.getInstagramURL());
            }
        });
    }

    private void showEditActivity(){
        startActivity(new Intent(AccountActivity.this, EditActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        accountLayout = findViewById(R.id.activityAccountLayout);
        buttonEdit = findViewById(R.id.buttonEdit);

        nameField = findViewById(R.id.nameField);
        emailField = findViewById(R.id.emailField);

        vkField = findViewById(R.id.vkField);
        telegramField = findViewById(R.id.telegramField);
        instagramField = findViewById(R.id.instagramField);

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditActivity();
            }
        });

        loadUserData();
    }
}
