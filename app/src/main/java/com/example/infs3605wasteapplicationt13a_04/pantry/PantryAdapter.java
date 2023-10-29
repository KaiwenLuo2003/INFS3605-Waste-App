package com.example.infs3605wasteapplicationt13a_04.pantry;

import static com.example.infs3605wasteapplicationt13a_04.R.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infs3605wasteapplicationt13a_04.objects.IngredientItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.MyViewHolder> {
    private LayoutInflater mInflater;
    private ArrayList<IngredientItem> mItems;
    private ItemClickListener mClickListener;

    public PantryAdapter(Context context, ArrayList<IngredientItem> items, ItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.mItems = items;
        this.mClickListener = listener;
    }

    @NonNull
    @Override
    public PantryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout.list_pantry_row, parent, false);
        return new MyViewHolder(view);
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

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image;
        TextView name;
        TextView expiry;
        TextView quantity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(id.ivArt);
            name = itemView.findViewById(id.tvName);
            expiry = itemView.findViewById(id.tvExpiry);
            quantity = itemView.findViewById(id.tvPortion);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null){
                mClickListener.onItemClick(view, (String) mItems.get(this.getLayoutPosition()).getItemName());
            }
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, String itemName);
    }

    public void updateList(List<IngredientItem> refreshedItemList){
        mItems.clear();
        mItems.addAll(refreshedItemList);
        this.notifyDataSetChanged();
    }
}
