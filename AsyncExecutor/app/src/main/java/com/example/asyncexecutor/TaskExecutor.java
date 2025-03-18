package com.example.asyncexecutor;

import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

// Classe para gerir tarefas assíncronas
public class TaskExecutor {
    private final Executor executor = Executors.newSingleThreadExecutor(); // Executor com uma única thread
    private final Handler handler = new Handler(Looper.getMainLooper()); // Handler para atualizar a UI
    private final TextView statusTextView; // Referência ao TextView

    // Construtor que aceita o TextView como argumento
    public TaskExecutor(TextView statusTextView) {
        this.statusTextView = statusTextView;
    }

    // Método para executar a tarefa
    public void executeTask() {
        handler.post(() -> statusTextView.setText("Tarefa assíncrona iniciada...")); // Atualiza a UI no início

        executor.execute(() -> {
            try {
                Thread.sleep(10000); // Simula uma tarefa longa
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            handler.post(() -> statusTextView.setText("Tarefa assíncrona concluída!")); // Atualiza a UI ao terminar
        });
    }
}
