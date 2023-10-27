package com.example.infs3605wasteapplicationt13a_04.pantry;

import static com.example.infs3605wasteapplicationt13a_04.R.*;
import static com.example.infs3605wasteapplicationt13a_04.R.drawable.*;
import static com.google.common.collect.Iterators.size;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infs3605wasteapplicationt13a_04.R;
import com.example.infs3605wasteapplicationt13a_04.ui.RecyclerViewInterface;

import java.util.ArrayList;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    private ArrayList<Pantry> mItems;

    public PantryAdapter(ArrayList<Pantry> items, RecyclerViewInterface recyclerViewInterface) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.mItems = items;
    }

    @NonNull
    @Override
    public PantryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout.list_pantry_row, parent, false);
        return new MyViewHolder(view, recyclerViewInterface);
    }


    @Override
    public void onBindViewHolder(@NonNull PantryAdapter.MyViewHolder holder, int position) {
        Pantry items = mItems.get(position);
        holder.name.setText(items.getItem());
        String item = items.getItem();
        holder.expiry.setText(items.getExpiry());
        holder.quantity.setText(items.getExpiry());
        holder.image.setImageResource(items.getImage());
        

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView expiry;
        TextView quantity;
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            image = itemView.findViewById(id.ivArt);
            name = itemView.findViewById(id.tvName);
            expiry = itemView.findViewById(id.tvExpiry);
            quantity = itemView.findViewById(id.tvPortion);
        }
    }
}
