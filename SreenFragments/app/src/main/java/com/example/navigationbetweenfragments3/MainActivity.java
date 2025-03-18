package com.example.navigationbetweenfragments3;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private WaveFragment[] fragments = new WaveFragment[6];
    private final Handler handler = new Handler();
    private final int[] colors = generateColorGradient(Color.parseColor("#ADD8E6"), Color.parseColor("#00008B"), 12); // Gradiente suave
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa os fragmentos
        fragments[0] = new WaveFragment();
        fragments[1] = new WaveFragment();
        fragments[2] = new WaveFragment();
        fragments[3] = new WaveFragment();
        fragments[4] = new WaveFragment();
        fragments[5] = new WaveFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment1, fragments[0]);
        transaction.replace(R.id.fragment2, fragments[1]);
        transaction.replace(R.id.fragment3, fragments[2]);
        transaction.replace(R.id.fragment4, fragments[3]);
        transaction.replace(R.id.fragment5, fragments[4]);
        transaction.replace(R.id.fragment6, fragments[5]);
        transaction.commit();

        // Inicia o efeito de onda
        startWaveEffect();
    }

    private void startWaveEffect() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Atualiza as cores dos fragmentos em sequência
                for (int i = 0; i < fragments.length; i++) {
                    int colorIndex = (currentIndex + i) % colors.length;
                    fragments[i].setBackgroundColor(colors[colorIndex]);
                }

                // Incrementa o índice
                currentIndex++;

                // Repete o efeito
                handler.postDelayed(this, 500); // Atualiza a cada 500ms
            }
        }, 500);
    }

    private int[] generateColorGradient(int startColor, int endColor, int steps) {
        int[] gradient = new int[steps];
        for (int i = 0; i < steps; i++) {
            float ratio = (float) i / (steps - 1);
            int red = (int) (Color.red(startColor) * (1 - ratio) + Color.red(endColor) * ratio);
            int green = (int) (Color.green(startColor) * (1 - ratio) + Color.green(endColor) * ratio);
            int blue = (int) (Color.blue(startColor) * (1 - ratio) + Color.blue(endColor) * ratio);
            gradient[i] = Color.rgb(red, green, blue);
        }
        return gradient;
    }
}