package com.example.crud;

import android.app.AlertDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Atividade principal que gere a interface com o utilizador.
 */
public class MainActivity extends AppCompatActivity {

    private TarefaRepository tarefaRepository;
    private ListView listViewTarefas;
    private Button btnAdicionarTarefa, btnDelete, btnUpdate;
    private EditText editTitulo, editDescricao;
    private ArrayAdapter<String> adapter;
    private ArrayList<Integer> ids; // IDs de todas as tarefas
    private ArrayList<Integer> selectedIds; // IDs das tarefas selecionadas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar os componentes
        tarefaRepository = new TarefaRepository(this);
        listViewTarefas = findViewById(R.id.listViewTarefas);
        btnAdicionarTarefa = findViewById(R.id.btnAdicionarTarefa);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
        editTitulo = findViewById(R.id.editTitulo);
        editDescricao = findViewById(R.id.editDescricao);
        ids = new ArrayList<>();
        selectedIds = new ArrayList<>();

        // Configuração do ListView para multiseleção
        listViewTarefas.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // Carregar as tarefas no início
        carregarTarefas();

        // Adicionar tarefa ao clicar no botão
        btnAdicionarTarefa.setOnClickListener(v -> adicionarTarefa());

        // Apagar tarefas selecionadas
        btnDelete.setOnClickListener(v -> confirmarApagarSelecionados());

        // Atualizar tarefa selecionada
        btnUpdate.setOnClickListener(v -> abrirModalAtualizar());

        // Configurar o comportamento ao clicar nos itens do ListView
        listViewTarefas.setOnItemClickListener((parent, view, position, id) -> {
            int tarefaId = ids.get(position);
            if (selectedIds.contains(tarefaId)) {
                selectedIds.remove((Integer) tarefaId);
                view.setBackgroundColor(getResources().getColor(android.R.color.transparent)); // Remove a seleção visual
            } else {
                selectedIds.add(tarefaId);
                view.setBackgroundColor(getResources().getColor(android.R.color.darker_gray)); // Adiciona a seleção visual
            }
        });
    }

    private void adicionarTarefa() {
        String titulo = editTitulo.getText().toString();
        String descricao = editDescricao.getText().toString();

        // Validar os campos
        if (titulo.isEmpty() || descricao.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Adicionar a tarefa à base de dados
        tarefaRepository.adicionarTarefa(titulo, descricao);

        // Limpar os campos e recarregar a lista
        editTitulo.setText("");
        editDescricao.setText("");
        carregarTarefas();

        Toast.makeText(this, "Tarefa adicionada", Toast.LENGTH_SHORT).show();
    }

    private void carregarTarefas() {
        Cursor cursor = tarefaRepository.listarTarefas();
        ArrayList<String> tarefas = new ArrayList<>();
        ids.clear();
        selectedIds.clear();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
            String titulo = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITULO));
            String descricao = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCRICAO));
            ids.add(id);
            tarefas.add(id + ": " + titulo + " - " + descricao);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tarefas);
        listViewTarefas.setAdapter(adapter);

        cursor.close();
    }

    private void confirmarApagarSelecionados() {
        if (selectedIds.isEmpty()) {
            Toast.makeText(this, "Nenhuma tarefa selecionada", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Confirmar Exclusão")
                .setMessage("Tem certeza de que deseja apagar as tarefas selecionadas?")
                .setPositiveButton("Sim", (dialog, which) -> apagarSelecionados())
                .setNegativeButton("Não", null)
                .show();
    }

    private void apagarSelecionados() {
        for (int id : selectedIds) {
            tarefaRepository.apagarTarefa(id);
        }
        selectedIds.clear();
        carregarTarefas();
        Toast.makeText(this, "Tarefas apagadas", Toast.LENGTH_SHORT).show();
    }

    private void abrirModalAtualizar() {
        if (selectedIds.size() != 1) {
            Toast.makeText(this, "Selecione apenas uma tarefa para atualizar", Toast.LENGTH_SHORT).show();
            return;
        }

        int idAtualizar = selectedIds.get(0);

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.modal_update);

        EditText editTituloModal = dialog.findViewById(R.id.editTituloModal);
        EditText editDescricaoModal = dialog.findViewById(R.id.editDescricaoModal);
        Button btnSalvar = dialog.findViewById(R.id.btnSalvarAtualizacao);

        btnSalvar.setOnClickListener(v -> {
            String novoTitulo = editTituloModal.getText().toString();
            String novaDescricao = editDescricaoModal.getText().toString();

            if (novoTitulo.isEmpty() || novaDescricao.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            tarefaRepository.atualizarTarefa(idAtualizar, novoTitulo, novaDescricao);
            carregarTarefas();
            dialog.dismiss();
            Toast.makeText(this, "Tarefa atualizada", Toast.LENGTH_SHORT).show();
        });

        dialog.show();
    }
}

