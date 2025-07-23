package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
    private Context context;

    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Book book = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        }

        ImageView ivCover = convertView.findViewById(R.id.ivCover);
        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        TextView tvAuthor = convertView.findViewById(R.id.tvAuthor);

        if (book != null) {
            tvTitle.setText(book.getTitle());
            tvAuthor.setText(book.getAuthor());

            Glide.with(context)
                    .load(book.getCoverUrl())
                    .placeholder(R.drawable.ic_book_placeholder)
                    .into(ivCover);

            convertView.setOnClickListener(v -> {
                Intent intent = new Intent(context, BookDetailActivity.class);
                intent.putExtra("book", book);  // <-- Truyền object Book
                context.startActivity(intent);
            });
        }

        return convertView;
    }
}
