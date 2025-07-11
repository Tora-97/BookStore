package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    List<Product> productList = new ArrayList<>();
    String API_URL = "https://openlibrary.org/subjects/fiction.json?limit=20";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productAdapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(productAdapter);

        fetchBooksFromAPI();
    }

    private void fetchBooksFromAPI() {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, API_URL, null,
                response -> {
                    try {
                        JSONArray worksArray = response.getJSONArray("works");

                        for (int i = 0; i < worksArray.length(); i++) {
                            JSONObject bookObj = worksArray.getJSONObject(i);
                            String title = bookObj.getString("title");
                            String author = "Không rõ";

                            if (bookObj.has("authors")) {
                                JSONArray authors = bookObj.getJSONArray("authors");
                                if (authors.length() > 0) {
                                    author = authors.getJSONObject(0).getString("name");
                                }
                            }

                            String coverUrl = "";
                            if (bookObj.has("cover_id")) {
                                int coverId = bookObj.getInt("cover_id");
                                coverUrl = "https://covers.openlibrary.org/b/id/" + coverId + "-L.jpg";
                            }

                            Product product = new Product(title, author, coverUrl, "Không có mô tả", 0);
                            productList.add(product);
                        }

                        productAdapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        Toast.makeText(this, "Lỗi xử lý dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Lỗi kết nối API", Toast.LENGTH_SHORT).show());

        queue.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setQueryHint("Tìm sách...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchBooks(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_cart) {
            startActivity(new Intent(this, CartActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchBooks(String query) {
        String url = "https://openlibrary.org/search.json?q=" + query.replace(" ", "+");

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    List<Product> results = new ArrayList<>();

                    try {
                        JSONArray docs = response.getJSONArray("docs");
                        for (int i = 0; i < docs.length(); i++) {
                            JSONObject book = docs.getJSONObject(i);
                            String title = book.optString("title");
                            String author = book.optJSONArray("author_name") != null ?
                                    book.getJSONArray("author_name").optString(0) : "Không rõ";
                            String coverId = book.optString("cover_i");
                            String coverUrl = coverId.isEmpty() ?
                                    "" : "https://covers.openlibrary.org/b/id/" + coverId + "-M.jpg";

                            results.add(new Product(title, author, coverUrl, "Không có mô tả", 0));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    productList.clear();
                    productList.addAll(results);
                    productAdapter.notifyDataSetChanged();
                },
                error -> Toast.makeText(this, "Lỗi tải dữ liệu", Toast.LENGTH_SHORT).show()
        );
        queue.add(request);
    }
}
