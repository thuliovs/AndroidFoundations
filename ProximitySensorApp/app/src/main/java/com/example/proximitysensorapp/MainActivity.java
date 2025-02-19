package com.example.proximitysensorapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor sensorProximidade;
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

        // Inicializar o SensorManager e o Sensor de Proximidade
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorProximidade = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if (sensorProximidade == null) {
            txtEstado.setText("Sensor de Proximidade não disponível");
            txtValor.setText("Distância: Indisponível");
            return;
        }

        // Configurar o Listener para o Sensor de Proximidade
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float distancia = event.values[0];

                // Atualizar os valores na interface
                txtValor.setText(String.format("Distância: %.2f cm", distancia));

                // Determinar o estado com base na distância
                if (distancia < sensorProximidade.getMaximumRange()) {
                    txtEstado.setText("Objeto próximo");
                } else {
                    txtEstado.setText("Sem um objeto próximo");
                    txtValor.setText(String.format(""));
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // Não utilizado neste exemplo
            }
        };

        // Verificar o valor inicial do sensor
        verificarEstadoInicial();
    }

    /**
     * Método para verificar e exibir o estado inicial do sensor.
     */
    private void verificarEstadoInicial() {
        float distanciaInicial = sensorProximidade.getMaximumRange();

        if (distanciaInicial < sensorProximidade.getMaximumRange()) {
            txtEstado.setText("Objeto próximo");
            txtValor.setText(String.format("Distância: %.2f cm", distanciaInicial));
        } else {
            txtEstado.setText("Sem objeto próximo");
            txtValor.setText(String.format("Distância: %.2f cm", distanciaInicial));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Registrar o Listener do Sensor de Proximidade
        sensorManager.registerListener(sensorEventListener, sensorProximidade, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Remover o Listener ao pausar
        sensorManager.unregisterListener(sensorEventListener);
    }
}