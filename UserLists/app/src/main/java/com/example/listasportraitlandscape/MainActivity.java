package com.example.listasportraitlandscape;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private boolean isCompactMode = false; // Estado inicial do modo de visualização

    private List<Utilizador> utilizadores;
    private UtilizadorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Elementos da interface
        EditText searchInput = findViewById(R.id.searchInput);
        Button addButton = findViewById(R.id.addButton);
        Button sortButton = findViewById(R.id.sortButton);
        TextView countTextView = findViewById(R.id.countTextView);
        ListView listView = findViewById(R.id.listView);
        Button toggleViewButton = findViewById(R.id.toggleViewButton);

        // Inicializar ou restaurar a lista de utilizadores
        if (savedInstanceState != null) {
            utilizadores = (ArrayList<Utilizador>) savedInstanceState.getSerializable("utilizadores");
        } else {
            utilizadores = new ArrayList<>();
            utilizadores.add(new Utilizador("João"));
            utilizadores.add(new Utilizador("Maria"));
            utilizadores.add(new Utilizador("Carlos"));
        }

        // Configurar o adapter
        adapter = new UtilizadorAdapter(this, utilizadores, R.layout.list_item);
        listView.setAdapter(adapter);

        // Atualizar contagem inicial
        updateCount(countTextView, utilizadores);

        // Botão para adicionar utilizador
        addButton.setOnClickListener(v -> abrirDialogoAdicionarUtilizador(listView, countTextView));

        // Botão para ordenar utilizadores
        sortButton.setOnClickListener(v -> {
            Collections.sort(utilizadores, (u1, u2) -> u1.getName().compareToIgnoreCase(u2.getName()));
            refreshList(listView, countTextView);
            Toast.makeText(this, "Lista ordenada!", Toast.LENGTH_SHORT).show();
        });

        // Alternar entre modos de visualização
        toggleViewButton.setOnClickListener(v -> {
            isCompactMode = !isCompactMode;

            // Alternar entre layouts compacto e detalhado
            adapter.setLayout(isCompactMode ? R.layout.list_item_compact : R.layout.list_item);
            adapter.notifyDataSetChanged();

            String modo = isCompactMode ? "Compacto" : "Detalhado";
            Toast.makeText(this, "Modo alterado para: " + modo, Toast.LENGTH_SHORT).show();
        });

        // Campo de busca
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<Utilizador> filtrados = new ArrayList<>();
                for (Utilizador utilizador : utilizadores) {
                    if (utilizador.getName().toLowerCase().contains(s.toString().toLowerCase())) {
                        filtrados.add(utilizador);
                    }
                }
                adapter.updateList(filtrados);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Clique curto no ListView para editar utilizadores
        listView.setOnItemClickListener((parent, view, position, id) -> {
            // Obter o utilizador selecionado
            Utilizador utilizadorSelecionado = utilizadores.get(position);

            // Exibir uma mensagem indicando a seleção
            Toast.makeText(this, "Selecionou: " + utilizadorSelecionado.getName(), Toast.LENGTH_SHORT).show();
        });


        // Clique longo para mostrar opções (Editar ou Apagar)
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Utilizador utilizadorSelecionado = utilizadores.get(position);

            // Criar o diálogo com opções
            new AlertDialog.Builder(this)
                    .setTitle("Escolha uma opção")
                    .setItems(new CharSequence[]{"Editar Nome", "Apagar"}, (dialog, which) -> {
                        if (which == 0) { // Editar Nome
                            editarNome(utilizadorSelecionado, adapter);
                        } else if (which == 1) { // Apagar
                            new AlertDialog.Builder(this)
                                    .setTitle("Confirmação")
                                    .setMessage("Pretende apagar o utilizador " + utilizadorSelecionado.getName() + "?")
                                    .setPositiveButton("Sim", (dialogDelete, whichDelete) -> {
                                        utilizadores.remove(position);
                                        adapter.notifyDataSetChanged();
                                        updateCount(countTextView, utilizadores);
                                        Toast.makeText(this, "Utilizador apagado!", Toast.LENGTH_SHORT).show();
                                    })
                                    .setNegativeButton("Não", (dialogDelete, whichDelete) -> dialogDelete.dismiss())
                                    .create()
                                    .show();
                        }
                    })
                    .create()
                    .show();

            return true; // Indicar que o clique longo foi tratado
        });
    }

    private void editarNome(Utilizador utilizadorSelecionado, UtilizadorAdapter adapter) {
        EditText editText = new EditText(this);
        editText.setText(utilizadorSelecionado.getName());

        new AlertDialog.Builder(this)
                .setTitle("Editar Utilizador")
                .setMessage("Altere o nome do utilizador:")
                .setView(editText)
                .setPositiveButton("Salvar", (dialogEdit, whichEdit) -> {
                    utilizadorSelecionado.setName(editText.getText().toString());
                    adapter.notifyDataSetChanged();
                    Toast.makeText(this, "Utilizador atualizado!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", (dialogEdit, whichEdit) -> dialogEdit.dismiss())
                .create()
                .show();
    }

    private void abrirDialogoAdicionarUtilizador(ListView listView, TextView countTextView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Adicionar Novo Utilizador");

        final EditText input = new EditText(this);
        input.setHint("Nome do Utilizador");
        builder.setView(input);

        builder.setPositiveButton("Salvar", (dialog, which) -> {
            String nome = input.getText().toString();
            if (!nome.isEmpty()) {
                utilizadores.add(new Utilizador(nome)); // Adicionar o novo utilizador
                Collections.sort(utilizadores, (u1, u2) -> u1.getName().compareToIgnoreCase(u2.getName()));
                adapter.notifyDataSetChanged();
                updateCount(countTextView, utilizadores);
            } else {
                Toast.makeText(this, "Por favor, insira um nome válido.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    private void refreshList(ListView listView, TextView countTextView) {
        adapter.notifyDataSetChanged(); // Atualiza a ListView
        updateCount(countTextView, utilizadores);

        // Rolagem para o último item adicionado
        listView.post(() -> listView.setSelection(adapter.getCount() - 1));
    }

    private void updateCount(TextView countTextView, List<Utilizador> utilizadores) {
        countTextView.setText("Total de Utilizadores: " + utilizadores.size());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Salvar o estado da lista
        outState.putSerializable("utilizadores", new ArrayList<>(utilizadores));
    }
}
