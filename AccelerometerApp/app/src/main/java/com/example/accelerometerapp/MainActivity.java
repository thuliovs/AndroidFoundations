package com.example.accelerometerapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor linearAcelerometro;
    private Sensor acelerometro;
    private SensorEventListener sensorEventListener;

    private TextView txtVelocidade, txtX, txtY, txtZ, txtGravidadeX, txtGravidadeY, txtGravidadeZ;

    private float velocidade = 0.0f; // Velocidade em m/s
    private long ultimoTempo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referências para os TextViews
        txtVelocidade = findViewById(R.id.txtVelocidade);
        txtX = findViewById(R.id.txtX);
        txtY = findViewById(R.id.txtY);
        txtZ = findViewById(R.id.txtZ);
        txtGravidadeX = findViewById(R.id.txtGravidadeX);
        txtGravidadeY = findViewById(R.id.txtGravidadeY);
        txtGravidadeZ = findViewById(R.id.txtGravidadeZ);

        // Inicializar o SensorManager e os Sensores
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        linearAcelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (linearAcelerometro == null || acelerometro == null) {
            txtVelocidade.setText("Sensores não disponíveis");
            return;
        }

        // Configurar o Listener para ambos os sensores
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                long tempoAtual = System.currentTimeMillis();
                if (ultimoTempo == 0) {
                    ultimoTempo = tempoAtual;
                    return;
                }

                float deltaTempo = (tempoAtual - ultimoTempo) / 1000.0f; // Converter para segundos

                if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
                    // Aceleração sem gravidade
                    float aceleracaoX = event.values[0];
                    float aceleracaoY = event.values[1];
                    float aceleracaoZ = event.values[2];

                    // Ignorar pequenas variações (ruído)
                    float limiar = 0.1f; // m/s²
                    if (Math.abs(aceleracaoX) < limiar) aceleracaoX = 0;
                    if (Math.abs(aceleracaoY) < limiar) aceleracaoY = 0;
                    if (Math.abs(aceleracaoZ) < limiar) aceleracaoZ = 0;

                    // Calcular o módulo da aceleração
                    float aceleracaoTotal = (float) Math.sqrt(
                            aceleracaoX * aceleracaoX +
                                    aceleracaoY * aceleracaoY +
                                    aceleracaoZ * aceleracaoZ
                    );

                    // Calcular velocidade: v = u + at
                    velocidade = aceleracaoTotal * deltaTempo;

                    // Converter velocidade para km/h
                    float velocidadeKmH = velocidade * 3.6f;

                    // Atualizar valores na interface
                    txtVelocidade.setText(String.format("Velocidade: %.2f km/h", velocidadeKmH));
                    txtX.setText(String.format("Aceleração X: %.2f m/s²", aceleracaoX));
                    txtY.setText(String.format("Aceleração Y: %.2f m/s²", aceleracaoY));
                    txtZ.setText(String.format("Aceleração Z: %.2f m/s²", aceleracaoZ));
                } else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    // Aceleração com gravidade
                    float gravidadeX = event.values[0];
                    float gravidadeY = event.values[1];
                    float gravidadeZ = event.values[2];

                    // Atualizar valores na interface
                    txtGravidadeX.setText(String.format("Gravidade X: %.2f m/s²", gravidadeX));
                    txtGravidadeY.setText(String.format("Gravidade Y: %.2f m/s²", gravidadeY));
                    txtGravidadeZ.setText(String.format("Gravidade Z: %.2f m/s²", gravidadeZ));
                }

                ultimoTempo = tempoAtual;
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
        // Registar os Listeners dos Sensores
        sensorManager.registerListener(sensorEventListener, linearAcelerometro, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListener, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Remover os Listeners dos Sensores
        sensorManager.unregisterListener(sensorEventListener);
    }
}