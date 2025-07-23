package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProductAdapter extends BaseAdapter {

    private final Context context;
    private final List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        }

        ImageView ivImage = convertView.findViewById(R.id.ivImage);
        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvAuthor = convertView.findViewById(R.id.tvAuthor);
        ImageView ivAddToCart = convertView.findViewById(R.id.ivAddToCart);

        Product product = productList.get(position);

        tvName.setText(product.getName());
        tvAuthor.setText("Tác giả: " + product.getAuthor());

        Glide.with(context)
                .load(product.getImageUrl())
                .placeholder(R.drawable.ic_bookshelf)
                .into(ivImage);

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BookDetailActivity.class);
            intent.putExtra("name", product.getName());
            intent.putExtra("author", product.getAuthor());
            intent.putExtra("cover", product.getImageUrl());
            intent.putExtra("description", product.getDescription());
            intent.putExtra("price", product.getPrice());
            context.startActivity(intent);
        });

        ivAddToCart.setOnClickListener(v -> {
            Cart.addToCart(product);
            Toast.makeText(context, "Đã thêm \"" + product.getName() + "\" vào giỏ", Toast.LENGTH_SHORT).show();
        });

        return convertView;
    }
}
