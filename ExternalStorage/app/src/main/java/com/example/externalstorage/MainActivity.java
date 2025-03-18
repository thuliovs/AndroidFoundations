package com.example.externalstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final String FILE_NAME = "meu_ficheiro.txt";
    private EditText editTextInput;
    private TextView textViewOutput;
    private TextView diskStatusText, sdCardStatusText;
    private ImageView diskStatusIcon, sdCardStatusIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar componentes
        editTextInput = findViewById(R.id.editTextInput);
        textViewOutput = findViewById(R.id.textViewOutput);

        // Botões e ações
        findViewById(R.id.btnSaveExternalPrivate).setOnClickListener(view -> {
            String texto = obterTextoInput();
            if (texto == null) {
                mostrarMensagem("Insira um texto para guardar no Disco Externo Privado", false);
                return;
            }
            boolean success = StorageUtils.salvarDiscoExternoPrivado(this, FILE_NAME, texto);
            mostrarMensagem("Guardar Privado no Disco Externo", success);
        });

        findViewById(R.id.btnSaveExternalPublic).setOnClickListener(view -> {
            String texto = obterTextoInput();
            if (texto == null) {
                mostrarMensagem("Insira um texto para guardar no Disco Externo Público", false);
                return;
            }
            boolean success = StorageUtils.salvarDiscoExternoPublico(this, FILE_NAME, texto);
            mostrarMensagem("Guardar Público no Disco Externo", success);
        });

        findViewById(R.id.btnReadExternalPrivate).setOnClickListener(view -> {
            boolean success = StorageUtils.lerDiscoExternoPrivado(this, FILE_NAME, textViewOutput);
            mostrarMensagem("Ler do Disco Externo Privado", success);
        });

        findViewById(R.id.btnDeleteExternalPrivate).setOnClickListener(view -> {
            boolean success = StorageUtils.removerDiscoExternoPrivado(this, FILE_NAME);
            mostrarMensagem("Remover do Disco Externo Privado", success);
        });

        findViewById(R.id.btnDeleteExternalPublic).setOnClickListener(view -> {
            boolean success = StorageUtils.removerDiscoExternoPublico(this, FILE_NAME);
            mostrarMensagem("Remover do Disco Externo Público", success);
        });


        findViewById(R.id.btnSaveSDPublic).setOnClickListener(view -> {
            String texto = obterTextoInput();
            if (texto == null) {
                mostrarMensagem("Insira um texto para guardar no Cartão SD Público", false);
                return;
            }
            boolean success = SDCardUtils.salvarSDPublico(this, FILE_NAME, texto);
            mostrarMensagem("Guardar Público no Cartão SD", success);
        });

        findViewById(R.id.btnDeleteSDPublic).setOnClickListener(view -> {
            boolean success = SDCardUtils.removerSDPublico(this, FILE_NAME);
            mostrarMensagem("Remover do Cartão SD Público", success);
        });

        findViewById(R.id.btnReadExternalPublic).setOnClickListener(view -> {
            boolean success = StorageUtils.lerDiscoExternoPublico(this, FILE_NAME, textViewOutput);
            mostrarMensagem("Ler do Disco Externo Público", success);
        });

        findViewById(R.id.btnReadSDPrivate).setOnClickListener(view -> {
            boolean success = SDCardUtils.lerSDPrivado(this, FILE_NAME, textViewOutput);
            mostrarMensagem("Ler do Cartão SD Privado", success);
        });

        findViewById(R.id.btnDeleteSDPrivate).setOnClickListener(view -> {
            boolean success = SDCardUtils.removerSDPrivado(this, FILE_NAME);
            mostrarMensagem("Remover do Cartão SD Privado", success);
        });

        findViewById(R.id.btnDeleteSDPublic).setOnClickListener(view -> {
            boolean success = SDCardUtils.removerSDPublico(this, "meu_ficheiro.txt");
            if (success) {
                Toast.makeText(this, "Ficheiro removido com sucesso do Cartão SD Público", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Erro ao remover o ficheiro do Cartão SD Público", Toast.LENGTH_SHORT).show();
            }
        });



        findViewById(R.id.btnSaveSDPrivate).setOnClickListener(view -> {
            String texto = obterTextoInput();
            if (texto == null) {
                mostrarMensagem("Insira um texto para guardar no Cartão SD Privado", false);
                return;
            }
            boolean success = SDCardUtils.salvarSDPrivado(this, FILE_NAME, texto);
            mostrarMensagem("Guardar Privado no Cartão SD", success);
        });

        findViewById(R.id.btnReadSDPublic).setOnClickListener(view -> {
            boolean success = SDCardUtils.lerSDPublico(this, "meu_ficheiro.txt", textViewOutput);
            if (!success) {
                Toast.makeText(this, "Erro ao ler do Cartão SD Público", Toast.LENGTH_SHORT).show();
            }
        });


    }

    // Método para exibir mensagens de sucesso ou erro
    private void mostrarMensagem(String acao, boolean sucesso) {
        if (sucesso) {
            Toast.makeText(this, acao + " realizado com sucesso!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Erro ao realizar: " + acao, Toast.LENGTH_SHORT).show();
        }
    }

    // Método para obter texto do EditTextInput
    private String obterTextoInput() {
        String texto = editTextInput.getText().toString().trim();
        return texto.isEmpty() ? null : texto;
    }
}




