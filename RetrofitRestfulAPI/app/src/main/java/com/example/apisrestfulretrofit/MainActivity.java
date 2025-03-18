package com.example.apisrestfulretrofit;

import android.os.Bundle;
import android.graphics.Rect;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configuração inicial do RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Adicionar espaçamento entre os itens (16dp padrão e 32dp para o primeiro item)
        recyclerView.addItemDecoration(new ItemSpacingDecoration(this, 8, 32)); // Espaçamento padrão: 16dp, extra no primeiro: 32dp

        // Chamada ao método para obter utilizadores
        fetchUsers();
    }


    // Método para obter a lista de utilizadores da API
    private void fetchUsers() {
        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);

        Call<List<User>> call = apiService.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Atualizar o RecyclerView com os dados
                    userAdapter = new UserAdapter(response.body());
                    recyclerView.setAdapter(userAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "Erro ao obter utilizadores", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Falha na ligação: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
