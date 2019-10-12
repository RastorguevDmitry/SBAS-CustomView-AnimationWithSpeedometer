package com.rdi.spidometr;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SpeedometerView speedometerView = findViewById(R.id.speedometerView);
        speedometerView.setCurentVeloсity(120);
        speedometerView.setColorArrow(Color.GREEN);
        speedometerView.setMaxVeloсity(500);
    }
}
