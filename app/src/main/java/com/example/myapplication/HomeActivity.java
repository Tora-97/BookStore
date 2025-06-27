package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.text.Normalizer;
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

        adapter = new ProductAdapter(this, new ArrayList<>(productList));
        lvProducts.setAdapter(adapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String rawQuery = s.toString().toLowerCase();
                String query = removeAccent(rawQuery);

                List<Product> filtered = new ArrayList<>();
                for (Product p : productList) {
                    String name = removeAccent(p.getName().toLowerCase());
                    if (name.contains(query)) {
                        filtered.add(p);
                    }
                }

                if (query.isEmpty()) {
                    adapter.updateList(productList);
                } else {
                    adapter.updateList(filtered);
                }
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

    private String removeAccent(String text) {
        if (text == null) return "";
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}
