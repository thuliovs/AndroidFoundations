package com.example.apisrestfulretrofit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private final List<User> userList;

    public UserAdapter(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.name.setText("Nome: " + user.getName());
        holder.username.setText("Utilizador: @" + user.getUsername());
        holder.email.setText("Email: " + user.getEmail());
        holder.phone.setText("Telefone: " + user.getPhone());
        if (user.getAddress() != null) {
            holder.address.setText("Endereço: " + user.getAddress().getStreet() + ", " + user.getAddress().getCity());
        }
        if (user.getCompany() != null) {
            holder.company.setText("Empresa: " + user.getCompany().getName());
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView name, username, email, phone, address, company;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            username = itemView.findViewById(R.id.username);
            email = itemView.findViewById(R.id.email);
            phone = itemView.findViewById(R.id.phone);
            address = itemView.findViewById(R.id.address);
            company = itemView.findViewById(R.id.company);
        }
    }
}
