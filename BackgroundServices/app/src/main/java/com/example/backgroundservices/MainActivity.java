package com.example.backgroundservices; // Pacote principal da aplicação

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

// Activity principal para iniciar e parar o serviço
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Define o layout principal

        // Botões para controlar o serviço
        Button startService = findViewById(R.id.start_service);
        Button stopService = findViewById(R.id.stop_service);

        // Clique para iniciar o serviço
        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyService.class);
                startService(intent); // Inicia o serviço
            }
        });

        // Clique para parar o serviço
        stopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyService.class);
                stopService(intent); // Termina o serviço
            }
        });
    }
}
