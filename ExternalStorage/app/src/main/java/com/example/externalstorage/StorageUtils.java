package com.example.externalstorage;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.TextView;
import android.database.Cursor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class StorageUtils {

    // Salvar no armazenamento externo privado
    public static boolean salvarDiscoExternoPrivado(Context context, String fileName, String texto) {
        try {
            File privateDir = context.getExternalFilesDir(null);
            File file = new File(privateDir, fileName);

            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(texto.getBytes());
                return true; // Sucesso
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Erro
        }
    }

    // Ler do armazenamento externo privado
    public static boolean lerDiscoExternoPrivado(Context context, String fileName, TextView textViewOutput) {
        try {
            File privateDir = context.getExternalFilesDir(null);
            File file = new File(privateDir, fileName);

            if (file.exists()) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    int c;
                    StringBuilder conteudo = new StringBuilder();
                    while ((c = fis.read()) != -1) {
                        conteudo.append((char) c);
                    }

                    // Exibir o conteúdo no TextView
                    textViewOutput.setText(conteudo.toString());
                    return true; // Sucesso
                }
            } else {
                textViewOutput.setText("Ficheiro não encontrado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Erro
    }

    // Salvar no armazenamento externo público
    public static boolean salvarDiscoExternoPublico(Context context, String fileName, String texto) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "text/plain");
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS);

        Uri uri = context.getContentResolver().insert(MediaStore.Files.getContentUri("external"), values);

        if (uri != null) {
            try (FileOutputStream fos = (FileOutputStream) context.getContentResolver().openOutputStream(uri)) {
                fos.write(texto.getBytes());
                return true; // Sucesso
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false; // Erro
    }

    // Remover ficheiro do armazenamento externo privado
    public static boolean removerDiscoExternoPrivado(Context context, String fileName) {
        File privateDir = context.getExternalFilesDir(null);
        File file = new File(privateDir, fileName);

        if (file.exists() && file.delete()) {
            return true; // Sucesso
        }
        return false; // Erro
    }

    // Remover ficheiro do armazenamento externo público
    public static boolean removerDiscoExternoPublico(Context context, String fileName) {
        Uri contentUri = MediaStore.Files.getContentUri("external");
        String selection = MediaStore.MediaColumns.DISPLAY_NAME + "=?";
        String[] selectionArgs = new String[]{fileName};

        int deletedRows = context.getContentResolver().delete(contentUri, selection, selectionArgs);
        return deletedRows > 0; // Sucesso se ao menos uma linha foi deletada
    }

    // Ler do armazenamento externo público
    public static boolean lerDiscoExternoPublico(Context context, String fileName, TextView textViewOutput) {
        Uri contentUri = MediaStore.Files.getContentUri("external");
        String[] projection = {MediaStore.MediaColumns.DISPLAY_NAME, MediaStore.MediaColumns.RELATIVE_PATH};
        String selection = MediaStore.MediaColumns.DISPLAY_NAME + "=?";
        String[] selectionArgs = new String[]{fileName};

        try (Cursor cursor = context.getContentResolver().query(contentUri, projection, selection, selectionArgs, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/" + fileName;
                File file = new File(filePath);

                if (file.exists()) {
                    try (FileInputStream fis = new FileInputStream(file)) {
                        int c;
                        StringBuilder conteudo = new StringBuilder();
                        while ((c = fis.read()) != -1) {
                            conteudo.append((char) c);
                        }

                        // Exibir o conteúdo no TextView
                        textViewOutput.setText(conteudo.toString());
                        return true; // Sucesso
                    }
                } else {
                    textViewOutput.setText("Ficheiro não encontrado.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Erro
    }


}

