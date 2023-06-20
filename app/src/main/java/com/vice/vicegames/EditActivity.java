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

public class EditActivity extends AppCompatActivity {

    RelativeLayout editLayout;
    Button buttonSave, buttonBack;
    TextView nameField;
    EditText vkField, telegramField, instagramField;

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
                        editLayout, "Ошибка авторизации: " + e.getMessage(),
                        Snackbar.LENGTH_SHORT
                ).show();
                startActivity(new Intent(EditActivity.this, MainActivity.class));
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
                                    editLayout, "Ошибка на сервере",
                                    Snackbar.LENGTH_SHORT
                            ).show();
                            startActivity(new Intent(EditActivity.this, MainActivity.class));
                            finish();
                            return;
                        }
                    } else {
                        Snackbar.make(
                                editLayout, "Ошибка на сервере",
                                Snackbar.LENGTH_SHORT
                        ).show();
                        startActivity(new Intent(EditActivity.this, MainActivity.class));
                        finish();
                        return;
                    }

                    Snackbar.make(
                            editLayout, "Успешная авторизация: " + response.message(),
                            Snackbar.LENGTH_SHORT
                    ).show();
                } else {
                    Snackbar.make(
                            editLayout, "Ошибка авторизации: " + response.message(),
                            Snackbar.LENGTH_SHORT
                    ).show();
                    startActivity(new Intent(EditActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }

    private void displayUserData(User user){

        EditActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                vkField.append(user.getVkURL());
                telegramField.append(user.getTelegramURL());
                instagramField.append(user.getInstagramURL());
            }
        });
    }

    private void save(String new_vk_url, String new_telegram_url, String new_instagram_url){
        TokenService tokenService = TokenService.getTokenService();

        if (new_vk_url == null || new_vk_url == ""){
            new_vk_url = "d";
        }
        if (new_telegram_url == null || new_telegram_url == ""){
            new_telegram_url = "d";
        }
        if (new_instagram_url == null || new_instagram_url == ""){
            new_instagram_url = "d";
        }

        OkHttpClient client = new OkHttpClient();
        String url = "http://vicegames.ru/integration/changeuserlinks?token=" + tokenService.getToken() +
                "&email=" + tokenService.getEmail() + "&device_name=AndroidApp" +
                "&new_vk_url=" + new_vk_url + "&new_telegram_url=" + new_telegram_url +
                "&new_instagram_url=" + new_instagram_url;

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Snackbar.make(
                        editLayout, "Ошибка авторизации: " + e.getMessage(),
                        Snackbar.LENGTH_SHORT
                ).show();
                startActivity(new Intent(EditActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    startActivity(new Intent(EditActivity.this, AccountActivity.class));
                    finish();
                } else {
                    Snackbar.make(
                            editLayout, "Ошибка авторизации: " + response.message(),
                            Snackbar.LENGTH_SHORT
                    ).show();
                    startActivity(new Intent(EditActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }

    private void showAccountActivity(){
        startActivity(new Intent(EditActivity.this, AccountActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editLayout = findViewById(R.id.activityEditLayout);
        buttonSave = findViewById(R.id.buttonSave);
        buttonBack = findViewById(R.id.buttonBack);

        nameField = findViewById(R.id.nameField);

        vkField = findViewById(R.id.vkField);
        telegramField = findViewById(R.id.telegramField);
        instagramField = findViewById(R.id.instagramField);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(
                        vkField.getText().toString(),
                        telegramField.getText().toString(),
                        instagramField.getText().toString()
                );
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAccountActivity();
            }
        });

        loadUserData();
    }
}
