package com.example.infs3605wasteapplicationt13a_04.ImgToTxtAPI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.infs3605wasteapplicationt13a_04.R;
import com.example.infs3605wasteapplicationt13a_04.objects.IngredientItem;

import java.util.List;

public class RecyclerViewAdapterReceiptResultView extends RecyclerView.Adapter<RecyclerViewAdapterReceiptResultView.ViewHolder>{
    private List<IngredientItem> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    RecyclerViewAdapterReceiptResultView(Context context, List<IngredientItem> data, ItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mClickListener = listener;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_receipt_result_row, parent, false);
        return new RecyclerViewAdapterReceiptResultView.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(RecyclerViewAdapterReceiptResultView.ViewHolder holder, int position) {
        IngredientItem ingredientItem = mData.get(position);
        holder.itemName.setText(ingredientItem.getItemName());
        holder.expiryDate.setText(ingredientItem.getExpiryDate());
        holder.portion.setText("Qty: " + ingredientItem.getQuantity());
        holder.itemImg.setImageResource(ingredientItem.getIcon());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView itemName;
        TextView portion;
        TextView expiryDate;
        ImageView itemImg;

        ViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.tvName);
            portion = itemView.findViewById(R.id.tvPortion);
            expiryDate = itemView.findViewById(R.id.tvExpiry);
            itemImg = itemView.findViewById(R.id.ivArt);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, (String) view.getTag());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id).getItemName();
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, String itemName);
    }
}
