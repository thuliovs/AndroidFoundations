package com.example.asyncexecutor;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

// Activity principal para demonstrar tarefas síncronas e assíncronas
public class MainActivity extends AppCompatActivity {
    private LinearLayout rootLayout; // Layout principal para alterar o fundo
    private ProgressBar progressBar; // Loader para as tarefas
    private TextView statusTextView; // Texto para status das tarefas
    private Button changeColorRed, changeColorBlue, startSyncTask, startAsyncTask; // Botões

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referências aos componentes
        rootLayout = findViewById(R.id.root_layout);
        progressBar = findViewById(R.id.progress_bar);
        statusTextView = findViewById(R.id.status_text);
        startSyncTask = findViewById(R.id.start_sync_task);
        startAsyncTask = findViewById(R.id.start_async_task);
        changeColorRed = findViewById(R.id.change_color_red);
        changeColorBlue = findViewById(R.id.change_color_blue);

        // Botões para iniciar tarefas
        startSyncTask.setOnClickListener(v -> executeSyncTask());
        startAsyncTask.setOnClickListener(v -> executeAsyncTask());

        // Botões para mudar cor
        changeColorRed.setOnClickListener(v -> rootLayout.setBackgroundColor(Color.RED));
        changeColorBlue.setOnClickListener(v -> rootLayout.setBackgroundColor(Color.BLUE));
    }

    // Executa uma tarefa síncrona
    private void executeSyncTask() {
        statusTextView.setText("Tarefa síncrona iniciada...");
        progressBar.setVisibility(View.VISIBLE);

        // Bloqueia os botões
        toggleButtons(false);

        // Timer para simular bloqueio
        new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
                statusTextView.setText("Tarefa síncrona: " + (millisUntilFinished / 1000) + " segundos restantes...");
            }

            public void onFinish() {
                statusTextView.setText("Tarefa síncrona concluída!");
                progressBar.setVisibility(View.GONE);
                toggleButtons(true); // Reativa os botões
            }
        }.start();
    }

    // Executa uma tarefa assíncrona
    private void executeAsyncTask() {
        statusTextView.setText("Tarefa assíncrona iniciada...");
        progressBar.setVisibility(View.VISIBLE);

        // Apenas permite mudar a cor
        toggleInteractionButtons(false);

        // Timer para simular execução
        new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
                statusTextView.setText("Tarefa assíncrona: " + (millisUntilFinished / 1000) + " segundos restantes...");
            }

            public void onFinish() {
                statusTextView.setText("Tarefa assíncrona concluída!");
                progressBar.setVisibility(View.GONE);
                toggleInteractionButtons(true);
            }
        }.start();
    }

    // Ativa/desativa todos os botões
    private void toggleButtons(boolean enable) {
        startSyncTask.setEnabled(enable);
        startAsyncTask.setEnabled(enable);
        changeColorRed.setEnabled(enable);
        changeColorBlue.setEnabled(enable);
    }

    // Ativa/desativa botões de interação
    private void toggleInteractionButtons(boolean enable) {
        startSyncTask.setEnabled(enable);
        startAsyncTask.setEnabled(enable);
        changeColorRed.setEnabled(!enable);
        changeColorBlue.setEnabled(!enable);
    }
}
