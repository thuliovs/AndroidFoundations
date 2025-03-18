package com.example.externalstorage;

import android.content.Context;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;


public class SDCardUtils {

    public static boolean salvarSDPublico(Context context, String fileName, String texto) {
        try {
            // Obter os volumes externos disponíveis
            File[] externalStorageVolumes = context.getExternalMediaDirs();

            // Verificar se o Cartão SD está disponível (o segundo volume)
            if (externalStorageVolumes.length > 1 && externalStorageVolumes[1] != null) {
                File sdCardPublicDir = new File(externalStorageVolumes[1], "Documents");

                // Criar o diretório público se não existir
                if (!sdCardPublicDir.exists() && !sdCardPublicDir.mkdirs()) {
                    return false; // Falha ao criar o diretório
                }

                // Criar o ficheiro no diretório público
                File file = new File(sdCardPublicDir, fileName);

                // Gravar o conteúdo no ficheiro
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(texto.getBytes());
                    return true; // Sucesso
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Erro
    }

    public static boolean removerSDPublico(Context context, String fileName) {
        try {
            // Obter os volumes externos disponíveis
            File[] externalStorageVolumes = context.getExternalMediaDirs();

            // Verificar se o Cartão SD está disponível (o segundo volume)
            if (externalStorageVolumes.length > 1 && externalStorageVolumes[1] != null) {
                File sdCardPublicDir = new File(externalStorageVolumes[1], "Documents");
                File file = new File(sdCardPublicDir, fileName);

                if (file.exists()) {
                    return file.delete(); // Sucesso se o ficheiro for excluído
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Erro
    }


    public static boolean salvarSDPrivado(Context context, String fileName, String texto) {
        try {
            // Obter os diretórios de armazenamento externo
            File[] externalStorageVolumes = context.getExternalFilesDirs(null);

            // Verificar se há mais de um volume (o primeiro é o armazenamento interno)
            if (externalStorageVolumes.length > 1) {
                File sdCardPrivateDir = externalStorageVolumes[1]; // O segundo volume é o cartão SD

                if (sdCardPrivateDir != null && sdCardPrivateDir.exists()) {
                    File file = new File(sdCardPrivateDir, fileName);

                    // Gravar no ficheiro
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        fos.write(texto.getBytes());
                        return true; // Sucesso
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Erro
    }

    public static boolean removerSDPrivado(Context context, String fileName) {
        try {
            // Obter os diretórios de armazenamento externo
            File[] externalStorageVolumes = context.getExternalFilesDirs(null);

            // Verificar se o Cartão SD está disponível (o segundo volume)
            if (externalStorageVolumes.length > 1) {
                File sdCardPrivateDir = externalStorageVolumes[1]; // O segundo volume é o Cartão SD
                File file = new File(sdCardPrivateDir, fileName);

                if (file.exists()) {
                    return file.delete(); // Sucesso se o ficheiro for excluído
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Erro
    }


    public static boolean lerSDPrivado(Context context, String fileName, TextView textViewOutput) {
        try {
            // Obter os diretórios de armazenamento externo
            File[] externalStorageVolumes = context.getExternalFilesDirs(null);

            // Verificar se o Cartão SD está disponível (o segundo volume)
            if (externalStorageVolumes.length > 1) {
                File sdCardPrivateDir = externalStorageVolumes[1]; // O segundo volume é o Cartão SD
                File file = new File(sdCardPrivateDir, fileName);

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
                    textViewOutput.setText("Ficheiro não encontrado no Cartão SD Privado.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Erro
    }

    public static boolean lerSDPublico(Context context, String fileName, TextView textViewOutput) {
        try {
            // Obter os volumes externos disponíveis
            File[] externalStorageVolumes = context.getExternalMediaDirs();

            // Verificar se o Cartão SD está disponível (o segundo volume)
            if (externalStorageVolumes.length > 1 && externalStorageVolumes[1] != null) {
                File sdCardPublicDir = new File(externalStorageVolumes[1], "Documents");
                File file = new File(sdCardPublicDir, fileName);

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
                    textViewOutput.setText("Ficheiro não encontrado no Cartão SD Público.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            textViewOutput.setText("Erro ao ler o ficheiro do Cartão SD Público.");
        }
        return false; // Erro
    }

}



