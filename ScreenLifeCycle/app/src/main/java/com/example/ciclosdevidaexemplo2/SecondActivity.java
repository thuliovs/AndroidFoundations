package com.example.ciclosdevidaexemplo2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Recuperar dados enviados pela MainActivity
        String message = getIntent().getStringExtra("EXTRA_MESSAGE");

        // Exibir a mensagem recebida ou uma mensagem padrão se for nulo
        TextView textView = findViewById(R.id.textView);
        if (message != null) {
            textView.setText(message);
        } else {
            textView.setText("Nenhuma mensagem recebida.");
        }
        Button backbutton = findViewById(R.id.back_button);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Criar Intent para navegar para SecondActivity
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}