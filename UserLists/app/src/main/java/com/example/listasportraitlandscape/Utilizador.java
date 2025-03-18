package com.example.listasportraitlandscape;

import java.io.Serializable;

public class Utilizador implements Serializable {
    private String name;
    private final int imageResId;

    public Utilizador(String name) {
        this.name = name;
        this.imageResId = R.drawable.avatar_utilizador;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageResId() {
        return imageResId;
    }
}