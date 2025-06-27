package com.example.myapplication;

import android.content.Context;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CartAdapter extends ArrayAdapter<Product> {
    private Context context;
    private List<Product> cartItems;
    private OnItemDeleteListener deleteListener;

    public interface OnItemDeleteListener {
        void onDelete(int position);
    }

    public CartAdapter(@NonNull Context context, List<Product> list, OnItemDeleteListener listener) {
        super(context, 0, list);
        this.context = context;
        this.cartItems = list;
        this.deleteListener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Product product = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvPrice = convertView.findViewById(R.id.tvPrice);
        ImageView ivImage = convertView.findViewById(R.id.ivImage);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);

        tvName.setText(product.getName());
        tvPrice.setText(formatPrice(product.getPrice()));
        ivImage.setImageResource(product.getImageResId());

        // Set tag để btnDelete biết chính xác vị trí item
        btnDelete.setTag(position);
        btnDelete.setOnClickListener(v -> {
            int pos = (int) v.getTag();
            if (deleteListener != null) {
                deleteListener.onDelete(pos);
            }
        });

        return convertView;
    }

    private String formatPrice(double price) {
        int priceInt = (int) price;
        return String.format("%,d", priceInt).replace(',', '.') + " VND";
    }
}
