package com.example.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CheckoutActivity extends AppCompatActivity {

    EditText edtName, edtAddress, edtPhone, edtEmail;
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        edtName = findViewById(R.id.edtName);
        edtAddress = findViewById(R.id.edtAddress);
        edtPhone = findViewById(R.id.edtPhone);
        edtEmail = findViewById(R.id.edtEmail);
        btnConfirm = findViewById(R.id.btnConfirm);

        btnConfirm.setOnClickListener(v -> {
            if (validateInput()) {
                Toast.makeText(this, "Thanh toán thành công!", Toast.LENGTH_LONG).show();
                Cart.cartItems.clear();
                finish();
            }
        });
    }

    private boolean validateInput() {
        String name = edtName.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();

        if (name.isEmpty()) {
            edtName.setError("Vui lòng nhập họ tên");
            edtName.requestFocus();
            return false;
        }

        if (address.isEmpty()) {
            edtAddress.setError("Vui lòng nhập địa chỉ");
            edtAddress.requestFocus();
            return false;
        }

        if (phone.isEmpty()) {
            edtPhone.setError("Vui lòng nhập số điện thoại");
            edtPhone.requestFocus();
            return false;
        }

        if (phone.length() != 10 || !phone.matches("\\d{10}")) {
            edtPhone.setError("Số điện thoại phải có 10 chữ số");
            edtPhone.requestFocus();
            return false;
        }

        if (!email.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Email không hợp lệ");
            edtEmail.requestFocus();
            return false;
        }

        return true;
    }
}
