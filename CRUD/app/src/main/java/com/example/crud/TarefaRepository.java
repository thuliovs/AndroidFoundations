package com.example.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Classe que gerencia as operações de CRUD (Create, Read, Update, Delete).
 */
public class TarefaRepository {
    private DatabaseHelper dbHelper;

    public TarefaRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Adiciona uma nova tarefa
    public void adicionarTarefa(String titulo, String descricao) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TITULO, titulo);
        values.put(DatabaseHelper.COLUMN_DESCRICAO, descricao);
        db.insert(DatabaseHelper.TABLE_NAME, null, values);
        db.close();
    }

    // Lista todas as tarefas
    public Cursor listarTarefas() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.query(DatabaseHelper.TABLE_NAME, null, null, null, null, null, null);
    }

    // Apaga uma tarefa pelo ID
    public void apagarTarefa(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Atualiza uma tarefa pelo ID
    public void atualizarTarefa(int id, String novoTitulo, String novaDescricao) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TITULO, novoTitulo);
        values.put(DatabaseHelper.COLUMN_DESCRICAO, novaDescricao);
        db.update(DatabaseHelper.TABLE_NAME, values, DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
