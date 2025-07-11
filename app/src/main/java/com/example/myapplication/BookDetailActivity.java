package com.example.myapplication;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class BookDetailActivity extends AppCompatActivity {
    ImageView ivCover;
    TextView tvTitle, tvAuthor, tvDescription;
    Button btnAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        ivCover = findViewById(R.id.ivCover);
        tvTitle = findViewById(R.id.tvTitle);
        tvAuthor = findViewById(R.id.tvAuthor);
        tvDescription = findViewById(R.id.tvDescription);
        btnAddToCart = findViewById(R.id.btnAddToCart);

        Product product = (Product) getIntent().getSerializableExtra("product");

        tvTitle.setText(product.getName());
        tvAuthor.setText(product.getAuthor());
        tvDescription.setText(product.getDescription());

        if (!product.getImageUrl().isEmpty()) {
            Picasso.get().load(product.getImageUrl()).into(ivCover);
        } else {
            ivCover.setImageResource(R.drawable.ic_bookshelf);
        }

        btnAddToCart.setOnClickListener(v -> {
            Cart.addToCart(product);
            Toast.makeText(this, "Đã thêm vào giỏ", Toast.LENGTH_SHORT).show();
        });
    }
}
