package com.example.myapplication;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    public static List<Product> cartItems = new ArrayList<>();

    public static void addToCart(Product product) {
        cartItems.add(product);
    }

    public static void clearCart() {
        cartItems.clear();
    }
}
