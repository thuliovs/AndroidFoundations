package com.example.listasportraitlandscape;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UtilizadorAdapter extends BaseAdapter {

    private final Context context;
    private List<Utilizador> utilizadores;
    private int layoutId;

    public UtilizadorAdapter(Context context, List<Utilizador> utilizadores, int layoutId) {
        this.context = context;
        this.utilizadores = utilizadores;
        this.layoutId = layoutId;
    }

    public void setLayout(int layoutId) {
        this.layoutId = layoutId;
    }

    public void updateList(List<Utilizador> novosUtilizadores) {
        this.utilizadores = new ArrayList<>(novosUtilizadores);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return utilizadores.size();
    }

    @Override
    public Object getItem(int position) {
        return utilizadores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        }

        // Referenciar o widget do texto
        TextView textView = convertView.findViewById(R.id.textView);
        Utilizador utilizador = utilizadores.get(position);

        // Configurar o texto
        textView.setText(utilizador.getName());

        // Configurar a imagem apenas no modo detalhado
        if (layoutId == R.layout.list_item) {
            ImageView imageView = convertView.findViewById(R.id.imageView);
            imageView.setImageResource(utilizador.getImageResId());
            imageView.setVisibility(View.VISIBLE);
        } else {
            // No modo compacto, esconda a imagem
            ImageView imageView = convertView.findViewById(R.id.imageView);
            if (imageView != null) {
                imageView.setVisibility(View.GONE);
            }
        }

        return convertView;
    }


}
