package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin, btnToRegister;

    String API_URL = "https://fakestoreapi.com/users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnToRegister = findViewById(R.id.btnToRegister);

        btnLogin.setOnClickListener(view -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                if (!loginOffline(username, password)) {
                    loginWithAPI(username, password);
                }
            }
        });

        btnToRegister.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    private boolean loginOffline(String username, String password) {
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String savedUsername = prefs.getString("username", "");
        String savedPassword = prefs.getString("password", "");

        if (username.equals(savedUsername) && password.equals(savedPassword)) {
            Toast.makeText(this, "Đăng nhập thành công (Offline)", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            return true;
        }
        return false;
    }

    private void loginWithAPI(String username, String password) {
        Toast.makeText(this, "Đang kiểm tra với API...", Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, API_URL, null,
                response -> {
                    boolean isAuthenticated = false;

                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JsonObject user = JsonParser.parseString(response.get(i).toString()).getAsJsonObject();
                            String userApi = user.get("username").getAsString();
                            String passApi = user.get("password").getAsString();

                            if (username.equals(userApi) && password.equals(passApi)) {
                                isAuthenticated = true;
                                break;
                            }
                        }

                        if (isAuthenticated) {
                            Toast.makeText(this, "Đăng nhập thành công (API)", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(this, HomeActivity.class));
                            finish();
                        } else {
                            Toast.makeText(this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(this, "Lỗi xử lý dữ liệu API", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Lỗi kết nối API: " + error.getMessage(), Toast.LENGTH_LONG).show()
        );

        queue.add(request);
    }
}
