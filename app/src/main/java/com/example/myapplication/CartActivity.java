package com.example.myapplication;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CartActivity extends AppCompatActivity {
    ListView lvCart;
    TextView tvTotal;
    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        lvCart = findViewById(R.id.lvCart);
        tvTotal = findViewById(R.id.tvTotal);

        adapter = new CartAdapter(this, Cart.cartItems, position -> {
            Cart.cartItems.remove(position);
            adapter.notifyDataSetChanged();
            updateTotal();
        });
        lvCart.setAdapter(adapter);

        updateTotal();
    }

    private void updateTotal() {
        int total = Cart.cartItems.size(); // Tạm tính theo số lượng
        tvTotal.setText("Số sách trong giỏ: " + total);
    }
}
