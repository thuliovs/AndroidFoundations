package com.example.sharedpreferences;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etNome;
    private TextView tvResultado;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referenciar os elementos do layout
        etNome = findViewById(R.id.etNome);
        tvResultado = findViewById(R.id.tvResultado);
        Button btnSalvar = findViewById(R.id.btnSalvar);
        Button btnMostrar = findViewById(R.id.btnMostrar);
        Button btnExcluir = findViewById(R.id.btnExcluir);

        // Inicializar o SharedPreferences
        sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Configurar os botões
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarNome();
            }
        });

        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarNome();
            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirNome();
            }
        });
    }

    // Método para salvar o nome no SharedPreferences
    private void salvarNome() {
        String nome = etNome.getText().toString();
        if (!nome.isEmpty()) {
            editor.putString("nome_utilizador", nome);
            editor.apply();
            tvResultado.setText("Nome guardado com sucesso em SharedPreferences!");
        } else {
            tvResultado.setText("Por favor, insira um nome.");
        }
    }

    // Método para recuperar o nome do SharedPreferences
    private void mostrarNome() {
        String nome = sharedPreferences.getString("nome_utilizador", "Nenhum nome encontrado.");
        tvResultado.setText("Nome guardado: " + nome);
    }

    // Método para excluir o nome do SharedPreferences
    private void excluirNome() {
        if (sharedPreferences.contains("nome_utilizador")) {
            editor.remove("nome_utilizador");
            editor.apply();
            tvResultado.setText("Nome eliminado com sucesso!");
        } else {
            tvResultado.setText("Nenhum nome para eliminar.");
        }
    }
}
