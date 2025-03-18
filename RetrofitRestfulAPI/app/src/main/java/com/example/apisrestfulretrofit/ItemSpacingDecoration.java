package com.example.apisrestfulretrofit;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// Classe para adicionar espaçamento entre os itens do RecyclerView
public class ItemSpacingDecoration extends RecyclerView.ItemDecoration {

    private final int spacing; // Espaçamento padrão em pixels
    private final int firstItemTopSpacing; // Espaçamento adicional para o primeiro item

    // Construtor que aceita o espaçamento padrão e o espaçamento do primeiro item
    public ItemSpacingDecoration(Context context, int spacingDp, int firstItemTopSpacingDp) {
        // Converte dp para pixels
        this.spacing = dpToPx(context, spacingDp);
        this.firstItemTopSpacing = dpToPx(context, firstItemTopSpacingDp);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // Posição do item na lista

        // Adicionar espaçamento extra no topo apenas para o primeiro item
        if (position == 0) {
            outRect.top = firstItemTopSpacing;
        } else {
            outRect.top = spacing; // Espaçamento padrão para os outros itens
        }

        // Espaçamento padrão para os lados e parte inferior
        outRect.left = spacing;
        outRect.right = spacing;
        outRect.bottom = spacing;
    }

    // Método para converter dp em pixels
    private int dpToPx(Context context, int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }
}

