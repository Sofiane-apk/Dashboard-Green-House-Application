package com.example.smart_green_house;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class splashscreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        startActivity(new Intent( this, startpage.class));
    }


}
