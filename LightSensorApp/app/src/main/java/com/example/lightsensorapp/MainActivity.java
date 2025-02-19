package com.example.lightsensorapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor sensorLuz;
    private SensorEventListener sensorEventListener;

    private TextView txtEstado, txtValor, txtExplicacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referências para os TextViews
        txtEstado = findViewById(R.id.txtEstado);
        txtValor = findViewById(R.id.txtValor);
        txtExplicacao = findViewById(R.id.txtExplicacao);

        // Inicializar o SensorManager e o Sensor de Luz
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorLuz = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (sensorLuz == null) {
            txtEstado.setText("Sensor de Luz não disponível");
            txtValor.setText("Intensidade: Indisponível");
            return;
        }

        // Configurar o Listener para o Sensor de Luz
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float intensidade = event.values[0];

                // Atualizar os valores na interface
                txtValor.setText(String.format("Intensidade: %.2f lux", intensidade));

                // Determinar o estado com base na intensidade
                if (intensidade < 10) {
                    txtEstado.setText("Ambiente Escuro");
                } else if (intensidade < 100) {
                    txtEstado.setText("Ambiente Moderado");
                } else {
                    txtEstado.setText("Ambiente Claro");
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // Não utilizado neste exemplo
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Registrar o Listener do Sensor de Luz
        sensorManager.registerListener(sensorEventListener, sensorLuz, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Remover o Listener ao pausar
        sensorManager.unregisterListener(sensorEventListener);
    }
}
