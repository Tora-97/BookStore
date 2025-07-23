package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CartActivity extends AppCompatActivity {
    ListView lvCart;
    TextView tvTotal;
    CartAdapter adapter;

    Button btnBack, btnClearCart, btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        lvCart = findViewById(R.id.lvCart);
        tvTotal = findViewById(R.id.tvTotal);

        btnBack = findViewById(R.id.btnBack);
        btnClearCart = findViewById(R.id.btnClearCart);
        btnCheckout = findViewById(R.id.btnCheckout);

        adapter = new CartAdapter(this, Cart.cartItems, position -> {
            Cart.cartItems.remove(position);
            adapter.notifyDataSetChanged();
            updateTotal();
        });
        lvCart.setAdapter(adapter);

        updateTotal();

        btnBack.setOnClickListener(v -> {
            finish();
        });

        btnClearCart.setOnClickListener(v -> {
            Cart.cartItems.clear();
            adapter.notifyDataSetChanged();
            updateTotal();
            Toast.makeText(this, "Đã xoá toàn bộ giỏ hàng", Toast.LENGTH_SHORT).show();
        });

        btnCheckout.setOnClickListener(v -> {
            if (Cart.cartItems.isEmpty()) {
                Toast.makeText(this, "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, CheckoutActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateTotal() {
        int total = 0;
        for (Product p : Cart.cartItems) {
            total += p.getPrice(); // tính tổng tiền
        }
        tvTotal.setText("Tổng: " + total + " VND");
    }

}
