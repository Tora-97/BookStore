package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnClearCart).setOnClickListener(v -> {
            Cart.cartItems.clear();
            adapter.notifyDataSetChanged();
            updateTotal();
        });
    }

    private void updateTotal() {
        double total = 0;
        for (Product p : Cart.cartItems) {
            total += p.getPrice();
        }
        tvTotal.setText("Tổng: " + formatPrice(total));
    }

    private String formatPrice(double price) {
        int priceInt = (int) price;
        return String.format("%,d", priceInt).replace(',', '.') + " VND";
    }
}
