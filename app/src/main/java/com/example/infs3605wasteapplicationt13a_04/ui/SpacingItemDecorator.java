package com.example.infs3605wasteapplicationt13a_04.ui;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpacingItemDecorator extends RecyclerView.ItemDecoration {
    //Declarations
    private final int verticalSpaceHeight;
    private final int horizontalSpaceHeight;

    public SpacingItemDecorator(int verticalSpaceHeight, int horizontalSpaceHeight){
        this.verticalSpaceHeight = verticalSpaceHeight;
        this.horizontalSpaceHeight = horizontalSpaceHeight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state){
        outRect.bottom = verticalSpaceHeight;
        outRect.left = horizontalSpaceHeight;
        outRect.right = horizontalSpaceHeight;

    }
}
