package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private GridView gridBooks;
    private ProductAdapter productAdapter;
    private final List<Product> productList = new ArrayList<>();

    private TextView btnAll, btnNovel, btnBusiness;
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        gridBooks = findViewById(R.id.gridBooks);
        btnAll = findViewById(R.id.btnAll);
        btnNovel = findViewById(R.id.btnNovel);
        btnBusiness = findViewById(R.id.btnBusiness);
        etSearch = findViewById(R.id.etSearch);

        productAdapter = new ProductAdapter(this, productList);
        gridBooks.setAdapter(productAdapter);

        gridBooks.setOnItemClickListener((parent, view, position, id) -> {
            Product selectedProduct = productList.get(position);
            Intent intent = new Intent(HomeActivity.this, BookDetailActivity.class);
            intent.putExtra("product", selectedProduct); // truyền nguyên Product
            Log.d("HomeActivity", "Selected product: " + selectedProduct.getName());
            startActivity(intent);
        });

        ImageButton btnCart = findViewById(R.id.btnCart);
        btnCart.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        });

        btnAll.setOnClickListener(v -> fetchBooksByCategory("all"));
        btnNovel.setOnClickListener(v -> fetchBooksByCategory("fiction"));
        btnBusiness.setOnClickListener(v -> {
            Toast.makeText(this, "Danh mục chưa có sách, thử Tiểu thuyết!", Toast.LENGTH_SHORT).show();
            btnNovel.performClick();
        });

        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                fetchBooks(etSearch.getText().toString());
                return true;
            }
            return false;
        });

        fetchBooksByCategory("all");
    }

    private void fetchBooksByCategory(String category) {
        String url = category.equals("all")
                ? "https://openlibrary.org/subjects/science.json?limit=20"
                : "https://openlibrary.org/subjects/" + category + ".json?limit=20";
        loadBooksFromAPI(url);
    }

    private void fetchBooks(String query) {
        String url = "https://openlibrary.org/search.json?q=" + query.replace(" ", "+");
        loadBooksFromAPI(url);
    }

    private void loadBooksFromAPI(String url) {
        productList.clear();
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray array = response.has("works")
                                ? response.getJSONArray("works")
                                : response.getJSONArray("docs");

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject bookObj = array.getJSONObject(i);
                            String title = bookObj.optString("title", "Không tên");
                            String author = "Không rõ";

                            if (bookObj.has("authors")) {
                                JSONArray authors = bookObj.getJSONArray("authors");
                                if (authors.length() > 0) {
                                    author = authors.getJSONObject(0).optString("name", "Không rõ");
                                }
                            } else if (bookObj.has("author_name")) {
                                author = bookObj.getJSONArray("author_name").optString(0, "Không rõ");
                            }

                            String coverUrl = "";
                            if (bookObj.has("cover_id")) {
                                int coverId = bookObj.getInt("cover_id");
                                coverUrl = "https://covers.openlibrary.org/b/id/" + coverId + "-M.jpg";
                            } else if (bookObj.has("cover_i")) {
                                String coverId = bookObj.optString("cover_i");
                                coverUrl = "https://covers.openlibrary.org/b/id/" + coverId + "-M.jpg";
                            }

                            productList.add(new Product(title, author, coverUrl, "Không có mô tả", R.drawable.ic_bookshelf, 0));
                        }

                        productAdapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        Toast.makeText(this, "Lỗi xử lý dữ liệu", Toast.LENGTH_SHORT).show();
                        Log.e("HomeActivity", "Error parsing JSON: " + e.getMessage());
                    }
                },
                error -> {
                    Toast.makeText(this, "Lỗi kết nối API", Toast.LENGTH_SHORT).show();
                    Log.e("HomeActivity", "Volley error: " + error.getMessage());
                }
        );

        queue.add(request);
    }
}
