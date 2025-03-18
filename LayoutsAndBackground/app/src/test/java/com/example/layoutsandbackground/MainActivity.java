package com.example.layoutsandbackground;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Botão para abrir LinearLayoutActivity
        Button linearButton = findViewById(R.id.linear_button);
        linearButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LinearLayoutActivity.class);
            startActivity(intent);
        });

        // Botão para abrir RelativeLayoutActivity
        Button relativeButton = findViewById(R.id.relative_button);
        relativeButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RelativeLayoutActivity.class);
            startActivity(intent);
        });

        // Botão para abrir ConstraintLayoutActivity
        Button constraintButton = findViewById(R.id.constraint_button);
        constraintButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ConstraintLayoutActivity.class);
            startActivity(intent);
        });

        // Botão para abrir FrameLayoutActivity
        Button frameButton = findViewById(R.id.frame_button);
        frameButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FrameLayoutActivity.class);
            startActivity(intent);
        });
    }
}