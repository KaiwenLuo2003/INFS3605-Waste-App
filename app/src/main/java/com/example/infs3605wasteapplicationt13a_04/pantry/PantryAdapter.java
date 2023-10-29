package com.example.infs3605wasteapplicationt13a_04.pantry;

import static com.example.infs3605wasteapplicationt13a_04.R.*;
import static com.example.infs3605wasteapplicationt13a_04.R.drawable.*;
import static com.google.common.collect.Iterators.size;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infs3605wasteapplicationt13a_04.R;
import com.example.infs3605wasteapplicationt13a_04.objects.IngredientItem;
import com.example.infs3605wasteapplicationt13a_04.ui.RecyclerViewInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    private ArrayList<IngredientItem> mItems;

    public PantryAdapter(ArrayList<IngredientItem> items, RecyclerViewInterface recyclerViewInterface) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.mItems = items;
    }

    @NonNull
    @Override
    public PantryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout.list_pantry_row, parent, false);
        return new MyViewHolder(view, recyclerViewInterface);
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull PantryAdapter.MyViewHolder holder, int position) {
        IngredientItem ingredientItem = mItems.get(position);
        holder.name.setText(ingredientItem.getItemName());
        String item = ingredientItem.getItemName();
        holder.expiry.setText(ingredientItem.getExpiryDate());
        holder.quantity.setText(ingredientItem.getQuantity());
        holder.image.setImageResource(ingredientItem.getIcon());


        //Background highlight based on expiry date
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date todayDate = cal.getTime();

        //parse ingredientItem String date to Date object
        try {
            Date expiryDate = df.parse(ingredientItem.getExpiryDate());

            //compare dates
            if(todayDate.compareTo(expiryDate) > 0){
                //If item expired, turn background red
                int redColor = Color.parseColor("#F87676");
                holder.itemView.setBackgroundColor(redColor);
            } else if(todayDate.compareTo(expiryDate) == 0){
                //If item expiring today, turn background yellow
                int yellowColor = Color.parseColor("#FFBF00");
                holder.itemView.setBackgroundColor(yellowColor);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

//        String today = df.format(date);




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

    public void updateList(List<IngredientItem> refreshedItemList){
        mItems.clear();
        mItems.addAll(refreshedItemList);
        this.notifyDataSetChanged();
    }
}
