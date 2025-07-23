package com.example.myapplication;

import android.os.Bundle;
import android.widget.*;
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

        String title = getIntent().getStringExtra("name");
        String author = getIntent().getStringExtra("author");
        String imageUrl = getIntent().getStringExtra("cover");
        String description = getIntent().getStringExtra("description");
        double price = getIntent().getDoubleExtra("price", 0);

        if (title == null || author == null || description == null) {
            Toast.makeText(this, "Không có dữ liệu sách", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tvTitle.setText(title);
        tvAuthor.setText(author);
        tvDescription.setText(description);

        if (imageUrl == null || imageUrl.isEmpty()) {
            ivCover.setImageResource(R.drawable.ic_bookshelf);
        } else {
            Picasso.get().load(imageUrl).placeholder(R.drawable.ic_bookshelf).into(ivCover);
        }

        btnAddToCart.setOnClickListener(v -> {
            Product product = new Product(title, author, imageUrl, description, R.drawable.ic_bookshelf, price);
            Cart.addToCart(product);
            Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
        });

        TextView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }
}
