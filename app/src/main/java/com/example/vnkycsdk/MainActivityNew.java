package com.example.vnkycsdk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.vnkycsdk.databinding.ActivityMainNewBinding;


public class MainActivityNew extends AppCompatActivity {

    private Context context;

    ActivityMainNewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        binding = ActivityMainNewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}