package com.example.gyroscopeapp;

import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor giroscopio;
    private SensorEventListener sensorEventListener;

    private TextView txtVelocidade, txtX, txtY, txtZ, txtDirecao, txtExplicacao;

    private float rotacaoAcumulada = 0.0f; // Rotação acumulada no eixo Z

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referências para os TextViews
        txtVelocidade = findViewById(R.id.txtVelocidade);
        txtX = findViewById(R.id.txtX);
        txtY = findViewById(R.id.txtY);
        txtZ = findViewById(R.id.txtZ);
        txtDirecao = findViewById(R.id.txtDirecao);
        txtExplicacao = findViewById(R.id.txtExplicacao);

        // Inicializar o SensorManager e o Giroscópio
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        giroscopio = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if (giroscopio == null) {
            txtVelocidade.setText("Giroscópio não disponível");
            return;
        }

        // Configurar o Listener para o Giroscópio
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                    float rotacaoZ = event.values[2]; // Rotação em torno do eixo Z
                    float deltaTempo = 0.02f; // Aproximadamente 50 Hz de leitura (1/50)

                    // Acumular rotação no eixo Z
                    rotacaoAcumulada += rotacaoZ * deltaTempo;

                    // Normalizar o ângulo para ficar entre 0 e 360 graus
                    rotacaoAcumulada = (rotacaoAcumulada + 360) % 360;

                    // Determinar a direção
                    String direcao = calcularDirecao(rotacaoAcumulada);

                    // Atualizar os valores na interface
                    txtVelocidade.setText("Sensor do Giroscópio");
                    txtX.setText(String.format("Rotação X: %.2f rad/s", event.values[0]));
                    txtY.setText(String.format("Rotação Y: %.2f rad/s", event.values[1]));
                    txtZ.setText(String.format("Rotação Z: %.2f rad/s", rotacaoZ));
                    txtDirecao.setText("Direção: " + direcao);
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

        // Verificar se a permissão HIGH_SAMPLING_RATE_SENSORS é necessária
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.HIGH_SAMPLING_RATE_SENSORS)
                    != PackageManager.PERMISSION_GRANTED) {
                // Solicitar a permissão ao utilizador
                requestPermissions(new String[]{android.Manifest.permission.HIGH_SAMPLING_RATE_SENSORS}, 1);
                return;
            }
        }

        // Registrar o Listener do Giroscópio
        sensorManager.registerListener(sensorEventListener, giroscopio, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Remover o Listener ao pausar
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissão concedida, registrar o Listener do Giroscópio
                sensorManager.registerListener(sensorEventListener, giroscopio, SensorManager.SENSOR_DELAY_FASTEST);
            } else {
                txtVelocidade.setText("Permissão de alta taxa de amostragem negada.");
            }
        }
    }

    /**
     * Método para calcular a direção com base no ângulo acumulado.
     */
    private String calcularDirecao(float angulo) {
        if (angulo >= 315 || angulo < 45) {
            return "Norte";
        } else if (angulo >= 45 && angulo < 135) {
            return "Este";
        } else if (angulo >= 135 && angulo < 225) {
            return "Sul";
        } else if (angulo >= 225 && angulo < 315) {
            return "Oeste";
        }
        return "Desconhecido";
    }
}
