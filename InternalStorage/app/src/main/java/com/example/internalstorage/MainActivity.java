package com.example.internalstorage;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private EditText editTextInput;
    private Button btnSave, btnRead, btnDelete;
    private TextView textViewOutput;
    private final String FILE_NAME = "meu_ficheiro.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Vincular os elementos do layout
        editTextInput = findViewById(R.id.editTextInput);
        btnSave = findViewById(R.id.btnSave);
        btnRead = findViewById(R.id.btnRead);
        btnDelete = findViewById(R.id.btnDelete);
        textViewOutput = findViewById(R.id.textViewOutput);

        // Salvar dados
        btnSave.setOnClickListener(view -> {
            String texto = editTextInput.getText().toString();
            if (!texto.isEmpty()) {
                salvarDados(texto);
            } else {
                Toast.makeText(this, "Por favor, insira o seu texto!", Toast.LENGTH_SHORT).show();
            }
        });

        // Ler dados
        btnRead.setOnClickListener(view -> lerDados());

        // Excluir arquivo
        btnDelete.setOnClickListener(view -> excluirArquivo());
    }

    private void salvarDados(String texto) {
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(texto.getBytes());
            fos.close();
            Toast.makeText(this, "Dados guardados com sucesso!", Toast.LENGTH_SHORT).show();
            editTextInput.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao guardar os dados!", Toast.LENGTH_SHORT).show();
        }
    }

    private void lerDados() {
        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            int c;
            StringBuilder conteudo = new StringBuilder();

            while ((c = fis.read()) != -1) {
                conteudo.append((char) c);
            }
            fis.close();
            textViewOutput.setText(conteudo.toString());
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao ler o ficheiro!", Toast.LENGTH_SHORT).show();
        }
    }

    private void excluirArquivo() {
        boolean deletado = deleteFile(FILE_NAME);
        if (deletado) {
            textViewOutput.setText("");
            Toast.makeText(this, "Ficheiro eliminado com sucesso!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Erro ao eliminar o ficheiro !", Toast.LENGTH_SHORT).show();
        }
    }
}
