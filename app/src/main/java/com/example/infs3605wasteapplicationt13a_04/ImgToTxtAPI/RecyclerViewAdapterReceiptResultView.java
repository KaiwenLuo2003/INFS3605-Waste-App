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
    private List<IngredientItem> mItems;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public IngredientItem item;

    // data is passed into the constructor
    RecyclerViewAdapterReceiptResultView(Context context, List<IngredientItem> data, ItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.mItems = data;
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
        item = mItems.get(position);
        holder.itemName.setText(item.getItemName());
        holder.expiryDate.setText(item.getExpiryDate());
        holder.portion.setText("Qty: " + item.getQuantity());
        holder.itemImg.setImageResource(item.getIcon());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mItems.size();
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
            if (mClickListener != null) {
                mClickListener.onItemClick(view, (String) mItems.get(this.getLayoutPosition()).getItemName());
            }
        }
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, String itemName);
    }

    public void updateList(List<IngredientItem> refreshedItemList){
        mItems.clear();
        mItems.addAll(refreshedItemList);
        this.notifyDataSetChanged();
    }

}
