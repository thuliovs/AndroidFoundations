package com.example.backgroundservices; // Pacote principal da aplicação

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

// Classe que define o serviço em segundo plano
public class MyService extends Service {
    private Handler handler = new Handler(); // Handler para executar tarefas repetitivas
    private Runnable runnable;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyService", "Serviço Criado"); // Log para indicar que o serviço foi criado

        // Definir a tarefa repetitiva
        runnable = new Runnable() {
            @Override
            public void run() {
                Log.d("MyService", "Serviço em execução..."); // Mensagem de log
                handler.postDelayed(this, 5000); // Reexecuta a cada 5 segundos
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.post(runnable); // Inicia a execução da tarefa
        // Retorna START_STICKY para indicar que o serviço será recriado se for finalizado
        return START_STICKY; // Indica que o serviço deve continuar até ser manualmente parado
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable); // Remove tarefas pendentes do handler
        Log.d("MyService", "Serviço Terminado"); // Log para indicar o término do serviço
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null; // Serviço não está ligado a nenhuma Activity
    }
}

