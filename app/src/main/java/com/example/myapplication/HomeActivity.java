package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    ListView lvProducts;
    List<Product> productList;
    ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        lvProducts = findViewById(R.id.lvProducts);
        EditText etSearch = findViewById(R.id.etSearch);
        Button btnCart = findViewById(R.id.btnCart);
        etSearch.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        productList = new ArrayList<>();
        productList.add(new Product("Java Cơ bản", "Lập trình Java cho người mới bắt đầu", 95000, R.drawable.java_book));
        productList.add(new Product("Android Studio", "Xây dựng ứng dụng Android hiện đại", 120000, R.drawable.android_book));
        productList.add(new Product("Đắc Nhân Tâm", "Kỹ năng sống kinh điển", 87000, R.drawable.dacnhantam));
        productList.add(new Product("Harry Potter", "Hòn đá phù thuỷ", 180000, R.drawable.harrypotter));
        productList.add(new Product("Lập trình Python", "Từ cơ bản đến nâng cao", 115000, R.drawable.python_book));
        productList.add(new Product("Tuổi trẻ đáng giá bao nhiêu", "Truyền cảm hứng sống tích cực", 89000, R.drawable.tuoitre));

        adapter = new ProductAdapter(this, productList);
        lvProducts.setAdapter(adapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().toLowerCase().trim();
                List<Product> filtered = new ArrayList<>();
                if (query.isEmpty()) {
                    filtered.addAll(productList);
                } else {
                    for (Product p : productList) {
                        if (p.getName().toLowerCase().contains(query)) {
                            filtered.add(p);
                        }
                    }
                }
                adapter.filterList(filtered);
            }
        });

        btnCart.setOnClickListener(v -> {
            startActivity(new Intent(this, CartActivity.class));
        });

        lvProducts.setOnItemClickListener((parent, view, position, id) -> {
            Intent i = new Intent(this, ProductDetailActivity.class);
            i.putExtra("product", adapter.getItem(position));
            startActivity(i);
        });
    }
}
