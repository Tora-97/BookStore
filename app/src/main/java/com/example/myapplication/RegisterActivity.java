package com.example.myapplication;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    EditText etUsername, etEmail, etPassword, etConfirmPassword;
    Button btnRegister, btnToLogin;
    ImageView ivShowPassword, ivShowConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnToLogin = findViewById(R.id.btnToLogin);
        ivShowPassword = findViewById(R.id.ivShowPassword);
        ivShowConfirmPassword = findViewById(R.id.ivShowConfirmPassword);

        ivShowPassword.setOnClickListener(v -> togglePasswordVisibility(etPassword, ivShowPassword));
        ivShowConfirmPassword.setOnClickListener(v -> togglePasswordVisibility(etConfirmPassword, ivShowConfirmPassword));

        btnRegister.setOnClickListener(v -> {
            if (validateForm()) {

                SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("username", etUsername.getText().toString().trim());
                editor.putString("password", etPassword.getText().toString().trim());
                editor.apply();

                Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
        });

        btnToLogin.setOnClickListener(v -> finish());
    }

    private void togglePasswordVisibility(EditText editText, ImageView imageView) {
        if (editText.getTransformationMethod() instanceof PasswordTransformationMethod) {
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            imageView.setImageResource(R.drawable.ic_eye);
        } else {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            imageView.setImageResource(R.drawable.ic_eye_off);
        }
        editText.setSelection(editText.getText().length());
    }

    private boolean validateForm() {
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            etUsername.setError("Vui lòng nhập tên đăng nhập");
            etUsername.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Email không hợp lệ");
            etEmail.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(password) || password.length() < 6) {
            etPassword.setError("Mật khẩu ít nhất 6 ký tự");
            etPassword.requestFocus();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Mật khẩu xác nhận không khớp");
            etConfirmPassword.requestFocus();
            return false;
        }

        return true;
    }
}
