package com.example.infs3605wasteapplicationt13a_04.recipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.infs3605wasteapplicationt13a_04.R;
import com.example.infs3605wasteapplicationt13a_04.api.Recipe;
import com.example.infs3605wasteapplicationt13a_04.ui.RecyclerViewInterface;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapterRecipeView extends RecyclerView.Adapter<RecyclerViewAdapterRecipeView.ViewHolder> {
    private List<Recipe> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context mContext;

    // data is passed into the constructor
    RecyclerViewAdapterRecipeView(Context context, List<Recipe> data) {
        mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_recipe_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView and ImageView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe tempRecipe = mData.get(position);
        holder.myTextView.setText(tempRecipe.getTitle());
        Picasso.get().load(tempRecipe.getImage()).into(holder.myImageView);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        ImageView myImageView;

        //binds view objects to respective elements in xml file
        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.recipeName);
            myImageView = itemView.findViewById(R.id.ivArt);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Recipe getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}